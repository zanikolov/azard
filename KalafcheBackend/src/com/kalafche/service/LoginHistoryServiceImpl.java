package com.kalafche.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalafche.dao.LoginHistoryDao;
import com.kalafche.model.LoginHistory;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
	
	@Autowired
	LoginHistoryDao loginHistoryDao;
	
	@Override
	public void trackLoginHistory(int employeeId) {	
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Sofia"));
		long loginTimestamp = cal.getTimeInMillis();
		
		loginHistoryDao.insertLoginHistoryRecord(employeeId, loginTimestamp);	
	}

	@Override
	public List<LoginHistory> getLoginHistoryRecords(long dateMillis) {
		return loginHistoryDao.selectFirstLoginForDate(dateMillis);
	}

}
