package com.azard.dao;

import java.util.List;

import com.azard.model.DeviceType;

public abstract interface DeviceTypeDao {
	public abstract List<DeviceType> getAllDeviceTypes();

	void insertDeviceType(DeviceType type);
}
