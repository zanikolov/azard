package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.Brand;
import com.azard.service.BrandService;

@CrossOrigin
@RestController
@RequestMapping({ "/brand" })
public class BrandController {
	
	@Autowired
	private BrandService brandService;

	@GetMapping
	public List<Brand> getAllBrands() {
		return brandService.getAllBrands();
	}

	@PutMapping
	public void insertBrand(@RequestBody Brand brand) {
		brandService.insertBrand(brand);
	}
	
}
