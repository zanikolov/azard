package com.azard.service;

import java.util.List;

import com.azard.model.LoginHistory;

public interface LoginHistoryService {

	void trackLoginHistory(int employeeId);
	
	List<LoginHistory> getLoginHistoryRecords(long dateMillis);
	
}
