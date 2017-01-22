package com.kalafche.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.UserDao;
import com.kalafche.exceptions.CommonException;
import com.kalafche.model.User;

@Service
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

	private static final String GET_USER_BY_USERNAME = "select * from user where username = ?";
	private static final String GET_USER_INFO_BY_USERNAME = "select u.id as user_id, e.id as employee_id, u.username, e.name, e.kalafche_store_id, e.job_responsibility_id from user u "
	+ "join employee e on u.id = e.user_id where username = ?";
	private static final String INSERT_USER = "insert into user (username, password, enabled) values (?, ?, ?)";
	private static final String DISABLE_USER = "update user set enabled = 0 where id = ?";
	
	private BeanPropertyRowMapper<User> rowMapper;
	
	@Autowired
	public UserDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<User> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<User>(User.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserInfo(String username) {
		User user = null;
		try {
			user = getJdbcTemplate().queryForObject(GET_USER_INFO_BY_USERNAME, getRowMapper(), username);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try {
			user = getJdbcTemplate().queryForObject(GET_USER_BY_USERNAME, getRowMapper(), username);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public int insertUser(User user)  throws CommonException {
		try { 
			Connection connection = getDataSource().getConnection();
			//connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(
						INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setBoolean(3, user.isEnabled());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new CommonException(
						"Creating the new user" + user.getUsername() + "failed, no rows affected.");
			}
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException(
							"Creating user " + user.getUsername() + " failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new CommonException(
					"Creating the new user" + user.getUsername() + "failed, no rows affected.");
		}
	}

	@Override
	public void disableUser(int userId) {
		getJdbcTemplate().update(DISABLE_USER, userId);	
	}

}
