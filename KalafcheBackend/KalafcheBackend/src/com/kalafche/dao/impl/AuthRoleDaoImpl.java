package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.AuthRoleDao;
import com.kalafche.model.AuthRole;

@Service
public class AuthRoleDaoImpl extends JdbcDaoSupport implements AuthRoleDao {

	private static final String GET_ALL_ROLES_FOR_USER = "select * from auth_role r join user_to_role ur on r.id = ur.auth_role_id where ur.user_id = ?";

	private BeanPropertyRowMapper<AuthRole> rowMapper;
	
	@Autowired
	public AuthRoleDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<AuthRole> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<AuthRole>(AuthRole.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}

	@Override
	public List<AuthRole> getAllAuthRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthRole> getAllRolesForUser(int userId) {
		List<AuthRole> authRoles = getJdbcTemplate().query(GET_ALL_ROLES_FOR_USER, getRowMapper(), userId);

		return authRoles;
	}

}