package com.kalafche.dao.impl;

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
			"e.name as employee_name, i.name as item_name, i.product_code as item_product_code, CONCAT(ks.city, \", \", ks.name) as kalafche_store_name, " +
			"ks.id as kalafche_store_id, " +
			"dm.id as device_model_id, dm.name as device_model_name, db.id as device_brand_id, db.name as device_brand_name " +
			"from sale s " +
			"join employee e on s.employee_id = e.id " +
			"join stock st on s.stock_id = st.id " +
			"join item i on st.item_id = i.id " +
			"join kalafche_store ks on st.KALAFCHE_STORE_ID = ks.ID " +
			"join device_model dm on st.device_model_id = dm.id " +
			"join device_brand db on dm.device_brand_id = db.id ";

	private static final String PERIOD_CRITERIA_QUERY = " where sale_timestamp between ? and ?";
	private static final String KALAFCHE_STORE_CRITERIA_QUERY = " and ks.id = ?";
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
			Long endDateMilliseconds, Integer kalafcheStoreId) {
		String searchQuery = GET_ALL_SALES_QUERY + PERIOD_CRITERIA_QUERY;
		Object[] args = null;
		
		if (kalafcheStoreId == 0) {			
			args = new Object[] {startDateMilliseconds, endDateMilliseconds};
		} else {
			args = new Object[] {startDateMilliseconds, endDateMilliseconds, kalafcheStoreId};
			searchQuery += KALAFCHE_STORE_CRITERIA_QUERY;
			
		}
		
		searchQuery += ORDER_BY;
		
		return getJdbcTemplate().query(
				searchQuery, args, getRowMapper());
	}
}
