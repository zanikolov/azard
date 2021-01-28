package com.azard.service;

import java.util.List;

import com.azard.model.DeviceModel;

public interface DeviceModelService {

	List<DeviceModel> getDeviceModelsByBrand(Integer brandId);

	List<DeviceModel> getAllDeviceModels();

	void submitModel(DeviceModel model);

	void updateModel(DeviceModel model);

	List<Integer> getDeviceModelIdsForDailyRevision(Integer lastRevisedDevieModelId, Integer count);

	List<Integer> getDeviceModelIdsForFullRevision();

	List<DeviceModel> getDeviceModelsByIds(List<Integer> deviceModelIds);

}
