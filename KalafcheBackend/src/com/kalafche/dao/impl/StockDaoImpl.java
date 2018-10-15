package com.kalafche.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.kalafche.dao.StockDao;
import com.kalafche.model.Stock;

@Service
public class StockDaoImpl extends JdbcDaoSupport implements StockDao {
	private static final String GET_ALL_STOCKS_QUERY = "select * from stock";
	
	private static final String GET_UNAPPROVED_STOCKS_BY_KALAFCHE_STORE_ID = "select " +
			"s.ID, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"iv.product_price, " +
			"ks.ID as kalafche_store_id, " +
			"CONCAT(ks.CITY, \", \", ks.NAME) as kalafche_store_name, " +
			"s.QUANTITY, " +
			"s.approved, " +
			"s.approver, " +
			"coalesce(s2.quantity, 0) as quantity_in_stock " +
			"from stock s " +
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " +
			"join item_vw iv on s.ITEM_ID=iv.ID " +
			"left join (select id, item_id, KALAFCHE_STORE_ID, quantity from stock where approved = true) s2 on s.item_id = s2.item_id and s.KALAFCHE_STORE_ID = s2.KALAFCHE_STORE_ID " +
			"where s.approved is false " +
			"and s.kalafche_store_id = ? ";
	
	private static final String GET_ALL_UNAPPROVED_STOCKS = "select " +
			"s.ID, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"iv.product_price, " +
			"ks.ID as kalafche_store_id, " +
			"CONCAT(ks.CITY, \", \", ks.NAME) as kalafche_store_name, " +
			"s.QUANTITY, " +
			"s.approved, " +
			"s.approver, " +
			"coalesce(s2.quantity, 0) as quantity_in_stock " +
			"from stock s " +
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " +
			"join item_vw iv on s.ITEM_ID=iv.ID " +
			"left join (select id, item_id, KALAFCHE_STORE_ID, quantity from stock where approved = true) s2 on s.item_id = s2.item_id and s.KALAFCHE_STORE_ID = s2.KALAFCHE_STORE_ID " +
			"where s.approved is false ";
	
	private static final String INSERT_APPROVED_STOCK = "insert into stock "
			+ "(item_id, kalafche_store_id, quantity, approved, approver) values "
			+ "(?, ?, ?, ?, ?) "
			+ "on duplicate key update quantity = quantity + ?";
	
	private static final String UPSERT_APPROVED_IN_STOCK = "insert into stock "
			+ "(item_id, kalafche_store_id, quantity, approved) values "
			+ "(?, ?, ?, true) "
			+ "on duplicate key update quantity = quantity + ?";
	
	private static final String INSERT_STOCK_FOR_APPROVAL = "insert into stock "
			+ "(item_id, kalafche_store_id, quantity, approved, approver) values "
			+ "(?, ?, ?, ?, ?);";
	
	private static final String DELETE_STOCK_FOR_APPROVAL = "delete from stock where id = ?";

	private static final String GET_ALL_APPROVED_STOCKS_FOR_STORES = "select " +
			"s.ID, " +
			"iv.id as item_id, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"iv.product_price, " +
			"ks.ID as kalafche_store_id, " +
			"CONCAT(ks.CITY,\",\",ks.NAME) as kalafche_store_name, " +
			"s.QUANTITY, " +
			"s.approved, " +
			"s.approver, " +
			"ws.quantity as extraQuantity, " +
			"sum(sr2.quantity) as orderedQuantity " +
			"from stock s " +
			"join kalafche_store ks on s.KALAFCHE_STORE_ID=ks.ID " +
			"join item_vw iv on s.ITEM_ID=iv.ID " +
			"left join stock ws on ws.item_id=iv.ID and ws.kalafche_store_id=4 and ws.approved=true " +
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
			"where s.approved is true " +
			"and ks.CODE <> 'RU_WH' ";
	
//	private static final String NON_WAREHOUSE_CLAUSE = 		
//			"and ks.CODE <> 'RU_WH' ";
	
	private static final String BY_STORE_CLAUSE = 		
			"and ks.ID = ? ";
	
	private static final String GROUP_BY_CLAUSE = 	
	"group by s.id ";
			
//			"union all " +
	
