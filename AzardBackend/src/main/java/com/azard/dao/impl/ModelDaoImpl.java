package com.azard.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.azard.dao.DeviceDao;
import com.azard.model.Model;

@Service
public class ModelDaoImpl extends JdbcDaoSupport implements
		DeviceDao {
	private static final String GET_ALL_MODELS_BY_BRAND_QUERY = "select * from model where brand_id = ?";
	private static final String GET_ALL_MODELS = "select * from model";
	private static final String SELECT_MODEL = "select * from model where id = ?";
	private static final String INSERT_MODEL = "insert into model (name, brand_id) values (?, ?)";
	private static final String UPDATE_MODEL = "update model set name = ? where id = ?";
	private static final String CHECK_IF_MODEL_EXISTS = "select count(*) from model where name = ? and brand_id = ?";
	private static final String ID_CLAUSE = " and id <> ?";
	private static final String SELECT_MODEL_IDS_FOR_FULL_REVISION = "select id from model"; 
	private static final String WHERE_CLAUSE_FOR_DAILY_REVISION = " where id > ? order by id asc limit ?;";
	private static final String SELECT_MODELS_BY_IDS = "select dm.id, db.id as brand_id, concat(db.name, ' ', dm.name) as name from model dm join brand db on dm.brand_id = db.id where dm.id in (%s);";

	private BeanPropertyRowMapper<Model> rowMapper;
	
	@Autowired
	public ModelDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Model> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Model>(Model.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<Model> getModelsByBrand(int brandId) {
		return getJdbcTemplate().query(GET_ALL_MODELS_BY_BRAND_QUERY, getRowMapper(), brandId);
	}

	@Override
	public void insertModel(Model model) {
		getJdbcTemplate().update(INSERT_MODEL, model.getName(), model.getBrandId());
	}

	@Override
	public List<Model> getAllModels() {
		return getJdbcTemplate().query(GET_ALL_MODELS, getRowMapper());
	}

	@Override
	public void updateModel(Model model) {
		getJdbcTemplate().update(UPDATE_MODEL, model.getName(), model.getId());	
	}

	@Override
	public Boolean checkIfModelExists(Model model) {
		Integer exists = null;
		if (model.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_MODEL_EXISTS, Integer.class, model.getName(), model.getBrandId());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_MODEL_EXISTS + ID_CLAUSE, Integer.class, model.getName(), model.getBrandId(), model.getId());
		}
			
		return exists != null && exists > 0 ;
	}
	
	@Override
	public Model selectModel(Integer modelId) {
		List<Model> model = getJdbcTemplate().query(SELECT_MODEL, getRowMapper(), modelId);
		
		return model.isEmpty() ? null : model.get(0);
	}

	@Override
	public List<Integer> getModelIdsForDailyRevision(Integer start, Integer count) {
		return getJdbcTemplate().queryForList(SELECT_MODEL_IDS_FOR_FULL_REVISION + WHERE_CLAUSE_FOR_DAILY_REVISION, Integer.class, start, count);
	}
	
	@Override
	public List<Integer> getModelIdsForFullRevision() {
		return getJdbcTemplate().queryForList(SELECT_MODEL_IDS_FOR_FULL_REVISION, Integer.class);
	}

	@Override
	public List<Model> getModelsByIds(List<Integer> modelIds) {
		String commaSeparatedModelIds = modelIds.stream().map(id -> id.toString())
				.collect(Collectors.joining(","));
		return getJdbcTemplate().query(String.format(SELECT_MODELS_BY_IDS, commaSeparatedModelIds), getRowMapper());
	}
	
}
