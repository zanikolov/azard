package com.azard.service;

import java.util.List;

import com.azard.model.DeviceBrand;

public interface DeviceBrandService {

	List<DeviceBrand> getAllBrands();

	void insertBrand(DeviceBrand brand);

}
