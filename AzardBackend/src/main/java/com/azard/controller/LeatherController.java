package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.BaseController;
import com.azard.model.Leather;
import com.azard.service.LeatherService;

@CrossOrigin
@RestController
@RequestMapping({ "/leather" })
public class LeatherController extends BaseController {
	
	@Autowired
	private LeatherService leatherService;

	@GetMapping
	public List<Leather> getAllLeathers() {
		List<Leather> leathers = this.leatherService.getAllLeathers();

		return leathers;
	}
	
	@PutMapping
	public void insertLeather(@RequestBody Leather leather) {
		leatherService.submitLeather(leather);
	}
	
	@PostMapping
	public void updateLeather(@RequestBody Leather leather) {
		this.leatherService.updateLeather(leather);
	}
	
}