package com.kalafche.service;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Employee;

public interface EmployeeService {

	Employee getEmployeeInfo();

	List<Employee> getAllEmployees();

	List<Employee> getAllActiveEmployees();

	void createEmployee(Employee employee) throws SQLException;

	Employee getLoggedInEmployee();

	Boolean isLoggedInEmployeeAdmin();

	List<Employee> getEmployeesByIds(List<Integer> employeeIds);

	void updateEmployee(Employee employee);

	Employee getEmployeeByUsername(String username);

}
