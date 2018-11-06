package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.model.Stock;

@Service
public class StockDaoImpl extends JdbcDaoSupport {

	private static final String UPSERT_APPROVED_IN_STOCK = "insert into stock "
			+ "(item_id, kalafche_store_id, quantity, approved) values " + "(?, ?, ?, true) "
			+ "on duplicate key update quantity = quantity + ?";

	private static final String GET_ALL_APPROVED_STOCKS_FOR_STORES = "select " +
			"s.ID, " +
			"iv.id as item_id, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"coalesce(psp.specific_price, iv.product_price) as product_price, " +
			"ks.ID as kalafche_store_id, " +
			"CONCAT(ks.CITY,',',ks.NAME) as kalafche_store_name, " +
			"s.QUANTITY, " +
			"ws.quantity as extraQuantity, " +
			"sum(sr2.quantity) as orderedQuantity " +
			"from stock s " +
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " +
			"join item_vw iv on s.ITEM_ID=iv.ID " +
			"left join stock ws on ws.item_id=iv.ID " +
			"and ws.kalafche_store_id=4 " +
			"and ws.approved=true " +
			"left join " +
			"( " +
			"   select " +
			"   sr1.quantity, " +
			"   sr1.dest_store_id, " +
			"   st3.product_id, " +
			"   st3.device_model_id " +
			"   from relocation sr1 " +
			"   join stock st3 on sr1.stock_id=st3.id " +
			"   where sr1.source_store_id=4 " +
			"   and sr1.arrived=false " +
			"   and sr1.archived=false " +
			"   and (sr1.rejected=false or sr1.rejected is null) " +
			") " +
			"sr2 on sr2.product_id=iv.product_id " +
			"and sr2.device_model_id=iv.device_model_id " +
			"and ks.id=sr2.dest_store_id " +
			"left join product_specific_price psp on psp.product_id = iv.product_id and psp.store_id = ks.id " +
			"where s.approved is true " +
			"and ks.CODE <> 'RU_WH' ";
	private static final String BY_STORE_CLAUSE = "and ks.ID = ? ";

	private static final String GROUP_BY_CLAUSE = "group by s.id ";

	private static final String GET_ALL_APPROVED_STOCKS_FROM_WH = "select " + 
			"s.ID, " + 
			"iv.id as item_id, " + 
			"iv.device_brand_id, " + 
			"iv.device_model_id, " + 
			"iv.device_model_name, " + 
			"iv.product_id, " + 
			"iv.product_code, " + 
			"iv.product_name, " + 
			"coalesce(psp.specific_price, iv.product_price) as product_price, " +
			"ks.ID as kalafche_store_id, " + 
			"CONCAT(ks.CITY,\",\",ks.NAME) as kalafche_store_name, " + 
			"s.QUANTITY, " + 
			"s.approved, " + 
			"s.approver, " + 
			"es.quantity as extraQuantity, " + 
			"sum(sr2.quantity) as orderedQuantity " + 
			"from stock s " + 
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " + 
			"join item_vw iv on s.ITEM_ID=iv.ID " + 
			"left join stock es on es.item_id=iv.ID and es.kalafche_store_id=? and es.approved=true " + 
			"left join " + 
			"( " + 
			"   select " + 
			"   sr1.quantity, " + 
			"   sr1.dest_store_id, " + 
			"   st3.product_id, " + 
			"   st3.device_model_id " + 
			"   from relocation sr1 " + 
			"   join stock st3 on sr1.stock_id=st3.id " + 
			"   where sr1.source_store_id=4 " + 
			"   and sr1.arrived=false " + 
			"   and sr1.archived=false " + 
			"   and (sr1.rejected=false or sr1.rejected is null) " + 
			") " + 
			"sr2 on sr2.product_id=iv.product_id " + 
			"and sr2.device_model_id=iv.device_model_id " + 
			"and sr2.dest_store_id=? " + 
			"left join product_specific_price psp on psp.product_id = iv.product_id and psp.store_id = ks.id " +
			"where s.approved is true " + 
			"and ks.CODE = 'RU_WH' ";

	private static final String ORDER_BY_CLAUSE = "order by iv.device_model_name, iv.product_id, kalafche_store_id ";

	private static final String UPDATE_QUANTITY_OF_SOLD_STOCK = "update stock set quantity = quantity - 1 where item_id = ? and kalafche_store_id = ?";

	private static final String GET_QUANTITY_OF_STOCK_IN_WH = "select " +
			"coalesce(( " +
			"	select quantity " +
			"	from stock st " +
			"	join item_vw iv on st.item_id = iv.id " +
			"	join kalafche_store ks on st.kalafche_store_id = ks.id " +
			"	where iv.product_code = ? " +
			"	and iv.device_model_id = ? " +
			"	and ks.code = 'RU_WH' " +
			"	and approved = true) " +
			", 0); ";;

