package com.kalafche.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kalafche.dao.ItemDao;
import com.kalafche.dao.WasteDao;
import com.kalafche.model.Employee;
import com.kalafche.model.Waste;
import com.kalafche.model.WasteReport;
import com.kalafche.service.DateService;
import com.kalafche.service.EmployeeService;
import com.kalafche.service.StockService;
import com.kalafche.service.WasteService;
import com.kalafche.service.fileutil.ImageUploadService;

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
	
	@Override
	public WasteReport searchWastes(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId) {
		WasteReport wasteReport = new WasteReport();
		List<Waste> wastes = wasteDao.searchWastes(startDateMilliseconds,
				endDateMilliseconds, storeIds, productCode, deviceBrandId, deviceModelId);
		
		if (wastes != null && !wastes.isEmpty()) {
			calculateTotalAmountAndCount(wastes, wasteReport);
			getWasteImages(wastes);
		} else {
			wasteReport.setCount(0);
			wasteReport.setTotalAmount(new BigDecimal(0));
		}	
		wasteReport.setWastes(wastes);
		
		return wasteReport;
	}

	private void getWasteImages(List<Waste> wastes) {
		wastes.forEach(waste -> {
			try {
				waste.setFileId(imageUploadService.getWasteImageId(waste.getId()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});	
	}

	private void calculateTotalAmountAndCount(List<Waste> wastes, WasteReport wasteReport) {
		BigDecimal totalAmount = new BigDecimal(0);
		wastes.forEach(waste -> totalAmount.add(waste.getPrice()));
		
		wasteReport.setCount(wastes.size());
		wasteReport.setTotalAmount(totalAmount);
	}

	@Override
	public void submitWaste(Waste waste, MultipartFile wasteImage) throws SQLException, IllegalStateException, IOException, GeneralSecurityException {
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		waste.setEmployeeId(loggedInEmployee.getId());
		waste.setStoreId(loggedInEmployee.getKalafcheStoreId());
		waste.setTimestamp(dateService.getCurrentMillisBGTimezone());
		waste.setPrice(itemDao.getItemPriceByStoreId(waste.getItemId(), waste.getStoreId()));	
		
		Integer wasteId = wasteDao.insertWaste(waste);
		stockService.updateTheQuantitiyOfSoldStock(waste.getItemId(), loggedInEmployee.getKalafcheStoreId());
		imageUploadService.uploadWasteImage(wasteId, wasteImage);
	}

}
