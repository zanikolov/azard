package com.azard.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.azard.model.ExpenseReport;
import com.azard.model.ExpenseType;

public interface ExpenseService {

	public void createExpense(String typeCode, String description, BigDecimal price, Integer storeId,
			MultipartFile expenseImage);

	public List<ExpenseType> getExpenseTypes();

	public ExpenseReport searchExpenses(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds);

}
