package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.KalafcheStore;
import com.kalafche.model.StoreDto;

@Service
public class KalafcheStoreDaoImpl extends JdbcDaoSupport implements KalafcheStoreDao {

	private static final String GET_ALL_KALAFCHE_ENTITES = "select * from kalafche_store";
	private static final String INSERT_KALAFCHE_STORE = "insert into kalafche_store (name, city) values (?, ?)";
	private static final String GET_ALL_STORES = "select GROUP_CONCAT(id SEPARATOR ',') as identifiers, 'Всички магазини' as displayName from kalafche_store ks where is_store is true " +
			"union " +
			"select GROUP_CONCAT(id SEPARATOR ',') as identifiers, 'Анико ЕООД' as displayName from kalafche_store ks where ks.own = 'ANIKO' " +
			"union " +
			"select GROUP_CONCAT(id SEPARATOR ',') as identifiers, 'Азард ЕООД' as displayName from kalafche_store ks where ks.own = 'AZARD' " +
			"union " +
			"select id as identifiers, concat(city, ',', name) as displayName from kalafche_store ks " +
			"where is_store is true; ";

	private BeanPropertyRowMapper<KalafcheStore> rowMapper;
	
	private BeanPropertyRowMapper<StoreDto> storeDtoRowMapper;
	
	@Autowired
	public KalafcheStoreDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<KalafcheStore> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<KalafcheStore>(KalafcheStore.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	private BeanPropertyRowMapper<StoreDto> getStoreDtoRowMapper() {
		if (storeDtoRowMapper == null) {
			storeDtoRowMapper = new BeanPropertyRowMapper<StoreDto>(StoreDto.class);
			storeDtoRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return storeDtoRowMapper;
	}
	
	@Override
	public List<KalafcheStore> getAllKalafcheEntities() {
		List<KalafcheStore> kalafcheStores = getJdbcTemplate().query(GET_ALL_KALAFCHE_ENTITES, getRowMapper());

		return kalafcheStores;
	}
	
	
	@Override
	public List<StoreDto> getAllStores() {
		List<StoreDto> storeDtos = getJdbcTemplate().query(GET_ALL_STORES, getStoreDtoRowMapper());

		return storeDtos;
	}

	@Override
	public void insertKalafcheStore(KalafcheStore kalafcheStore) {
		getJdbcTemplate().update(INSERT_KALAFCHE_STORE, kalafcheStore.getName(), kalafcheStore.getCity());		
	}

}
