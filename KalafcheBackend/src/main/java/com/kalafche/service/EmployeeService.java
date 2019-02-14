package com.kalafche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.AuthRoleDao;
import com.kalafche.dao.EmployeeDao;
import com.kalafche.dao.UserDao;
import com.kalafche.dao.UserToRoleDao;
import com.kalafche.exceptions.CommonException;
import com.kalafche.model.AuthRole;
import com.kalafche.model.Employee;
import com.kalafche.model.User;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	AuthRoleDao authRoleDao;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserToRoleDao userToRoleDao;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Transactional
	public Employee getEmployeeInfo(String username) {		
		
		Employee employee = employeeDao.getEmployee(username);
		if (employee == null) {
			return null;
		}
		
		loginHistoryService.trackLoginHistory(employee.getId());
		
		List<AuthRole> roles = authRoleDao.getAllRolesForUser(employee.getUserId());
		employee.setRoles(roles);
		
		return employee;
	}
	
	public void updateEmployeeInfo() {
		
	}
	
	@Transactional
	public void createEmployee(Employee newEmployee) throws CommonException {
		User newUser = new User(newEmployee.getUsername(), newEmployee.getPassword(), true);
		int userId = userDao.insertUser(newUser);
		newEmployee.setUserId(userId);
		userToRoleDao.insertUserToRole(userId, "ROLE_USER");
		employeeDao.insertEmployee(newEmployee);
	}

	public void deactivateAccount(int userId) {
		userDao.disableUser(userId);
	}

	public List<Employee> getAllActiveEmployees() {
		return employeeDao.getAllActiveEmployees();
	}
	
	public Employee getLoggedInEmployee() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return employeeDao.getEmployee(username);
	}
	
	public Boolean isLoggedInEmployeeAdmin() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return employeeDao.getIsEmployeeAdmin(username);
	}
	
	public List<Employee> getEmployeesByIds(List<Integer> employeeIds) {
		return employeeDao.getEmployeesByIds(employeeIds);
	}
	
}
