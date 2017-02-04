package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.SaleDao;
import com.kalafche.model.Sale;
import com.kalafche.service.StockService;

@CrossOrigin
@RestController
@RequestMapping({ "/service/sale" })
public class SaleController {
	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private StockService stockService;

	@RequestMapping(value = { "/getAllSales" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Sale> getAllSales() {
		List<Sale> sales = this.saleDao.getAllSales();

		return sales;
	}
	
	@RequestMapping(value = { "/searchSales" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Sale> searchSales(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, @RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "kalafcheStoreId") Integer kalafcheStoreId) {
		List<Sale> sales = this.saleDao.searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreId);

		return sales;
	}
	
	@RequestMapping(value = { "/insertSale" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void insertSale(@RequestBody Sale sale) {
		this.saleDao.insertSale(sale);
		this.stockService.updateTheQuantitiyOfSoldStock(sale.getStockId());
	}
}