	private static final String GET_COMPANY_QUANTITY_OF_STOCK = "select coalesce((select " +
			"sum(st.quantity) " +
			"from stock st " +
			"join item_vw iv on st.item_id = iv.id " +
			"join kalafche_store ks on ks.id = st.kalafche_store_id " +
			"where iv.product_code = ? " +
			"and iv.device_model_id = ? " +
			"and ks.code != 'RU_KAUFL_1' " +
			"and ks.code != 'RU_KAUFL_2' " +
			"and ks.code != 'RU_DEF' " +
			"and ks.code != 'RU_OS'), 0); ";

	private static final String UPDATE_QUANTITY_OF_APPROVED_STOCK = "update stock set quantity = quantity + ? where item_id = ? and kalafche_store_id = ?";

	private static final String GET_ALL_STOCKS_FOR_REPORT = "select " +
			"s.ID, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.PRODUCT_CODE as product_code, " +
			"iv.PRODUCT_NAME as product_name, " +
			"iv.PRODUCT_PRICE as product_price, " +
			"os.QUANTITY as ordered_quantity, " +
			"sum(s.QUANTITY) as quantity " +
			"from stock s " +
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " +
			"join item_vw iv on s.ITEM_ID=iv.ID " +
			"left join ordered_stock os on os.product_id = iv.product_id " +
			"and os.DEVICE_MODEL_ID = iv.device_model_id " +
			"and os.STOCK_ORDER_ID = ? " +
			"where s.approved is true " +
			"group by iv.PRODUCT_CODE,iv.PRODUCT_NAME,iv.device_model_name " +
			"order by iv.device_model_name,quantity desc ";

	private BeanPropertyRowMapper<Stock> rowMapper;

	@Autowired
	public StockDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	private BeanPropertyRowMapper<Stock> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Stock>(Stock.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return rowMapper;
	}

	public void insertOrUpdateQuantityOfInStock(Integer itemId, Integer storeId, Integer quantity) {
		getJdbcTemplate().update(UPSERT_APPROVED_IN_STOCK, itemId, storeId, quantity, quantity);
	}

	public List<Stock> getAllApprovedStocks(int userKalafcheStoreId, int selectedKalafcheStoreId) {
		if (selectedKalafcheStoreId == 0) {
			List<Stock> stocks = getJdbcTemplate()
					.query(GET_ALL_APPROVED_STOCKS_FOR_STORES + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE, getRowMapper());
			stocks.addAll(getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FROM_WH + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE,
					new Object[] { userKalafcheStoreId, userKalafcheStoreId }, getRowMapper()));

			return stocks;

		} else if (selectedKalafcheStoreId == 4) {
			return getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FROM_WH + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE,
					new Object[] { userKalafcheStoreId, userKalafcheStoreId }, getRowMapper());
		} else {
			return getJdbcTemplate().query(
					GET_ALL_APPROVED_STOCKS_FOR_STORES + BY_STORE_CLAUSE + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE,
					new Object[] { selectedKalafcheStoreId }, getRowMapper());
		}
	}

	public void updateTheQuantitiyOfSoldStock(int itemId, int kalafceStoreId) {
		getJdbcTemplate().update(UPDATE_QUANTITY_OF_SOLD_STOCK, itemId, kalafceStoreId);
	}

	public Integer getQuantitiyOfStockInWH(String productCode, int deviceModelId) {
		Integer quantity = getJdbcTemplate().queryForObject(GET_QUANTITY_OF_STOCK_IN_WH, Integer.class,
				new Object[] { productCode, deviceModelId });

		return quantity;
	}

	public Integer getCompanyQuantityOfStock(String productCode, int deviceModelId) {
		Integer quantity = getJdbcTemplate().queryForObject(GET_COMPANY_QUANTITY_OF_STOCK, Integer.class,
				new Object[] { productCode, deviceModelId });

		return quantity;
	}

	public void updateQuantityOfApprovedStock(Integer itemId, Integer storeId, Integer quantity) {
		getJdbcTemplate().update(UPDATE_QUANTITY_OF_APPROVED_STOCK, quantity, itemId, storeId);
	}

	public List<Stock> getAllStocksForReport(int stockOrderId) {
		List<Stock> stocks = getJdbcTemplate().query(GET_ALL_STOCKS_FOR_REPORT, getRowMapper(), stockOrderId);
		;
		return stocks;
	}

}
