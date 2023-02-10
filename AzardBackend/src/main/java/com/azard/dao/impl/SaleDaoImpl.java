package com.azard.dao.impl;

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

import com.azard.dao.SaleDao;
import com.azard.model.Sale;
import com.azard.model.SaleItem;
import com.azard.model.SalesByStore;

@Service
public class SaleDaoImpl extends JdbcDaoSupport implements SaleDao {
	private static final String GET_ALL_SALES_QUERY = "select " +
			"s.id, " +
			"s.sale_timestamp, " +
			"s.employee_id, " +
			"s.store_id, " +
			"s.is_cash_payment, " +
			"sum(si.sale_price) as amount, " +
			"e.name as employee_name, " +
			"CONCAT(ks.city, \", \", ks.name) as store_name, " +
			"dc.code as discount_code_code " +
			"from sale s " +
			"join sale_item si on si.sale_id = s.id " +
			//"join sale_item si2 on si2.sale_id = s.id" +
			"join employee e on s.employee_id = e.id " +
			"join store ks on s.store_id = ks.id " +
			"left join discount_code dc on s.discount_code_id = dc.id ";
	
	private static final String GET_ALL_SALE_ITEMS_QUERY = "select " +
			"si.id, " +
			"si.sale_id, " +
			"si.item_id, " +
			"si.is_refunded, " +
			"s.sale_timestamp, " +
			"iv.leather_id, " +
			"iv.leather_name, " +
			"iv.model_id, " +
			"iv.model_name, " +
			"iv.brand_id, " +
			"si.sale_price, " +
			"si.item_price, " +
			"e.id as employee_id, " +
			"e.name as employee_name, " +
			"ks.id as store_id, " +
			"CONCAT(ks.city, \", \", ks.name) as store_name, " +
			"dc.code as discountCodeCode " +
			"from sale_item si " +
			"join sale s on si.sale_id = s.id " +
			"left join discount_code dc on s.discount_code_id = dc.id " +
			"join item_vw iv on iv.id = si.item_id " +
			"join store ks on ks.id = s.store_id " +
			"join employee e on e.id = s.employee_id ";

	private static final String GET_SALES_BY_STORE_QUERY = "select " +
			"ks.id as storeId, " +
			"ks.code as storeCode, " +
			"CONCAT(ks.city,\",\",ks.name) as store_name, " +
			"coalesce(sum(si.sale_price), 0.00) as amount, " +
			"coalesce(count(si.id), 0) as count " +
			"from store ks " +
			"left join sale s on s.store_id = ks.id and s.sale_timestamp between ? and ? " +
			"left join sale_item si on si.sale_id = s.id " +
			"and si.is_refunded <> true " +
			"where ks.is_store is true ";
	
	private static final String PERIOD_CRITERIA_QUERY = " where sale_timestamp between ? and ?";
	private static final String STORE_CRITERIA_QUERY = " and ks.id in (%s)";
	private static final String REFUND_QUERY = " and si.is_refunded <> true";
	private static final String LEATHER_QUERY = " and iv.leather_id = ?";
	private static final String BRAND_QUERY = " and iv.device_brand_id = ?";
	private static final String MODEL_QUERY = " and iv.device_model_id = ?";
	private static final String INSERT_SALE = "insert into sale (employee_id, store_id, sale_timestamp, is_cash_payment, discount_code_id)"
			+ " values (?, ?, ?, ?, ?)";
	private static final String ORDER_BY_SALE = " order by s.sale_timestamp";
	private static final String ORDER_BY_STORE = " order by ks.id";
	private static final String GROUP_BY_SALE = " group by s.id";
	private static final String GROUP_BY_STORE = " group by ks.id";
	private static final String GET_SALE_ITEMS_PER_SALE = "select " +
			"si.id, " +
			"si.sale_id, " +
			"si.item_id, " +
			"si.is_refunded, " +
			"l.id as leather_id, " +
			"l.name as leather_name, " +
			"m.id as model_id, " +
			"m.name as model_name, " +
			"b.id as brand_id, " +
			"b.name as brand_name, " +
			"si.sale_price, " +
			"si.item_price " +
			"from sale_item si " +
			"join item i on si.item_id = i.id " +
			"join leather l on i.leather_id = l.id " +
			"join model m on i.model_id = m.id " +
			"join brand b on m.brand_id = b.id " +
			"where si.sale_id = ?";
	private static final String INSERT_SALE_ITEM = "insert into sale_item(sale_id, item_id, item_price, sale_price) values (?, ?, ?, ?)";
	