	private static final String GET_ALL_APPROVED_STOCKS_FROM_WH = "select " +
			"s.ID, " +
			"iv.id as item_id, " +
			"iv.device_brand_id, " +
			"iv.device_model_id, " +
			"iv.device_model_name, " +
			"iv.product_id, " +
			"iv.product_code, " +
			"iv.product_name, " +
			"iv.product_price, " +
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
			"where s.approved is true " +
			"and ks.CODE = 'RU_WH' ";
	
	private static final String ORDER_BY_CLAUSE =
			"order by iv.device_model_name, iv.product_id, kalafche_store_id ";
	
	private static final String UPDATE_QUANTITY_OF_SOLD_STOCK = "update stock set quantity = quantity - 1 where item_id = ? and kalafche_store_id = ?";
	
	private static final String GET_QUANTITY_OF_STOCK_BY_STORE = "select coalesce((select quantity from stock where item_id = ? and kalafche_store_id = ? and approved = true), 0)";
	
	private static final String GET_QUANTITY_OF_STOCK_IN_WH = "select coalesce((select quantity from stock st join item_vw iv on st.item_id = iv.id join kalafche_store ks on st.kalafche_store_id = ks.id where iv.product_code = ? and iv.device_model_id = ? and ks.code = 'RU_WH' and approved = true), 0); ";
	
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
	
	private static final String GET_STOCK_BY_ID = "select * from stock where id = ?";
	
	private static final String GET_STOCK_BY_INFO = "select * from stock st join item_vw iv on st.item_id = iv.id where st.kalafche_store_id = ? and iv.device_model_id = ? and iv.product_code = ?";

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
			"left join ordered_stock os on os.product_id = iv.product_id and os.DEVICE_MODEL_ID = iv.device_model_id and os.STOCK_ORDER_ID = ? " +
			"where s.approved is true " +
			"group by iv.PRODUCT_CODE, iv.PRODUCT_NAME, iv.device_model_name " +
			"order by iv.device_model_name, quantity desc ";

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

	@Override
	public List<Stock> getAllStocks() {
		List<Stock> stocks = getJdbcTemplate().query(GET_ALL_STOCKS_QUERY,
				getRowMapper());

		return stocks;
	}
	
	@Override
	public List<Stock> getUnapprovedStocksByKalafcheStoreId(int kalafcheStoreId) {
		User loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = loggedUser.getAuthorities();
		
		boolean isAdmin = false;
		for (GrantedAuthority authority : authorities) {
			isAdmin = authority.getAuthority().equals("ROLE_ADMIN");
			if (isAdmin) {
				break;
			}
		}		
		
		List<Stock> stocks = new ArrayList<Stock>();
		if (isAdmin) {
			stocks = getJdbcTemplate().query(GET_ALL_UNAPPROVED_STOCKS,
					getRowMapper());
		} else {
			stocks = getJdbcTemplate().query(GET_UNAPPROVED_STOCKS_BY_KALAFCHE_STORE_ID,
					getRowMapper(), kalafcheStoreId);	
		}

		return stocks;
	}

	@Override
	public void insertStockForApproval(Stock stock) throws DuplicateKeyException {
		getJdbcTemplate().update(INSERT_STOCK_FOR_APPROVAL, 
				stock.getItemId(),
				stock.getKalafcheStoreId(), stock.getQuantity(), 
				stock.isApproved(), stock.getApprover());
	}
	
	@Override
	public void deleteStocksForApproval(List<Stock> stocks) {
		for (Stock stock : stocks) {
			getJdbcTemplate().update(DELETE_STOCK_FOR_APPROVAL, stock.getId());
		}
	}
	
	@Override
	public void approveStockForApproval(Stock stock) {
			stock.setApproved(true);
			getJdbcTemplate().update(INSERT_APPROVED_STOCK, 
					stock.getItemId(),
					stock.getKalafcheStoreId(), stock.getQuantity(), 
					stock.isApproved(), stock.getApprover(), stock.getQuantity());	
	}
	
	
	@Override
	public void insertOrUpdateQuantityOfApprovedStock(Integer itemId, Integer storeId, Integer quantity) {
			getJdbcTemplate().update(UPSERT_APPROVED_IN_STOCK, 
					itemId, storeId, quantity, quantity);	
	}

	public List<Stock> getAllApprovedStocks(int userKalafcheStoreId, int selectedKalafcheStoreId) {
		if (selectedKalafcheStoreId == 0) {
			List<Stock> stocks = getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FOR_STORES + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE,
					getRowMapper());
			stocks.addAll(getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FROM_WH + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE, 
					new Object[]{userKalafcheStoreId, userKalafcheStoreId}, getRowMapper()));

