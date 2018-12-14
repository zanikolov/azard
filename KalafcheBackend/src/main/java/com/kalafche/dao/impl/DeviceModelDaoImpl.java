package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.DeviceModelDao;
import com.kalafche.model.DeviceModel;

@Service
public class DeviceModelDaoImpl extends JdbcDaoSupport implements
		DeviceModelDao {
	private static final String GET_ALL_MODELS_BY_BRAND_QUERY = "select * from device_model where device_brand_id = ?";
	private static final String GET_ALL_MODELS = "select * from device_model";
	private static final String SELECT_DEVICE_MODEL = "select * from device_model where id = ?";
	private static final String INSERT_MODEL = "insert into device_model (name, device_brand_id) values (?, ?)";
	private static final String UPDATE_MODEL = "update device_model set name = ? where id = ?";
	private static final String CHECK_IF_MODEL_EXISTS = "select count(*) from device_model where name = ? and device_brand_id = ?";
	private static final String ID_CLAUSE = " and id <> ?";

	private BeanPropertyRowMapper<DeviceModel> rowMapper;
	
	@Autowired
	public DeviceModelDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<DeviceModel> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<DeviceModel>(DeviceModel.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<DeviceModel> getDeviceModelsByBrand(int brandId) {
		return getJdbcTemplate().query(GET_ALL_MODELS_BY_BRAND_QUERY, getRowMapper(), brandId);
	}

	@Override
	public void insertModel(DeviceModel model) {
		getJdbcTemplate().update(INSERT_MODEL, model.getName(), model.getDeviceBrandId());
	}

	@Override
	public List<DeviceModel> getAllDeviceModels() {
		return getJdbcTemplate().query(GET_ALL_MODELS, getRowMapper());
	}

	@Override
	public void updateModel(DeviceModel model) {
		getJdbcTemplate().update(UPDATE_MODEL, model.getName(), model.getId());	
	}

	@Override
	public Boolean checkIfDeviceModelExists(DeviceModel model) {
		Integer exists = null;
		if (model.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_MODEL_EXISTS, Integer.class, model.getName(), model.getDeviceBrandId());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_MODEL_EXISTS + ID_CLAUSE, Integer.class, model.getName(), model.getDeviceBrandId(), model.getId());
		}
			
		return exists != null && exists > 0 ;
	}
	
	@Override
	public DeviceModel selectDeviceModel(Integer deviceModelId) {
		List<DeviceModel> deviceModel = getJdbcTemplate().query(SELECT_DEVICE_MODEL, getRowMapper(), deviceModelId);
		
		return deviceModel.isEmpty() ? null : deviceModel.get(0);
	}
}
