package com.kalafche.dao;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.AuthRole;
import com.kalafche.model.Employee;

public interface EmployeeDao {

	public abstract List<Employee> getAllEmployees();
	
	public abstract Employee getEmployee(String username);

	public abstract Integer insertEmployee(Employee newEmployee) throws SQLException;

	public abstract void updateEmployee(Employee employee);

	public abstract List<Employee> getAllActiveEmployees();

	public abstract Boolean getIsEmployeeAdmin(String username);

	public abstract List<Employee> getEmployeesByIds(List<Integer> employeeIds);

	public void insertEmployeeRole(Integer employeeId, String authRoleName);

	public List<AuthRole> getAllRolesForEmployee(Integer employeeId);

	public boolean checkIfEmployeeUsernameExists(Employee employee);

}
