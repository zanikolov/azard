package com.kalafche.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.ExpenseDao;
import com.kalafche.model.Expense;
import com.kalafche.model.ExpenseType;

@Service
public class ExpenseDaoImpl extends JdbcDaoSupport implements ExpenseDao {

	private static final String INSERT_EXPENSE = "insert into expense (create_timestamp, employee_id, price, description, store_id, file_id, type_id)"
			+ " values (?, ?, ?, ?, ?, ?, (select id from expense_type where code = ?))";

	private static final String SELECT_EXPENSE_TYPES = "select * from expense_type ";
	private static final String EXPENSE_TYPES_IS_ADMIN_CLAUSE = " where is_admin = false ";
	
	private static final String GET_EXPENSES_QUERY = "select " +
			"e.id, " +
			"e.price, " +
			"e.description, " +
			"e.create_timestamp as timestamp, " +
			"e.file_id, " +
			"em.id as employee_id, " +
			"em.name as employee_name, " +
			"ks.id as store_id, " +
			"CONCAT(ks.city, ', ', ks.name) as store_name " +
			"from expense e " +
			"join kalafche_store ks on ks.id = e.store_id " +
			"join employee em on em.id = e.employee_id ";
	
	private static final String PERIOD_CRITERIA_QUERY = " where create_timestamp between ? and ?";
	private static final String STORE_CRITERIA_QUERY = " and ks.id in (%s)";
	private static final String ORDER_BY = " order by create_timestamp";
	
	private BeanPropertyRowMapper<Expense> rowMapper;
	private BeanPropertyRowMapper<ExpenseType> expenseTypeRowMapper;
	
	@Autowired
	public ExpenseDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Expense> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Expense>(Expense.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	private BeanPropertyRowMapper<ExpenseType> getExpenseTypeRowMapper() {
		if (expenseTypeRowMapper == null) {
			expenseTypeRowMapper = new BeanPropertyRowMapper<ExpenseType>(ExpenseType.class);
			expenseTypeRowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return expenseTypeRowMapper;
	}
	
	@Override
	public void insertExpense(Expense expense) {
		getJdbcTemplate().update(INSERT_EXPENSE, expense.getTimestamp(), expense.getEmployeeId(), expense.getPrice(),
				expense.getDescription(), expense.getStoreId(), expense.getFileId(), expense.getTypeCode());
	}
	
	@Override
	public List<ExpenseType> selectExpenseTypes(Boolean isAdmin) {
		String query = SELECT_EXPENSE_TYPES;
		
		if (!isAdmin) {
			query += EXPENSE_TYPES_IS_ADMIN_CLAUSE;
		}
		
		return getJdbcTemplate().query(query, getExpenseTypeRowMapper());
	}

	@Override
	public List<Expense> searchExpenses(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds) {
		String searchQuery = GET_EXPENSES_QUERY + PERIOD_CRITERIA_QUERY + String.format(STORE_CRITERIA_QUERY, storeIds)
				+ ORDER_BY;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(startDateMilliseconds);
		argsList.add(endDateMilliseconds);

		return getJdbcTemplate().query(searchQuery, getRowMapper(), startDateMilliseconds, endDateMilliseconds);
	}

}
