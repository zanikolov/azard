package com.azard.dao;

import java.util.List;

import com.azard.model.DeviceModel;

public interface DeviceModelDao {
	public abstract List<DeviceModel> getDeviceModelsByBrand(int brandId);

	public abstract void insertModel(DeviceModel model);

	public abstract List<DeviceModel> getAllDeviceModels();

	public abstract void updateModel(DeviceModel model);
	
	public abstract Boolean checkIfDeviceModelExists(DeviceModel model);
	
	public abstract DeviceModel selectDeviceModel(Integer deviceModelId);

	public abstract List<Integer> getDeviceModelIdsForDailyRevision(Integer start, Integer count);

	public abstract List<Integer> getDeviceModelIdsForFullRevision();

	public abstract List<DeviceModel> getDeviceModelsByIds(List<Integer> deviceModelIds);
	
}
