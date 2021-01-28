package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.StoreDao;
import com.kalafche.model.Product;
import com.kalafche.model.StoreDto;

@Service
public class StoreDaoImpl extends JdbcDaoSupport implements StoreDao {

	private static final String SELECT_STORE_BY_ID = "select * from store where id = ?";
	private static final String GET_ALL_ENTITES = "select * from store";
	private static final String SELECT_STORES = "select * from store where is_store is true";
	private static final String SELECT_STORE_IDS_BY_OWNER = "select GROUP_CONCAT(id) from store where own = ?";
	private static final String SELECT_ALL_STORE_IDS = "select GROUP_CONCAT(id) from store";
	private static final String INSERT_STORE = "insert into store (name, city, code, is_store) values (?, ?, ?, true)";
	private static final String UPDATE_STORE = "update store set name = ?, city = ? where id = ?";
	private static final String CHECK_IF_STORE_NAME_EXISTS = "select count(*) from store where name = ? and city = ?";
	private static final String CHECK_IF_STORE_CODE_EXISTS = "select count(*) from store where code = ?";
	private static final String ID_NOT_CLAUSE = " and id <> ?";

	private BeanPropertyRowMapper<StoreDto> rowMapper;
	
	@Autowired
	public StoreDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<StoreDto> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<StoreDto>(StoreDto.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<StoreDto> getAllEntities() {
		return getJdbcTemplate().query(GET_ALL_ENTITES, getRowMapper());
	}

	@Override
	public void insertStore(StoreDto store) {
		getJdbcTemplate().update(INSERT_STORE, store.getName(), store.getCity(), store.getCode());		
	}

	@Override
	public List<StoreDto> selectStores() {
		return getJdbcTemplate().query(SELECT_STORES, getRowMapper());
	}

	@Override
	public String selectStoreIdsByOwner(String owner) {
		if (owner.equals("0")) {
			return getJdbcTemplate().queryForObject(SELECT_ALL_STORE_IDS, String.class);
		} else {
			return getJdbcTemplate().queryForObject(SELECT_STORE_IDS_BY_OWNER, String.class ,owner);
		}
	}

	@Override
	public StoreDto selectStore(String storeId) {
		List<StoreDto> store = getJdbcTemplate().query(SELECT_STORE_BY_ID, getRowMapper(), storeId);
		
		return store.isEmpty() ? null : store.get(0);
	}
	
	@Override
	public Boolean checkIfStoreNameExists(StoreDto store) {
		Integer exists = null;
		if (store.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_STORE_NAME_EXISTS, Integer.class, store.getName(), store.getCity());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_STORE_NAME_EXISTS + ID_NOT_CLAUSE, Integer.class, store.getName(), store.getCity(), store.getId());
		}
			
		return exists != null && exists > 0 ;
	}
	
	@Override
	public Boolean checkIfStoreCodeExists(StoreDto store) {
		Integer exists = null;
		if (store.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_STORE_CODE_EXISTS, Integer.class, store.getCode());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_STORE_CODE_EXISTS + ID_NOT_CLAUSE, Integer.class, store.getCode(), store.getId());
		}
			
		return exists != null && exists > 0 ;
	}

	@Override
	public void updateStore(StoreDto store) {
		getJdbcTemplate().update(UPDATE_STORE, store.getName(), store.getCity(), store.getId());		
	}

}
