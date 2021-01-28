package com.azard.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.azard.dao.ItemDao;
import com.azard.dao.StoreDao;
import com.azard.dao.WasteDao;
import com.azard.model.Employee;
import com.azard.model.Waste;
import com.azard.model.WasteReport;
import com.azard.service.DateService;
import com.azard.service.EmployeeService;
import com.azard.service.StockService;
import com.azard.service.WasteService;
import com.azard.service.fileutil.ImageUploadService;

@Service
public class WasteServiceImpl implements WasteService {

	@Autowired
	WasteDao wasteDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	ImageUploadService imageUploadService;
	
	@Autowired
	StoreDao storeDao;
	
	@Override
	public WasteReport searchWastes(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId) {
		WasteReport wasteReport = new WasteReport();
		
		if (storeIds.equals("0") || storeIds.equals("ANIKO") || storeIds.equals("AZARD")) {
			storeIds = storeDao.selectStoreIdsByOwner(storeIds);
		}
		
		List<Waste> wastes = wasteDao.searchWastes(startDateMilliseconds,
				endDateMilliseconds, storeIds, productCode, deviceBrandId, deviceModelId);
		
		if (wastes != null && !wastes.isEmpty()) {
			calculateTotalAmountAndCount(wastes, wasteReport);
		} else {
			wasteReport.setCount(0);
			wasteReport.setTotalAmount(BigDecimal.ZERO);
		}	
		wasteReport.setWastes(wastes);
		
		return wasteReport;
	}

	private void calculateTotalAmountAndCount(List<Waste> wastes, WasteReport wasteReport) {
		BigDecimal totalAmount = wastes.stream()
		        .map(waste -> waste.getPrice())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		wasteReport.setCount(wastes.size());
		wasteReport.setTotalAmount(totalAmount);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void submitWaste(Waste waste, MultipartFile wasteImage) throws SQLException {
		String fileId = imageUploadService.uploadWasteImage(wasteImage);
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		waste.setEmployeeId(loggedInEmployee.getId());
		waste.setStoreId(loggedInEmployee.getStoreId());
		waste.setTimestamp(dateService.getCurrentMillisBGTimezone());
		waste.setPrice(itemDao.getItemPriceByStoreId(waste.getItemId(), waste.getStoreId()));
		waste.setFileId(fileId);
		
		wasteDao.insertWaste(waste);
		stockService.updateTheQuantitiyOfSoldStock(waste.getItemId(), loggedInEmployee.getStoreId());
	}

}
