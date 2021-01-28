package com.azard.dao;

import java.util.List;

import com.azard.model.AuthRole;

public abstract interface AuthRoleDao {
	public abstract List<AuthRole> getAllAuthRoles();

	public abstract List<AuthRole> getAllRolesForUser(int userId);
}
