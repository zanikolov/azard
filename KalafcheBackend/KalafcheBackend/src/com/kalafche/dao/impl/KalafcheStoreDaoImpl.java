package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.KalafcheStore;

@Service
public class KalafcheStoreDaoImpl extends JdbcDaoSupport implements KalafcheStoreDao {

	private static final String GET_ALL_KALAFCHE_STORES = "select * from kalafche_store";

	private BeanPropertyRowMapper<KalafcheStore> rowMapper;
	
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
	
	@Override
	public List<KalafcheStore> getAllKalafcheStores() {
		List<KalafcheStore> kalafcheStores = getJdbcTemplate().query(GET_ALL_KALAFCHE_STORES, getRowMapper());

		return kalafcheStores;
	}

}
