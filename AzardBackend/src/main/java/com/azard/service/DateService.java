package com.azard.service;

public interface DateService {

	public long getCurrentMillisBGTimezone();

	public String convertMillisToDateTimeString(Long millis, Boolean withTime);
}
