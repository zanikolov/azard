package com.kalafche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.model.Sale;
import com.kalafche.model.SaleReport;
import com.kalafche.service.SaleService;

@CrossOrigin
@RestController
@RequestMapping({ "/sale" })
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@RequestMapping(value = { "" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public SaleReport searchSales(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "kalafcheStoreIds") String kalafcheStoreIds,
			@RequestParam(value = "productCode", required = false) String productCode, @RequestParam(value = "deviceBrandId", required = false) Integer deviceBrandId,
			@RequestParam(value = "deviceModelId", required = false) Integer deviceModelId) {
		return saleService.searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, productCode, deviceBrandId, deviceModelId);
	}
	
	@RequestMapping(value = { "" }, method = { org.springframework.web.bind.annotation.RequestMethod.PUT })
	public void insertSale(@RequestBody Sale sale) {
		this.saleService.submitSale(sale);
	}
}
