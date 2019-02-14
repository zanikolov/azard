package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.Employee;

public interface EmployeeDao {

	public abstract List<Employee> getAllEmployees();
	
	public abstract Employee getEmployee(String username);

	public abstract void insertEmployee(Employee newEmployee);

	public abstract void updateEmployee(Employee employee);

	public abstract List<Employee> getAllActiveEmployees();

	public abstract Boolean getIsEmployeeAdmin(String username);

	public abstract List<Employee> getEmployeesByIds(List<Integer> employeeIds);

}
