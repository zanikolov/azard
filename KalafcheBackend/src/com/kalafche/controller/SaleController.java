package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.SaleDao;
import com.kalafche.dao.StockDao;
import com.kalafche.model.Sale;
import com.kalafche.model.SaleReport;
import com.kalafche.service.StockService;

@CrossOrigin
@RestController
@RequestMapping({ "/service/sale" })
public class SaleController {
	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private StockDao stockDao;

	@RequestMapping(value = { "/getAllSales" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Sale> getAllSales() {
		List<Sale> sales = this.saleDao.getAllSales();

		return sales;
	}
	
	@RequestMapping(value = { "/searchSales" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public SaleReport searchSales(@RequestParam(value = "startDateMilliseconds") Long startDateMilliseconds, 
			@RequestParam(value = "endDateMilliseconds") Long endDateMilliseconds, @RequestParam(value = "kalafcheStoreIds") String kalafcheStoreIds,
			@RequestParam(value = "productCode", required = false) String productCode, @RequestParam(value = "deviceBrandId", required = false) Integer deviceBrandId,
			@RequestParam(value = "deviceModelId", required = false) Integer deviceModelId) {
		List<Sale> sales = this.saleDao.searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, productCode, deviceBrandId, deviceModelId);

		int warehouseQuantity = 0;
		int companyQuantity = 0;
		if (deviceModelId != null && productCode != null && productCode != "") {
			warehouseQuantity = stockDao.getQuantitiyOfStockInWH(productCode, deviceModelId);
			companyQuantity = stockDao.getCompanyQuantityOfStock(productCode, deviceModelId);
		}
		
		return new SaleReport(sales, warehouseQuantity, companyQuantity);
	}
	
	@RequestMapping(value = { "/insertSale" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void insertSale(@RequestBody Sale sale) {
		this.saleDao.insertSale(sale);
		this.stockService.updateTheQuantitiyOfSoldStock(sale.getStockId());
	}
}
