package com.azard.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.EmployeeDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.AuthRole;
import com.azard.model.Employee;
import com.azard.service.EmployeeService;
import com.azard.service.LoginHistoryService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Override
	@Transactional
	public Employee getEmployeeInfo() {		
		Employee employee = getLoggedInEmployee();
		if (employee == null) {
			return null;
		}
		
		if(employee.getStoreId() == null) {
			employee.setStoreId(0);
		}
		loginHistoryService.trackLoginHistory(employee.getId());
		
		List<AuthRole> roles = employeeDao.getAllRolesForEmployee(employee.getId());
		employee.setRoles(roles);
		
		return employee;
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	public List<Employee> getAllActiveEmployees() {
		return employeeDao.getAllActiveEmployees();
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);
	}
	
	@Override
	@Transactional
	public void createEmployee(Employee employee) throws SQLException {
		validateUsername(employee);
		//employee.setJobResponsibilityId(1);
		Integer employeeId = employeeDao.insertEmployee(employee);
		employeeDao.insertEmployeeRole(employeeId, "ROLE_USER");
	}
	
	@Override
	public Employee getLoggedInEmployee() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
		return employeeDao.getEmployee(username);
	}
	
	@Override
	public Boolean isLoggedInEmployeeAdmin() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();	
		return employeeDao.getIsEmployeeAdmin(username);
	}
	
	@Override
	public List<Employee> getEmployeesByIds(List<Integer> employeeIds) {
		return employeeDao.getEmployeesByIds(employeeIds);
	}
	
	@Override
	public Employee getEmployeeByUsername(String username) {
		return employeeDao.getEmployee(username);
	}
	
	private void validateUsername(Employee employee) {
		if (employeeDao.checkIfEmployeeUsernameExists(employee)) {
			throw new DuplicationException("username", "Username duplication.");
		}
	}
	
}
