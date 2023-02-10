package com.azard.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.PastPeriodSaleReport;
import com.azard.model.Sale;
import com.azard.model.SaleItem;
import com.azard.model.SaleItemExcelReportRequest;
import com.azard.model.SaleReport;
import com.azard.model.TotalSumReport;
import com.azard.model.TotalSumRequest;
import com.azard.service.SaleService;
import com.azard.service.fileutil.SaleItemExcelReportService;

@CrossOrigin
@RestController
@RequestMapping({ "/sale" })
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired 
	private SaleItemExcelReportService saleItemExcelReportService;
	
	@GetMapping
	public SaleReport searchSales(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "storeIds") String storeIds) {
		return saleService.searchSales(startDateMilliseconds, endDateMilliseconds, storeIds);
	}
	
	@GetMapping("/saleItem")
	public SaleReport searchSaleItems(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "storeIds") String storeIds,
			@RequestParam(value = "leatherId", required = false) Integer leatherId, @RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "modelId", required = false) Integer modelId) {
		return saleService.searchSaleItems(startDateMilliseconds, endDateMilliseconds, storeIds, leatherId, brandId, modelId);
	}
	
	@GetMapping("/store")
	public SaleReport searchSalesByStores(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds,
			@RequestParam(value = "leatherId", required = false) Integer leatherId, @RequestParam(value = "deviceBrandId", required = false) Integer deviceBrandId,
			@RequestParam(value = "deviceModelId", required = false) Integer deviceModelId, @RequestParam(value = "productTypeId", required = false) Integer productTypeId) {
		return saleService.searchSalesByStores(startDateMilliseconds, endDateMilliseconds, leatherId, deviceBrandId, deviceModelId);
	}
	
	@GetMapping("/pastPeriods")
	public PastPeriodSaleReport searchSalesByStores(@RequestParam(value = "month") String month) {
		return saleService.searchSalesForPastPeriodsByStores(month);
	}
	
	@PutMapping
	public void insertSale(@RequestBody Sale sale) throws SQLException, InterruptedException {
		//TimeUnit.SECONDS.sleep(5);
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
	
	@PostMapping("/excel")
	public ResponseEntity<byte[]> getTotalSum(@RequestBody SaleItemExcelReportRequest saleItemExcelReportRequest) {
		byte[] contents = saleItemExcelReportService.generateExcel(saleItemExcelReportRequest);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
	    String filename = "sale-item-report.xlsx";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.set("Content-Transfer-Encoding", "binary");
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
		
	}
	
}
