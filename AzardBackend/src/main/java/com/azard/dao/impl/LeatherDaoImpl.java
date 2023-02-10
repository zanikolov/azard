package com.azard.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.azard.dao.LeatherDao;
import com.azard.model.Leather;

@Service
public class LeatherDaoImpl extends JdbcDaoSupport implements LeatherDao {
	
	private static final String GET_ALL_LEATHER_QUERY = "select * from leather";
	private static final String INSERT_LEATHER = "insert into leather(name, supplier, original_name) values (?, ?, ?)";
	private static final String UPDATE_LEATHER = "update leather set name = ?, supplier = ?, original_name = ? where id = ?";
	private static final String CHECK_IF_LEATHER_NAME_EXISTS = "select count(*) from leather where name = ?";
	private static final String ID_CLAUSE = " and id <> ?";
	
	private BeanPropertyRowMapper<Leather> leatherRowMapper;
	
	@Autowired
	public LeatherDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Leather> getLeatherRowMapper() {
		if (leatherRowMapper == null) {
			leatherRowMapper = new BeanPropertyRowMapper<Leather>(Leather.class);
			leatherRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return leatherRowMapper;
	}
	
	@Override
	public List<Leather> getAllLeathers() {
		return getJdbcTemplate().query(GET_ALL_LEATHER_QUERY, getLeatherRowMapper());
	}

	@Override
	public void insertLeather(Leather leather) {
		getJdbcTemplate().update(INSERT_LEATHER, leather.getName(), leather.getSupplier(), leather.getOriginalName());	
	}
	
	@Override
	public void updateLeather(Leather leather) {
		getJdbcTemplate().update(UPDATE_LEATHER, leather.getName(), leather.getSupplier(), leather.getOriginalName(), leather.getId());
	}

	
	@Override
	public boolean checkIfLeatherNameExists(Leather leather) {
		Integer exists = null;
		if (leather.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_LEATHER_NAME_EXISTS, Integer.class, leather.getName());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_LEATHER_NAME_EXISTS + ID_CLAUSE, Integer.class, leather.getName(), leather.getId());
		}
		
		return exists != null && exists > 0 ;
	}
	
}
