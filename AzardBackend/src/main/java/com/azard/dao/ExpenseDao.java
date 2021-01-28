package com.azard.dao;

import java.util.List;

import com.azard.model.Expense;
import com.azard.model.ExpenseType;

public interface ExpenseDao {

	public void insertExpense(Expense expense);
	
	public List<ExpenseType> selectExpenseTypes(Boolean isAdmin);

	public List<Expense> searchExpenses(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds);
	
}
