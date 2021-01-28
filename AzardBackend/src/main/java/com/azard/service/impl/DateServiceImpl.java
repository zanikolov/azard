package com.azard.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.azard.service.DateService;

@Service
public class DateServiceImpl implements DateService {

	@Override
	public long getCurrentMillisBGTimezone() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Sofia"));
		return cal.getTimeInMillis();
	}

	@Override
	public String convertMillisToDateTimeString(Long millis, Boolean withTime) {
		Date date = new java.util.Date(millis);
		
		SimpleDateFormat sdf = null;
		if (withTime) {
			sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
		} else {
			sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		}
		
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Sofia"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
}
