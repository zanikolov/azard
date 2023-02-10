package com.azard.dao;

import java.util.List;

import com.azard.model.Brand;

public interface BrandDao {

	public abstract List<Brand> getAllBrands();

	public abstract void insertBrand(Brand brand);

	public abstract boolean checkIfBrandExists(Brand brand);
	
	public abstract Brand selectBrand(Integer brandId);
	
}
