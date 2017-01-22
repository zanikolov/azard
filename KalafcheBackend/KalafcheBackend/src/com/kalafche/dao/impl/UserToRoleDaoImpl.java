package com.kalafche.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.UserToRoleDao;

@Service
public class UserToRoleDaoImpl extends JdbcDaoSupport implements UserToRoleDao {

	private static final String INSERT_USER_TO_ROLE = "insert into user_to_role(user_id, auth_role_id) "
			+ "values (?, (select id from auth_role where name = ?))";

	@Autowired
	public UserToRoleDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	@Override
	public void insertUserToRole(int userId, String authRoleName) {
		getJdbcTemplate().update(INSERT_USER_TO_ROLE, userId, authRoleName);
	}

}
