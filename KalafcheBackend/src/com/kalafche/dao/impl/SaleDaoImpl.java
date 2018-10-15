package com.kalafche.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.SaleDao;
import com.kalafche.model.Sale;

@Service
public class SaleDaoImpl extends JdbcDaoSupport implements SaleDao {
	private static final String GET_ALL_SALES_QUERY = 
			"select " +
					"s.id, " +
					"s.sale_timestamp, " +
					"s.employee_id, " +
					"iv.id, " +
					"s.cost, " +
					"case s.discounted_cost when 0 then s.cost else s.discounted_cost end as sale_price, " +
					"e.name as employee_name, " +
					"iv.product_name, " +
					"iv.product_code, " +
					"CONCAT(ks.city,\",\",ks.name) as kalafche_store_name, " +
					"ks.id as kalafche_store_id, " +
					"iv.device_model_id, " +
					"iv.device_model_name, " +
					"iv.device_brand_id, " +
					"p.code as partner_code " +
					"from sale s " +
					"join employee e on s.employee_id = e.id " +
					"join item_vw iv on s.item_id = iv.id " +
					"join kalafche_store ks on s.STORE_ID = ks.ID " +
					"left join partner p on p.id = s.partner_id ";

	private static final String PERIOD_CRITERIA_QUERY = " where sale_timestamp between ? and ?";
	private static final String KALAFCHE_STORE_CRITERIA_QUERY = " and ks.id in (%s)";
	private static final String PRODUCT_CODE_QUERY = " and iv.product_code in (?)";
	private static final String DEVICE_BRAND_QUERY = " and iv.device_brand_id = ?";
	private static final String DEVICE_MODEL_QUERY = " and iv.device_model_id = ?";
	private static final String INSERT_SALE = "insert into sale (partner_id, employee_id, discounted_cost, stock_id, cost, sale_timestamp)"
			+ " values (?, ?, ?, ?, ?, ?)";
	private static final String ORDER_BY = " order by s.sale_timestamp";

	private BeanPropertyRowMapper<Sale> rowMapper;

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

	@Override
	public void insertSale(Sale sale) {
		getJdbcTemplate().update(INSERT_SALE, sale.getPartnerId() != 0 ? sale.getPartnerId() : null, 
				sale.getEmployeeId(), sale.getDiscountedCost(), sale.getStockId(), 
				sale.getCost(), sale.getSaleTimestamp());
	}

	@Override
	public List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode, Integer deviceBrandId, Integer deviceModelId) {
		String searchQuery = GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY + String.format(KALAFCHE_STORE_CRITERIA_QUERY, kalafcheStoreIds);
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		
		searchQuery += addDetailedSearch(productCode, deviceBrandId, deviceModelId, argsList);
		
		searchQuery += ORDER_BY;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);

		return getJdbcTemplate().query(
				searchQuery, argsArr, getRowMapper());
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
}
