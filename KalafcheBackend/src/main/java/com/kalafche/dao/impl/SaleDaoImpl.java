package com.kalafche.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.SaleDao;
import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;

@Service
public class SaleDaoImpl extends JdbcDaoSupport implements SaleDao {
	private static final String GET_ALL_SALES_QUERY = "select " +
			"s.id, " +
			"s.sale_timestamp, " +
			"s.employee_id, " +
			"s.store_id, " +
			"sum(si.sale_price) as amount, " +
			"e.name as employee_name, " +
			"CONCAT(ks.city, \", \", ks.name) as store_name, " +
			"p.code as partner_code " +
			"from sale s " +
			"join sale_item si on si.sale_id = s.id " +
			//"join sale_item si2 on si2.sale_id = s.id" +
			"join employee e on s.employee_id = e.id " +
			"join kalafche_store ks on s.store_id = ks.id " +
			"left join partner p on p.id = s.partner_id ";
	
	private static final String GET_ALL_SALE_ITEMS_QUERY = "select " +
			"si.id, " +
			"si.sale_id, " +
			"si.item_id, " +
			"si.is_refunded, " +
			"s.sale_timestamp, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.device_brand_id, " +
			"si.sale_price, " +
			"si.item_price, " +
			"e.id as employee_id, " +
			"e.name as employee_name, " +
			"ks.id as store_id, " +
			"CONCAT(ks.city, \", \", ks.name) as store_name " +
			"from sale_item si " +
			"join sale s on si.sale_id = s.id " +
			"join item_vw iv on iv.id = si.item_id " +
			"join kalafche_store ks on ks.id = s.store_id " +
			"join employee e on e.id = s.employee_id ";

	private static final String PERIOD_CRITERIA_QUERY = " where sale_timestamp between ? and ?";
	private static final String KALAFCHE_STORE_CRITERIA_QUERY = " and ks.id in (%s)";
	private static final String REFUND_QUERY = " and si.is_refunded <> true";
	private static final String PRODUCT_CODE_QUERY = " and iv.product_code in (?)";
	private static final String DEVICE_BRAND_QUERY = " and iv.device_brand_id = ?";
	private static final String DEVICE_MODEL_QUERY = " and iv.device_model_id = ?";
	private static final String INSERT_SALE = "insert into sale (employee_id, store_id, sale_timestamp, is_cash_payment, partner_id)"
			+ " values (?, ?, ?, ?, ?)";
	private static final String ORDER_BY = " order by s.sale_timestamp";
	private static final String GROUP_BY = " group by s.id";
	private static final String GET_SALE_ITEMS_PER_SALE = "select " +
			"si.id, " +
			"si.sale_id, " +
			"si.item_id, " +
			"si.is_refunded, " +
			"p.id as product_id, " +
			"p.code as product_code, " +
			"p.name as product_name, " +
			"dm.id as device_model_id, " +
			"dm.name as device_model_name, " +
			"db.id as device_brand_id, " +
			"db.name as device_brand_name, " +
			"si.sale_price, " +
			"si.item_price " +
			"from sale_item si " +
			"join item i on si.item_id = i.id " +
			"join product p on i.product_id = p.id " +
			"join device_model dm on i.device_model_id = dm.id " +
			"join device_brand db on dm.device_brand_id = db.id " +
			"where si.sale_id = ?";
	private static final String INSERT_SALE_ITEM = "insert into sale_item(sale_id, item_id, item_price, sale_price) values (?, ?, ?, ?)";
	
	private static final String UPDATE_REFUNDED_SALE_ITEM = "update sale_item set is_refunded = true where id = ?";

	private static final String GET_SALE_ITEM_PRICE = "select sale_price from sale_item where id = ?";
	
	private BeanPropertyRowMapper<Sale> rowMapper;
	private BeanPropertyRowMapper<SaleItem> saleItemRowMapper;

	@Autowired
	public SaleDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Sale> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Sale>(Sale.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return rowMapper;
	}
	
	private BeanPropertyRowMapper<SaleItem> getSaleItemRowMapper() {
		if (saleItemRowMapper == null) {
			saleItemRowMapper = new BeanPropertyRowMapper<SaleItem>(SaleItem.class);
			saleItemRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return saleItemRowMapper;
	}

	@Override
	public Integer insertSale(Sale sale) throws SQLException {		
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(
						INSERT_SALE, Statement.RETURN_GENERATED_KEYS);) {
			statement.setInt(1, sale.getEmployeeId());
			statement.setInt(2, sale.getStoreId());
			statement.setLong(3, sale.getSaleTimestamp());
			statement.setBoolean(4, sale.getIsCashPayment());
			if (sale.getPartnerId() != null) {
				statement.setInt(5, sale.getPartnerId());
			} else {
				statement.setNull(5, Types.INTEGER);
			}

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating new sale failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating sale failed, no ID obtained.");
				}
			}
		}
	}

	@Override
	public List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds) {
		String searchQuery = GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY + String.format(KALAFCHE_STORE_CRITERIA_QUERY, kalafcheStoreIds);
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		searchQuery += GROUP_BY;
		searchQuery += ORDER_BY;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);

		return getJdbcTemplate().query(
				searchQuery, argsArr, getRowMapper());
	}
	
	@Override
	public List<SaleItem> searchSaleItems(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode, Integer deviceBrandId, Integer deviceModelId) {
		String searchQuery = GET_ALL_SALE_ITEMS_QUERY + PERIOD_CRITERIA_QUERY + String.format(KALAFCHE_STORE_CRITERIA_QUERY, kalafcheStoreIds) + REFUND_QUERY;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		searchQuery += addDetailedSearch(productCode, deviceBrandId, deviceModelId, argsList);
		
		searchQuery += ORDER_BY;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);
				
		return getJdbcTemplate().query(
				searchQuery, argsArr, getSaleItemRowMapper());
	}

	private String addDetailedSearch(String productCode, Integer deviceBrandId, Integer deviceModelId, List<Object> args) {
		String detailedQuery = "";
		if (productCode != null && productCode != "") {
			detailedQuery += PRODUCT_CODE_QUERY;
			args.add(productCode);
		}
		
		if (deviceBrandId != null) {
			detailedQuery += DEVICE_BRAND_QUERY;
			args.add(deviceBrandId);
		}
		
		if (deviceModelId != null) {
			detailedQuery += DEVICE_MODEL_QUERY;
			args.add(deviceModelId);
		}
		
		return detailedQuery;
	}

	@Override
	public List<SaleItem> getSaleItemsBySaleId(Integer saleId) {
		return getJdbcTemplate().query(GET_SALE_ITEMS_PER_SALE, getSaleItemRowMapper(), saleId);
	}

	@Override
	public void insertSaleItem(SaleItem saleItem) {
		getJdbcTemplate().update(INSERT_SALE_ITEM, saleItem.getSaleId(), saleItem.getItemId(), saleItem.getItemPrice(),
				saleItem.getSalePrice());
	}
	
	@Override
	public void updateRefundedSaleItem(Integer saleItemId) {
		getJdbcTemplate().update(UPDATE_REFUNDED_SALE_ITEM, saleItemId);
	}
	
	@Override
	public BigDecimal getSaleItemPrice(Integer saleItemId) {
		return getJdbcTemplate().queryForObject(GET_SALE_ITEM_PRICE, BigDecimal.class, saleItemId);
	}
	
}
