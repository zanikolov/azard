package com.kalafche.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.EmployeeDao;
import com.kalafche.exceptions.CommonException;
import com.kalafche.model.Employee;
import com.kalafche.model.LoginHistory;
import com.kalafche.service.EmployeeService;
import com.kalafche.service.LoginHistoryService;

@CrossOrigin
@RestController
@RequestMapping({ "/service/employee" })
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@CrossOrigin
	@GetMapping("/getEmployee")
	public Employee getEmployee() {
		User loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = loggedUser.getUsername(); //get logged in username
		Employee employee = employeeService.getEmployeeInfo(username);
		
		return employee;
	}
	
	@GetMapping("/enabled")
	public List<Employee> getAllActiveEmployees() {	
		return employeeService.getAllActiveEmployees();
	}
	
	@GetMapping("/admin/getEmployeeByName")
	public Employee getEmployeeByName(@RequestParam(value = "username") String username) {
		Employee employee = employeeService.getEmployeeInfo(username);
		
		return employee;
	}
	
	@PostMapping
	public void createEmployee(@RequestBody Employee employee, HttpServletResponse response) {
		
		try {
			employee.setJobResponsibilityId(1);
			employeeService.createEmployee(employee);
		} catch (CommonException e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.BAD_REQUEST.value(), "Error during creation of new employee");
		}
	}
	
	@GetMapping("/all")
	public List<Employee> getAllEmployee() {
		List<Employee> employeeList = employeeDao.getAllEmployees();
		
		return employeeList;
	}
	
	
	@PutMapping
	public void updateEmployee(@RequestBody Employee employee) {
		employeeDao.updateEmployee(employee);		
	}
	
	@PutMapping("/disable")
	public void deactivateAccount(@RequestBody int userId) {
		employeeService.deactivateAccount(userId);		
	}
		
	@GetMapping("/firstLoginForDate")
	public List<LoginHistory> getFirstLoginForDate(@RequestParam(value = "dateMillis")  long dateMillis) {
		return loginHistoryService.getLoginHistoryRecords(dateMillis);
	}
	
}
