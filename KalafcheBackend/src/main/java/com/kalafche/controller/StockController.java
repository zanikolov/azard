package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.impl.StockDaoImpl;
import com.kalafche.model.Stock;
import com.kalafche.service.StockService;

@CrossOrigin
@RestController
@RequestMapping({ "/stock" })
public class StockController {
	@Autowired
	private StockDaoImpl stockDao;
	
	@Autowired
	private StockService stockService;
	
	@GetMapping
	public List<Stock> getStocksByStoreId(
			@RequestParam(value = "userStoreId", required = false) int userStoreId,
			@RequestParam(value = "selectedStoreId", required = false) int selectedStoreId) {
		List<Stock> stocks = this.stockDao.getAllApprovedStocks(userStoreId, selectedStoreId);

		return stocks;
	}
	
	@GetMapping("/getAllStocksForReport")
	public List<Stock> getAllStocksForReport() {
		List<Stock> stocks = stockService.generateStockReport();

		return stocks;
	}
}