	private static final String UPDATE_REFUNDED_SALE_ITEM = "update sale_item set is_refunded = true where id = ?";

	private static final String GET_SALE_ITEM_PRICE = "select sale_price from sale_item where id = ?";
	
	private BeanPropertyRowMapper<Sale> saleRowMapper;
	private BeanPropertyRowMapper<SaleItem> saleItemRowMapper;
	private BeanPropertyRowMapper<SalesByStore> saleByStoreRowMapper;

	@Autowired
	public SaleDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Sale> getSaleRowMapper() {
		if (saleRowMapper == null) {
			saleRowMapper = new BeanPropertyRowMapper<Sale>(Sale.class);
			saleRowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return saleRowMapper;
	}
	
	private BeanPropertyRowMapper<SaleItem> getSaleItemRowMapper() {
		if (saleItemRowMapper == null) {
			saleItemRowMapper = new BeanPropertyRowMapper<SaleItem>(SaleItem.class);
			saleItemRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return saleItemRowMapper;
	}
	
	private BeanPropertyRowMapper<SalesByStore> getSaleByStoreRowMapper() {
		if (saleByStoreRowMapper == null) {
			saleByStoreRowMapper = new BeanPropertyRowMapper<SalesByStore>(SalesByStore.class);
			saleByStoreRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return saleByStoreRowMapper;
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
			if (sale.getDiscountCodeId() != null) {
				statement.setInt(5, sale.getDiscountCodeId());
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
			Long endDateMilliseconds, String storeIds) {
		String searchQuery = GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY + String.format(STORE_CRITERIA_QUERY, storeIds);
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		searchQuery += GROUP_BY_SALE;
		searchQuery += ORDER_BY_SALE;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);

		return getJdbcTemplate().query(
				searchQuery, argsArr, getSaleRowMapper());
	}
	
	@Override
	public List<SaleItem> searchSaleItems(Long startDateMilliseconds,
			Long endDateMilliseconds, String storeIds, Integer leatherId, Integer brandId, Integer modelId) {
		String searchQuery = GET_ALL_SALE_ITEMS_QUERY + PERIOD_CRITERIA_QUERY + String.format(STORE_CRITERIA_QUERY, storeIds) + REFUND_QUERY;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		searchQuery += addDetailedSearch(leatherId, brandId, modelId, argsList);
		
		searchQuery += ORDER_BY_SALE;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);
				
		return getJdbcTemplate().query(
				searchQuery, argsArr, getSaleItemRowMapper());
	}

	private String addDetailedSearch(Integer leatherId, Integer brandId, Integer modelId, List<Object> args) {
		String detailedQuery = "";
		if (leatherId != null) {
			detailedQuery += LEATHER_QUERY;
			args.add(leatherId);
		}
		
		if (brandId != null) {
			detailedQuery += BRAND_QUERY;
			args.add(brandId);
		}
		
		if (modelId != null) {
			detailedQuery += MODEL_QUERY;
			args.add(modelId);
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

	@Override
	public List<SalesByStore> searchSaleByStore(Long startDateMilliseconds, Long endDateMilliseconds) {
		String searchQuery = GET_SALES_BY_STORE_QUERY;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		//searchQuery += addDetailedSearch(productCode, deviceBrandId, deviceModelId, productTypeId, argsList);
		
		searchQuery += GROUP_BY_STORE;
		searchQuery += ORDER_BY_STORE;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);

		return getJdbcTemplate().query(
				searchQuery, argsArr, getSaleByStoreRowMapper());
	}
	
}
