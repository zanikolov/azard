package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.DeviceModel;

public interface DeviceModelDao {
	public abstract List<DeviceModel> getModelsByBrand(int brandId);

	public abstract void insertModel(DeviceModel model);

	public abstract List<DeviceModel> getAllDeviceModels();
}
