package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.StoreDto;

@Service
public class KalafcheStoreDaoImpl extends JdbcDaoSupport implements KalafcheStoreDao {

	private static final String SELECT_STORE_BY_ID = "select * from kalafche_store where id = ?";
	private static final String GET_ALL_KALAFCHE_ENTITES = "select * from kalafche_store";
	private static final String SELECT_STORES = "select * from kalafche_store where is_store is true";
	private static final String SELECT_STORE_IDS_BY_OWNER = "select GROUP_CONCAT(id) from kalafche_store where own = ?";
	private static final String SELECT_ALL_STORE_IDS = "select GROUP_CONCAT(id) from kalafche_store";
	private static final String INSERT_KALAFCHE_STORE = "insert into kalafche_store (name, city) values (?, ?)";

	private BeanPropertyRowMapper<StoreDto> rowMapper;
	
	@Autowired
	public KalafcheStoreDaoImpl(DataSource dataSource) {
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
	public List<StoreDto> getAllKalafcheEntities() {
		return getJdbcTemplate().query(GET_ALL_KALAFCHE_ENTITES, getRowMapper());
	}

	@Override
	public void insertKalafcheStore(StoreDto kalafcheStore) {
		getJdbcTemplate().update(INSERT_KALAFCHE_STORE, kalafcheStore.getName(), kalafcheStore.getCity());		
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

}
