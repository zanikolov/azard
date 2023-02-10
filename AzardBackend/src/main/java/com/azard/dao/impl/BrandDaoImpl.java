package com.azard.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.azard.dao.BrandDao;
import com.azard.model.Brand;

@Service
public class BrandDaoImpl extends JdbcDaoSupport implements
		BrandDao {
	private static final String GET_ALL_BRANDS_QUERY = "select * from brand";
	private static final String INSERT_BRAND = "insert into brand (name) values (?)";
	private static final String CHECK_IF_BRAND_EXISTS = "select count(*) from brand where name = ?";
	private static final String SELECT_BRAND = "select * from brand where id = ?";

	private BeanPropertyRowMapper<Brand> rowMapper;
	
	@Autowired
	public BrandDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Brand> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Brand>(Brand.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	public List<Brand> getAllBrands() {		
		List<Brand> brands = getJdbcTemplate().query(GET_ALL_BRANDS_QUERY, getRowMapper());

		return brands;
	}

	@Override
	public void insertBrand(Brand brand) {
		getJdbcTemplate().update(INSERT_BRAND, brand.getName());
	}

	@Override
	public boolean checkIfBrandExists(Brand brand) {
		Integer exists = getJdbcTemplate().queryForObject(CHECK_IF_BRAND_EXISTS, Integer.class, brand.getName());
		
		return exists != null && exists > 0 ;
	}

	@Override
	public Brand selectBrand(Integer brandId) {
		List<Brand> brand = getJdbcTemplate().query(SELECT_BRAND, getRowMapper(), brandId);
		
		return brand.isEmpty() ? null : brand.get(0);
	}
}
