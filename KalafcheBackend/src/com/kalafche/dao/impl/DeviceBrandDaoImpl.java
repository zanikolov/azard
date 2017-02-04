package com.kalafche.dao.impl;

import com.kalafche.dao.DeviceBrandDao;
import com.kalafche.model.DeviceBrand;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class DeviceBrandDaoImpl extends JdbcDaoSupport implements
		DeviceBrandDao {
	private static final String GET_ALL_BRANDS_QUERY = "select * from device_brand";
	private static final String INSERT_BRAND = "insert into device_brand (name) values (?)";

	private BeanPropertyRowMapper<DeviceBrand> rowMapper;
	
	@Autowired
	public DeviceBrandDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<DeviceBrand> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<DeviceBrand>(DeviceBrand.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	public List<DeviceBrand> getAllBrands() {		
		List<DeviceBrand> brands = getJdbcTemplate().query(GET_ALL_BRANDS_QUERY, getRowMapper());

		return brands;
	}

	@Override
	public void insertBrand(DeviceBrand brand) {
		getJdbcTemplate().update(INSERT_BRAND, brand.getName());
	}
}