			return stocks;
			
		} else if (selectedKalafcheStoreId == 4) {
			return getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FROM_WH + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE, 
					new Object[]{userKalafcheStoreId, userKalafcheStoreId}, getRowMapper());
		} else {
			return getJdbcTemplate().query(GET_ALL_APPROVED_STOCKS_FOR_STORES + BY_STORE_CLAUSE + GROUP_BY_CLAUSE + ORDER_BY_CLAUSE, new Object[]{selectedKalafcheStoreId},
					getRowMapper());
		}
	}
	
//	@Override
//	public List<Stock> searchStocks(int deviceBrandId, int deviceModelId,
//			int stockTypeId) {
//		List<Stock> stocks;
//		List<Integer> args = new ArrayList<Integer>();
//
//		StringBuilder query = new StringBuilder(GET_ALL_STOCKS_QUERY);
//
//		if (deviceBrandId != 0 || deviceModelId != 0 || stockTypeId != 0) {
//			boolean shouldAppendAnd = false;
//			query.append(" WHERE");
//
//			if (deviceBrandId != 0) {
//				query.append(" DEVICE_BRAND_ID = ?");
//				args.add(deviceBrandId);
//				shouldAppendAnd = true;
//			}
//
//			if (deviceModelId != 0) {
//				if (shouldAppendAnd) {
//					query.append(" and");
//				}
//				query.append(" DEVICE_MODEL_ID = ?");
//				args.add(deviceModelId);
//			}
//
//			if (stockTypeId != 0) {
//				if (shouldAppendAnd) {
//					query.append(" and");
//				}
//				query.append(" PRODUCT_TYPE_ID = ?");
//				args.add(stockTypeId);
//			}
//
//			stocks = getJdbcTemplate().query(query.toString(),
//					args.toArray(new Integer[args.size()]), getRowMapper());
//		} else {
//			stocks = getJdbcTemplate().query(GET_ALL_STOCKS_QUERY,
//					getRowMapper());
//		}
//
//		return stocks;
//	}

	@Override
	public void updateTheQuantitiyOfSoldStock(int itemId, int kalafceStoreId) {
		getJdbcTemplate().update(UPDATE_QUANTITY_OF_SOLD_STOCK, itemId, kalafceStoreId);
	}
	
	public Integer getQuantitiyOfStock(int itemId, int kalafcheStoreId) {
		Integer quantity = getJdbcTemplate().queryForObject(GET_QUANTITY_OF_STOCK_BY_STORE, Integer.class, new Object[] {itemId, kalafcheStoreId});
		
		return quantity;
	}
	
	@Override
	public Integer getQuantitiyOfStockInWH(String productCode, int deviceModelId) {
		Integer quantity = getJdbcTemplate().queryForObject(GET_QUANTITY_OF_STOCK_IN_WH, Integer.class, new Object[] {productCode, deviceModelId});
		
		return quantity;
	}
	
	@Override
	public Integer getCompanyQuantityOfStock(String productCode, int deviceModelId) {
		Integer quantity = getJdbcTemplate().queryForObject(GET_COMPANY_QUANTITY_OF_STOCK, Integer.class, new Object[] {productCode, deviceModelId});
		
		return quantity;
	}

	@Override
	public void updateQuantityOfApprovedStock(Integer itemId, Integer storeId, Integer quantity) {
		getJdbcTemplate().update(UPDATE_QUANTITY_OF_APPROVED_STOCK, quantity, itemId, storeId);
	}

	@Override
	public Stock getStockById(int stockId) {
		List<Stock> stocks = getJdbcTemplate().query(GET_STOCK_BY_ID, getRowMapper(), stockId);
		
		return stocks.get(0);
	}
	
	@Override
	public Stock getStockByInfo(int kalafcheStoreId, int deviceModelId, String productCode) {
		List<Stock> stocks = getJdbcTemplate().query(GET_STOCK_BY_INFO, getRowMapper(), kalafcheStoreId, deviceModelId, productCode);
		
		return stocks.get(0);
	}

	@Override
	public List<Stock> getAllStocksForReport(int stockOrderId) {
		List<Stock> stocks = getJdbcTemplate().query(GET_ALL_STOCKS_FOR_REPORT,
				getRowMapper(), stockOrderId);;
		return stocks;
	}

}
