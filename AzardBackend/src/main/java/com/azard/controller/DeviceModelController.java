package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.DeviceModel;
import com.azard.service.DeviceModelService;

@CrossOrigin
@RestController
@RequestMapping({ "/deviceModel" })
public class DeviceModelController {
	
	@Autowired
	private DeviceModelService deviceModelService;

	@GetMapping("/byBrand")
	public List<DeviceModel> getModelsByBrand(@RequestParam(value = "brandId") int brandId) {
		return deviceModelService.getDeviceModelsByBrand(brandId);
	}
	
	@GetMapping
	public List<DeviceModel> getAllDeviceModels() {
		return deviceModelService.getAllDeviceModels();
	}
	
	@PutMapping
	public void submitModel(@RequestBody DeviceModel model) {		
		deviceModelService.submitModel(model);
	}
	
	@PostMapping
	public void updateModel(@RequestBody DeviceModel model) {		
		deviceModelService.updateModel(model);
	}
	
}
