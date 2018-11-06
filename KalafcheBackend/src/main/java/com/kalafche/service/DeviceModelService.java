package com.kalafche.service;

import java.util.List;

import com.kalafche.model.DeviceModel;

public interface DeviceModelService {

	List<DeviceModel> getDeviceModelsByBrand(Integer brandId);

	List<DeviceModel> getAllDeviceModels();

	void submitModel(DeviceModel model);

	void updateModel(DeviceModel model);

}
