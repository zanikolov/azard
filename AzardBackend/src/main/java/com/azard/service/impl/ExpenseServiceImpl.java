package com.azard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.azard.dao.ExpenseDao;
import com.azard.dao.StoreDao;
import com.azard.model.Employee;
import com.azard.model.Expense;
import com.azard.model.ExpenseReport;
import com.azard.model.ExpenseType;
import com.azard.service.DateService;
import com.azard.service.EmployeeService;
import com.azard.service.ExpenseService;
import com.azard.service.fileutil.ImageUploadService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseDao expenseDao;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DateService dateService;

	@Autowired
	ImageUploadService imageUploadService;

	@Autowired
	StoreDao storeDao;

	@Transactional
	@Override
	public void createExpense(String typeCode, String description, BigDecimal price, Integer storeId,
			MultipartFile expenseImage) {
		Boolean isAdmin = employeeService.isLoggedInEmployeeAdmin();
		Expense expense = new Expense(typeCode, description, price);
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		expense.setEmployeeId(loggedInEmployee.getId());
		
		if (!isAdmin) {
			expense.setStoreId(loggedInEmployee.getStoreId());
		} else {
			expense.setStoreId(storeId);
		}
		
		expense.setTimestamp(dateService.getCurrentMillisBGTimezone());
		if (expenseImage != null) {
			expense.setFileId(imageUploadService.uploadExpenseImage(expenseImage));
		}

		expenseDao.insertExpense(expense);
	}

	@Override
	public List<ExpenseType> getExpenseTypes() {
		Boolean isAdmin = employeeService.isLoggedInEmployeeAdmin();
		return expenseDao.selectExpenseTypes(isAdmin);
	}

	@Override
	public ExpenseReport searchExpenses(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds) {
		ExpenseReport report = new ExpenseReport();
		
		if (storeIds.equals("0") || storeIds.equals("ANIKO") || storeIds.equals("AZARD")) {
			storeIds = storeDao.selectStoreIdsByOwner(storeIds);
		}
		
		List<Expense> expenses = expenseDao.searchExpenses(startDateMilliseconds, endDateMilliseconds, storeIds);
		calculateTotalAmountAndCount(expenses, report);
		report.setExpenses(expenses);
		
		return report;
	}

	private void calculateTotalAmountAndCount(List<Expense> expenses, ExpenseReport expenseReport) {
		BigDecimal totalAmount = expenses.stream()
		        .map(expense -> expense.getPrice())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		expenseReport.setCount(expenses.size());
		expenseReport.setTotalAmount(totalAmount);
	}
	
}
