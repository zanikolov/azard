package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.DeviceBrand;
import com.azard.service.DeviceBrandService;

@CrossOrigin
@RestController
@RequestMapping({ "/deviceBrand" })
public class DeviceBrandController {
	
	@Autowired
	private DeviceBrandService deviceBrandService;

	@GetMapping
	public List<DeviceBrand> getAllDeviceBrands() {
		return deviceBrandService.getAllBrands();
	}

	@PutMapping
	public void insertBrand(@RequestBody DeviceBrand brand) {
		deviceBrandService.insertBrand(brand);
	}
	
}
