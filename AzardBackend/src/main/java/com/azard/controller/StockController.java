package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azard.model.Stock;
import com.azard.service.StockService;

@CrossOrigin
@RestController
@RequestMapping({ "/stock" })
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	@GetMapping
	public List<Stock> getStocksByStoreId(
			@RequestParam(value = "userStoreId", required = false) Integer userStoreId,
			@RequestParam(value = "selectedStoreId", required = false) Integer selectedStoreId,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "modelId", required = false) Integer modelId,
			@RequestParam(value = "leatherId", required = false) Integer leatherId,
			@RequestParam(value = "barcode", required = false) String barcode
			) {
		return stockService.getAllApprovedStocks(userStoreId, selectedStoreId, brandId, modelId, leatherId, barcode);
	}
	
	@GetMapping("/getAllStocksForReport")
	public List<Stock> getAllStocksForReport() {
		List<Stock> stocks = stockService.generateStockReport();

		return stocks;
	}
	
	@GetMapping("/printStickers/{storeId}")
	public ResponseEntity<byte[]> printStockStickersByStoreId(@PathVariable(value = "storeId") Integer storeId) {
	
		byte[] pdfBytes = new byte[1];
		
		if (storeId != null && storeId != 0) {
			pdfBytes = stockService.printStockStickersByStoreId(storeId);
		}
	
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = "stickers.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	    
	    return response;
	}
	
}
