package com.azard.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azard.dao.EmployeeDao;
import com.azard.model.Employee;
import com.azard.model.LoginHistory;
import com.azard.service.EmployeeService;
import com.azard.service.LoginHistoryService;

@CrossOrigin
@RestController
@RequestMapping({ "/employee" })
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@GetMapping("/login")
	public Employee loginEmployee() {
		Employee employee = employeeService.getEmployeeInfo();
		
		return employee;
	}
	@GetMapping
	public List<Employee> getAllEmployee() {
		List<Employee> employeeList = employeeService.getAllEmployees();
		
		return employeeList;
	}
	
	@GetMapping("/enabled")
	public List<Employee> getAllActiveEmployees() {	
		return employeeService.getAllActiveEmployees();
	}
	
	@GetMapping("/firstLoginForDate")
	public List<LoginHistory> getFirstLoginForDate(@RequestParam(value = "dateMillis")  long dateMillis) {
		return loginHistoryService.getLoginHistoryRecords(dateMillis);
	}
	
	@PutMapping
	public void createEmployee(@RequestBody Employee employee) throws SQLException {
		employeeService.createEmployee(employee);
	}
	
	@PostMapping
	public void updateEmployee(@RequestBody Employee employee) {
		employeeDao.updateEmployee(employee);		
	}	

}
