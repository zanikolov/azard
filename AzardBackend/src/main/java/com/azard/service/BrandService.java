package com.azard.service;

import java.util.List;

import com.azard.model.Brand;

public interface BrandService {

	List<Brand> getAllBrands();

	void insertBrand(Brand brand);

}
