package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.DeviceModel;

public interface DeviceModelDao {
	public abstract List<DeviceModel> getDeviceModelsByBrand(int brandId);

	public abstract void insertModel(DeviceModel model);

	public abstract List<DeviceModel> getAllDeviceModels();

	public abstract void updateModel(DeviceModel model);
	
	public abstract Boolean checkIfDeviceModelExists(DeviceModel model);
	
	public abstract DeviceModel selectDeviceModel(Integer deviceModelId);
	
}
