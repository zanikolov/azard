package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.Expense;
import com.kalafche.model.ExpenseType;

public interface ExpenseDao {

	public void insertExpense(Expense expense);
	
	public List<ExpenseType> selectExpenseTypes(Boolean isAdmin);

	public List<Expense> searchExpenses(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds);
	
}
