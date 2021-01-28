package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.LoginHistoryDao;
import com.azard.model.LoginHistory;
import com.azard.service.DateService;
import com.azard.service.LoginHistoryService;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
	
	@Autowired
	LoginHistoryDao loginHistoryDao;
	
	@Autowired
	DateService dateService;
	
	@Override
	public void trackLoginHistory(int employeeId) {	
		long loginTimestamp = dateService.getCurrentMillisBGTimezone();
		loginHistoryDao.insertLoginHistoryRecord(employeeId, loginTimestamp);	
	}

	@Override
	public List<LoginHistory> getLoginHistoryRecords(long dateMillis) {
		return loginHistoryDao.selectFirstLoginForDate(dateMillis);
	}

}
