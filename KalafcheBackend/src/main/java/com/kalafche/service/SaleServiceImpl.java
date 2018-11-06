package com.kalafche.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.ItemDao;
import com.kalafche.dao.SaleDao;
import com.kalafche.model.Employee;
import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;
import com.kalafche.model.SaleReport;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	SaleDao saleDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Transactional
	@Override
	public void submitSale(Sale sale) throws SQLException {
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		sale.setEmployeeId(loggedInEmployee.getId());
		sale.setStoreId(loggedInEmployee.getKalafcheStoreId());
		sale.setSaleTimestamp(dateService.getCurrentMillisBGTimezone());
		
		Integer saleId = saleDao.insertSale(sale);
		
		sale.getSaleItems().forEach(saleItem -> {
			saleItem.setSaleId(saleId);
			BigDecimal itemPrice = itemDao.getItemPriceByStoreId(saleItem.getItemId(), sale.getStoreId());
			saleItem.setItemPrice(itemPrice);
			if (sale.getPartnerId() != null) {
				saleItem.setSalePrice(itemPrice.multiply(new BigDecimal("0.8")).setScale(2, RoundingMode.HALF_UP));
			} else {
				saleItem.setSalePrice(itemPrice);
			}
			saleDao.insertSaleItem(saleItem);
			stockService.updateTheQuantitiyOfSoldStock(saleItem.getItemId(), loggedInEmployee.getKalafcheStoreId());
		});
	}

	@Override
	public SaleReport searchSales(Long startDateMilliseconds, Long endDateMilliseconds, String kalafcheStoreIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId) {
		SaleReport saleReport = new SaleReport();
		List<Sale> sales = saleDao.searchSales(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, productCode, deviceBrandId, deviceModelId);
		
		if (sales != null && !sales.isEmpty()) {
			calculateTotalAmountAndCountForSales(sales, saleReport);
		}
		saleReport.setSales(sales);
		
		return saleReport;
	}

	@Override
	public List<SaleItem> getSaleItems(Integer saleId) {
		return saleDao.getSaleItemsBySaleId(saleId);
	}

	@Override
	public SaleReport searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds, String kalafcheStoreIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId) {
		SaleReport saleReport = new SaleReport();
		List<SaleItem> saleItems = saleDao.searchSaleItems(startDateMilliseconds, endDateMilliseconds, kalafcheStoreIds, productCode, deviceBrandId, deviceModelId);
		
		if (deviceModelId != null && productCode != null && productCode != "") {
			saleReport.setWarehouseQuantity(stockService.getQuantitiyOfStockInWH(productCode, deviceModelId));
			saleReport.setCompanyQuantity(stockService.getCompanyQuantityOfStock(productCode, deviceModelId));
		}
		
		if (saleItems != null && !saleItems.isEmpty()) {
			calculateTotalAmountAndCountSaleItems(saleItems, saleReport);
		}
		
		return saleReport;
	}

	private void calculateTotalAmountAndCountSaleItems(List<SaleItem> saleItems, SaleReport saleReport) {
		BigDecimal totalAmount = new BigDecimal(0);
		saleItems.forEach(saleItem -> totalAmount.add(saleItem.getSalePrice()));
		
		saleReport.setTotalAmount(totalAmount);
		saleReport.setCount(saleItems.size());
	}
	
	private void calculateTotalAmountAndCountForSales(List<Sale> sales, SaleReport saleReport) {
		BigDecimal totalAmount = new BigDecimal(0);
		sales.forEach(sale -> totalAmount.add(sale.getAmount()));
		
		saleReport.setTotalAmount(totalAmount);
		saleReport.setCount(sales.size());
	}

}
