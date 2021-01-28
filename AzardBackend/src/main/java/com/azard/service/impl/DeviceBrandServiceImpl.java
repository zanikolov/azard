package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.DeviceBrandDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.DeviceBrand;
import com.azard.service.DeviceBrandService;

@Service
public class DeviceBrandServiceImpl implements DeviceBrandService {

	@Autowired
	DeviceBrandDao deviceBrandDao;
	
	@Override
	public List<DeviceBrand> getAllBrands() {
		return deviceBrandDao.getAllBrands();
	}

	@Override
	public void insertBrand(DeviceBrand brand) {
		validateDeviceBrand(brand);
		deviceBrandDao.insertBrand(brand);
	}

	private void validateDeviceBrand(DeviceBrand brand) {
		if(deviceBrandDao.checkIfDeviceBrandExists(brand)) {
			throw new DuplicationException("name", "Name duplicate.");
		}
	}

}
