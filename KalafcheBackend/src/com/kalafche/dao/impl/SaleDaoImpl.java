package com.kalafche.dao.impl;

import java.util.ArrayList;
import java.util.Date;
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
			"s.id, s.sale_timestamp, s.employee_id, s.stock_id, s.cost, " +
			"case s.discounted_cost " +
			"	when 0 then s.cost " +
			"	else s.discounted_cost " +
			"end as sale_price, " +
			"e.name as employee_name, pr.name as product_name, pr.code as product_code, CONCAT(ks.city, \", \", ks.name) as kalafche_store_name, " +
			"ks.id as kalafche_store_id, " +
			"dm.id as device_model_id, dm.name as device_model_name, db.id as device_brand_id, db.name as device_brand_name, " +
			"p.code as partner_code " +
			"from sale s " +
			"join employee e on s.employee_id = e.id " +
			"join stock st on s.stock_id = st.id " +
			"join product pr on st.product_id = pr.id " +
			"join kalafche_store ks on st.KALAFCHE_STORE_ID = ks.ID " +
			"join device_model dm on st.device_model_id = dm.id " +
			"join device_brand db on dm.device_brand_id = db.id " +
			"left join partner p on p.id = s.partner_id ";

	private static final String PERIOD_CRITERIA_QUERY = " where sale_timestamp between ? and ?";
	private static final String KALAFCHE_STORE_CRITERIA_QUERY = " and ks.id in (%s)";
	private static final String PRODUCT_CODE_QUERY = " and pr.code in (?)";
	private static final String DEVICE_BRAND_QUERY = " and db.id = ?";
	private static final String DEVICE_MODEL_QUERY = " and dm.id = ?";
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
	public List<Sale> getAllSales() {
		List<Sale> sales = getJdbcTemplate().query(GET_ALL_SALES_QUERY,
				getRowMapper());

		return sales;
	}

	@Override
	public List<Sale> getAllSalesForPeriod(Date startPeriod, Date endPeriod) {
		List<Sale> salesForPeriod = getJdbcTemplate().query(
				GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY,
				new Date[] { startPeriod, endPeriod }, getRowMapper());

		return salesForPeriod;
	}

	@Override
	public void insertSale(Sale sale) {
		getJdbcTemplate().update(INSERT_SALE, sale.getPartnerId() != 0 ? sale.getPartnerId() : null, 
				sale.getEmployeeId(), sale.getSalePrice(), sale.getStockId(), 
				sale.getCost(), sale.getSaleTimestamp());
	}

	@Override
	public List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode, Integer deviceBrandId, Integer deviceModelId) {
		String searchQuery = GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY + String.format(KALAFCHE_STORE_CRITERIA_QUERY, kalafcheStoreIds);
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);
		//argsList.add(kalafcheStoreIds);

		
		searchQuery += addDetailedSearch(productCode, deviceBrandId, deviceModelId, argsList);
		
		searchQuery += ORDER_BY;
		
		Object[] argsArr = new Object[argsList.size()];
		argsArr = argsList.toArray(argsArr);
		
		//Foo[] array = list.toArray(new Foo[list.size()]);
		
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
