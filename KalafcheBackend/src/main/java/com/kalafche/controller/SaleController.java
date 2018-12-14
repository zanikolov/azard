package com.kalafche.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;
import com.kalafche.model.SaleReport;
import com.kalafche.model.TotalSumReport;
import com.kalafche.model.TotalSumRequest;
import com.kalafche.service.SaleService;

@CrossOrigin
@RestController
@RequestMapping({ "/sale" })
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@GetMapping
	public SaleReport searchSales(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "storeIds") String storeIds) {
		return saleService.searchSales(startDateMilliseconds, endDateMilliseconds, storeIds);
	}
	
	@GetMapping("/saleItem")
	public SaleReport searchSaleItems(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "storeIds") String storeIds,
			@RequestParam(value = "productCode", required = false) String productCode, @RequestParam(value = "deviceBrandId", required = false) Integer deviceBrandId,
			@RequestParam(value = "deviceModelId", required = false) Integer deviceModelId) {
		return saleService.searchSaleItems(startDateMilliseconds, endDateMilliseconds, storeIds, productCode, deviceBrandId, deviceModelId);
	}
	
	@PutMapping
	public void insertSale(@RequestBody Sale sale) throws SQLException {
		saleService.submitSale(sale);
	}
	
	@GetMapping("/{saleId}")
	public List<SaleItem> getSaleItems(@PathVariable(value = "saleId") Integer saleId) {
		return saleService.getSaleItems(saleId);
	}
	
	@PostMapping("/totalSum")
	public TotalSumReport getTotalSum(@RequestBody TotalSumRequest totalSumRequest) {
		return saleService.calculateTotalSum(totalSumRequest);
	}
	
}
