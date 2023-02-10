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

import com.azard.model.Model;
import com.azard.service.ModelService;

@CrossOrigin
@RestController
@RequestMapping({ "/model" })
public class ModelController {
	
	@Autowired
	private ModelService modelService;

	@GetMapping("/byBrand")
	public List<Model> getModelsByBrand(@RequestParam(value = "brandId") int brandId) {
		return modelService.getModelsByBrand(brandId);
	}
	
	@GetMapping
	public List<Model> getAllModels() {
		return modelService.getAllModels();
	}
	
	@PutMapping
	public void submitModel(@RequestBody Model model) {		
		modelService.submitModel(model);
	}
	
	@PostMapping
	public void updateModel(@RequestBody Model model) {		
		modelService.updateModel(model);
	}
	
}
