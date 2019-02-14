package com.kalafche.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.DeviceBrandDao;
import com.kalafche.dao.DeviceModelDao;
import com.kalafche.dao.ItemDao;
import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.dao.SaleDao;
import com.kalafche.model.Employee;
import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;
import com.kalafche.model.SaleReport;
import com.kalafche.model.StoreDto;
import com.kalafche.model.TotalSumReport;
import com.kalafche.model.TotalSumRequest;
import com.kalafche.service.DateService;
import com.kalafche.service.EmployeeService;
import com.kalafche.service.SaleService;
import com.kalafche.service.StockService;

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
	
	@Autowired
	KalafcheStoreDao storeDao;
	
	@Autowired
	DeviceBrandDao deviceBrandDao;
	
	@Autowired
	DeviceModelDao deviceModelDao;
	
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
	public SaleReport searchSales(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds) {

		SaleReport saleReport = generateReport(storeIds, startDateMilliseconds, endDateMilliseconds, null, null, null);
		
		if (storeIds.equals("0") || storeIds.equals("ANIKO") || storeIds.equals("AZARD")) {
			storeIds = storeDao.selectStoreIdsByOwner(storeIds);
		}
		
		List<Sale> sales = saleDao.searchSales(startDateMilliseconds, endDateMilliseconds, storeIds);
		
		
		calculateTotalAmountAndCountForSales(sales, saleReport);
		saleReport.setSales(sales);
		
		return saleReport;
	}

	private SaleReport generateReport(String storeId, Long startDateMilliseconds,
			Long endDateMilliseconds, String productCode, Integer deviceBrandId, Integer deviceModelId) {
		SaleReport report = new SaleReport();
		
		report.setStartDate(startDateMilliseconds);
		report.setEndDate(endDateMilliseconds);		
		setSaleReportStoreName(storeId, report);
		
		if (deviceBrandId != null) {
			report.setDeviceBrandName(deviceBrandDao.selectDeviceBrand(deviceBrandId).getName());
		}
		
		if (deviceModelId != null) {
			report.setDeviceModelName(deviceModelDao.selectDeviceModel(deviceModelId).getName());
		}
		
		if (productCode != null) {
			report.setProductCode(productCode);
		}
		
		return report;
		
	}

	private void setSaleReportStoreName(String storeId, SaleReport report) {
		switch (storeId) {
			case "0": 
				report.setStoreName("Всички магазини");
				break;
			case "ANIKO":
				report.setStoreName("Анико ЕООД");
				break;
			case "AZARD":
				report.setStoreName("Азард ЕООД");
				break;
			default: {
				StoreDto store = storeDao.selectStore(storeId);
				report.setStoreName(store.getCity() + ", " + store.getName());
			}
		}
	}

	@Override
	public List<SaleItem> getSaleItems(Integer saleId) {
		return saleDao.getSaleItemsBySaleId(saleId);
	}

	@Override
	public SaleReport searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId, Integer productTypeId) {
		SaleReport saleReport = generateReport(storeIds, startDateMilliseconds, endDateMilliseconds, productCode, deviceBrandId, deviceModelId);
		
		if (storeIds.equals("0") || storeIds.equals("ANIKO") || storeIds.equals("AZARD")) {
			storeIds = storeDao.selectStoreIdsByOwner(storeIds);
		}
		
		List<SaleItem> saleItems = saleDao.searchSaleItems(startDateMilliseconds, endDateMilliseconds, storeIds, productCode, deviceBrandId, deviceModelId, productTypeId);
		
		if (deviceModelId != null && productCode != null && productCode != "") {
			saleReport.setWarehouseQuantity(stockService.getQuantitiyOfStockInWH(productCode, deviceModelId));
			saleReport.setCompanyQuantity(stockService.getCompanyQuantityOfStock(productCode, deviceModelId));
		}		

		calculateTotalAmountAndCountSaleItems(saleItems, saleReport);
		saleReport.setSaleItems(saleItems);
		
		return saleReport;
	}

	private void calculateTotalAmountAndCountSaleItems(List<SaleItem> saleItems, SaleReport saleReport) {
		BigDecimal totalAmount = BigDecimal.ZERO;	
		Integer count = 0;

		if (saleItems != null && !saleItems.isEmpty()) {
			totalAmount = saleItems.stream()
			        .map(saleItem -> saleItem.getSalePrice())
			        .reduce(BigDecimal.ZERO, BigDecimal::add);	
			count = saleItems.size();
		}
		
		saleReport.setCount(count);
		saleReport.setTotalAmount(totalAmount);	
	}
	
	private void calculateTotalAmountAndCountForSales(List<Sale> sales, SaleReport saleReport) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		Integer count = 0;
		
		if (sales != null && !sales.isEmpty()) {
			totalAmount = sales.stream()
			        .map(sale -> sale.getAmount())
			        .reduce(BigDecimal.ZERO, BigDecimal::add);
			count = sales.size();
		}
		
		saleReport.setCount(count);
		saleReport.setTotalAmount(totalAmount);
	}

	@Override
	public TotalSumReport calculateTotalSum(TotalSumRequest totalSumRequest) {
		TotalSumReport totalSumReport = new TotalSumReport();
		
		BigDecimal totalSum = totalSumRequest.getPrices().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		totalSumReport.setTotalSum(totalSum);
		totalSumReport.setTotalSumAfterDiscount(totalSum);
		
		if (totalSumRequest.getDiscount() != null) {
			BigDecimal discount = totalSum.multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
			totalSumReport.setDiscount(discount);
			totalSumReport.setTotalSumAfterDiscount(totalSum.subtract(discount));
		}
		
		return totalSumReport;
	}

}
