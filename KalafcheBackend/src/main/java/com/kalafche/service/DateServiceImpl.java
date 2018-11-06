package com.kalafche.service;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {

	@Override
	public long getCurrentMillisBGTimezone() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Sofia"));
		return cal.getTimeInMillis();
	}

}
