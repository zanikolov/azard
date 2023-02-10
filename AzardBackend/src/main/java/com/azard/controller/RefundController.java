package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.Refund;
import com.azard.service.RefundService;

@CrossOrigin
@RestController
@RequestMapping({ "/refund" })
public class RefundController {
	
	@Autowired
	private RefundService refundService;
	
	@GetMapping
	public List<Refund> searchRefunds(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "storeIds") String storeIds,
			@RequestParam(value = "leatherCode", required = false) String leatherCode, @RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "modelId", required = false) Integer modelId) {
		return refundService.searchRefunds(startDateMilliseconds, endDateMilliseconds, storeIds, leatherCode, brandId, modelId);
	}
	
	@PutMapping
	public void submitRefund(@RequestBody Refund refund) {
		refundService.submitRefund(refund);
	}
	
}