package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.ColorDao;
import com.kalafche.model.Color;

@Service
public class ColorDaoImpl extends JdbcDaoSupport implements ColorDao {

	private static final String GET_ALL_COLORS = "select * from color";

	private BeanPropertyRowMapper<Color> rowMapper;
	
	@Autowired
	public ColorDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Color> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Color>(Color.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<Color> getAllColors() {
		List<Color> itemTypes = getJdbcTemplate().query(GET_ALL_COLORS, getRowMapper());

		return itemTypes;
	}

}
