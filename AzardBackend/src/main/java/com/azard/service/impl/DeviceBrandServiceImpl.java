package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.BrandDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Brand;
import com.azard.service.BrandService;

@Service
public class DeviceBrandServiceImpl implements BrandService {

	@Autowired
	BrandDao brandDao;
	
	@Override
	public List<Brand> getAllBrands() {
		return brandDao.getAllBrands();
	}

	@Override
	public void insertBrand(Brand brand) {
		validateBrand(brand);
		brandDao.insertBrand(brand);
	}

	private void validateBrand(Brand brand) {
		if(brandDao.checkIfBrandExists(brand)) {
			throw new DuplicationException("name", "Name duplicate.");
		}
	}

}
