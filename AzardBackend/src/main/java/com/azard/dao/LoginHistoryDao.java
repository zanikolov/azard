package com.azard.dao;

import java.util.List;

import com.azard.model.LoginHistory;

public interface LoginHistoryDao {
	
	void insertLoginHistoryRecord(int employeeId, long loginTimestamp);
	
	List<LoginHistory> selectFirstLoginForDate(long dateMillis);

}
