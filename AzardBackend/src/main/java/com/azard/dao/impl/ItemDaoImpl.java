package com.azard.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.azard.dao.ItemDao;
import com.azard.model.Item;
import com.azard.model.ItemSpecificPricePerStore;

@Service
public class ItemDaoImpl extends JdbcDaoSupport implements ItemDao {
	
	private static final String GET_ALL_ITEMS = "select * from item_vw";
	private static final String GET_ITEM = "select * from item where leather_id = ? and model_id = ?";
	private static final String GET_ITEM_BY_CRITERIA = "select * from item_vw where leather_id = ? and brand_id = ? and model_id = ?";
	private static final String GET_ITEM_BY_BARCODE = "select * from item_vw where barcode = ?";
	private static final String GET_ITEM_BY_ID = "select * from item where id = ?";
	private static final String INSERT_ITEM = "insert into item(leather_id, model_id, barcode, price) values (?, ?, ?, ?)";
	private static final String UPDATE_ITEM = "update item set barcode = ?, price = ?  where id = ?";
	private static final String UPDATE_ITEM_PRICE = "update item set price = ?  where id = ?";
	private static final String UPDATE_ITEM__BARCODE_BY_PRODUCT_ID_AND_MODEL_ID = "update item set barcode = ?  where leather_id = ? and model_id = ?";
	private static final String GET_ITEM_PRICE_BY_STORE = "select " +
			"coalesce " +
			"( " +
			"   ( " +
			"   	 select " +
			"      specific_price " +
			"      from item_specific_price " +
			"      where store_id = ? and item_id = ?), " +
			"   ( " +
			"      select " +
			"      price " +
			"      from item " +
			"      where id = ?" + 
			"   ) " +
			") ";
	
	private static final String UPSERT_ITEM_SPECIFIC_PRICE = "insert into item_specific_price "
			+ "(item_id, store_id, specific_price) values "
			+ "(?, ?, ?) "
			+ "on duplicate key update specific_price = ?";
	
	private static final String DELETE_ITEM_SPECIFIC_PRICE = "delete from item_specific_price "
			+ " where item_id = ? ";
	
	private static final String GET_ITEM_SPECIFIC_PRICE = "select " +
			"? as item_id, " +
			"isp.specific_price as price, " +
			"concat(ks.city, ', ', ks.name) as store_name, " +
			"ks.id as store_id " +
			"from store ks " +
			"left join item_specific_price isp on isp.store_id = ks.id " +
			"and isp.item_id = ? " +
			"where ks.is_store is true ";
	
	private static final String STORE_ID_CLAUSE = "and ks.id = ? ";
	
	private BeanPropertyRowMapper<Item> rowMapper;
	private BeanPropertyRowMapper<ItemSpecificPricePerStore> specificPriceRowMapper;
	
	@Autowired
	public ItemDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Item> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Item>(Item.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	private BeanPropertyRowMapper<ItemSpecificPricePerStore> getSpecificPriceRowMapper() {
		if (specificPriceRowMapper == null) {
			specificPriceRowMapper = new BeanPropertyRowMapper<ItemSpecificPricePerStore>(ItemSpecificPricePerStore.class);
			specificPriceRowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return specificPriceRowMapper;
	}
	
	@Override
	public Item getItem(Integer leatherId, Integer modelId) {
		List<Item> items = getJdbcTemplate().query(GET_ITEM, getRowMapper(), leatherId, modelId);
		
		return items.isEmpty() ? null : items.get(0);
	}

	@Override
	public List<Item> getAllItems() {
		return getJdbcTemplate().query(GET_ALL_ITEMS, getRowMapper());
	}

	@Override
	public void insertItem(Integer productId, Integer deviceModelId, String barcode, BigDecimal price) {
		getJdbcTemplate().update(INSERT_ITEM, productId, deviceModelId, barcode, price);
	}

	@Override
	public Item getItem(String barcode) {
		List<Item> items = getJdbcTemplate().query(GET_ITEM_BY_BARCODE, getRowMapper(), barcode);
		
		return items.isEmpty() ? null : items.get(0);
	}

	@Override
	public void updateItem(Integer id, String barcode, BigDecimal price) {
		getJdbcTemplate().update(UPDATE_ITEM, barcode, price, id);
	}

	@Override
	public Item getItem(Integer id) {
		List<Item> items =  getJdbcTemplate().query(GET_ITEM_BY_ID, getRowMapper(), id);
		
		return items.isEmpty() ? null : items.get(0);
	}

	@Override
	public BigDecimal getItemPriceByStoreId(Integer itemId, Integer storeId) {
		return getJdbcTemplate().queryForObject(GET_ITEM_PRICE_BY_STORE, BigDecimal.class, storeId, itemId, itemId);
	}

	@Override
	public void updateItemBarcode(Integer productId, Integer deviceModelId, String barcode) {
		getJdbcTemplate().update(UPDATE_ITEM__BARCODE_BY_PRODUCT_ID_AND_MODEL_ID, barcode, productId, deviceModelId);	
	}

	@Override
	public void updateItemSpecificPricePerStore(ItemSpecificPricePerStore specificPrice) {
		getJdbcTemplate().update(UPSERT_ITEM_SPECIFIC_PRICE, 
				specificPrice.getItemId(), specificPrice.getStoreId(), specificPrice.getPrice(), specificPrice.getPrice());
	}
	
	@Override
	public void deleteItemSpecificPricesPerStore(Integer productId) {
		getJdbcTemplate().update(DELETE_ITEM_SPECIFIC_PRICE, productId);
	}
	
	
	@Override
	public ItemSpecificPricePerStore getItemSpecificPricePerStore(Integer itemId, Integer storeId) {
		List<ItemSpecificPricePerStore> result = getJdbcTemplate().query(GET_ITEM_SPECIFIC_PRICE + STORE_ID_CLAUSE, getSpecificPriceRowMapper(), itemId, itemId, storeId);
		
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public List<ItemSpecificPricePerStore> getItemSpecificPricePerStore(Integer itemId) {
		return getJdbcTemplate().query(GET_ITEM_SPECIFIC_PRICE, getSpecificPriceRowMapper(), itemId, itemId);
	}

	@Override
	public void updateItemPrice(Integer id, BigDecimal price) {
		getJdbcTemplate().update(UPDATE_ITEM_PRICE, price, id);
	}

	@Override
	public Item getItem(Integer leatherId, Integer brandId, Integer modelId) {
		List<Item> items = getJdbcTemplate().query(GET_ITEM_BY_CRITERIA, getRowMapper(), leatherId, brandId, modelId);
		
		return items.isEmpty() ? null : items.get(0);
	}
	
}
