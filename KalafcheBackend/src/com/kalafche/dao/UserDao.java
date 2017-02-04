package com.kalafche.dao;

import java.util.List;

import com.kalafche.exceptions.CommonException;
import com.kalafche.model.User;

public abstract interface UserDao {
	
	public abstract List<User> getAllUsers();

	public abstract User getUserInfo(String username);

	public abstract User getUserByUsername(String username);
	
	public abstract int insertUser(User user) throws CommonException;

	public abstract void disableUser(int userId);
	
}

