package com.kalafche.dao;

public interface UserToRoleDao {

	public abstract void  insertUserToRole(int userId, String authRoleName);
	
}
