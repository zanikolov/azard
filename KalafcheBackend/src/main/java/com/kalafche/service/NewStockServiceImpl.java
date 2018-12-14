package com.kalafche.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kalafche.dao.ItemDao;
import com.kalafche.dao.NewStockDao;
import com.kalafche.dao.WarehouseDao;
import com.kalafche.exceptions.DomainObjectNotFoundException;
import com.kalafche.model.Item;
import com.kalafche.model.NewStock;
import com.kalafche.service.fileutil.ExcelItem;
import com.kalafche.service.fileutil.NewStockExcelReaderService;
import com.kalafche.service.fileutil.PDFGeneratorService;

@Service
public class NewStockServiceImpl implements NewStockService {

	@Autowired
	NewStockDao newStockDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	WarehouseDao warehouseDao;
	
	@Autowired
	NewStockExcelReaderService newStockExcelReaderService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	PDFGeneratorService pdfGeneratorService;
	
	@Override
	public List<NewStock> getAllNewStocks() {
		return newStockDao.getAllNewStocks();
	}

	@Override
	public void deleteNewStock(Integer newStockId) {
		newStockDao.deleteNewStock(newStockId);
	}

	@Transactional
	@Override
	public void submitNewStock(Integer productId, Integer deviceModelId, Integer quantity) {
		Item item = itemDao.getItem(productId, deviceModelId);
		
		if (item == null) {
			itemDao.insertItem(productId, deviceModelId, null);
		} 
//		else {
//			newStockDao.insertOrUpdateQuantityOfNewStock(productId, deviceModelId, quantity);
//		}
		
		newStockDao.insertNewStock(productId, deviceModelId, quantity);
	}

	@Transactional
	@Override
	public void submitNewStockFromFile(MultipartFile newStockFile) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Integer newStockImport = submitNewStockImport(newStockFile);
		List<ExcelItem> uploadedNewStock = newStockExcelReaderService.parseExcelData(newStockFile);
		
		uploadedNewStock.forEach(excelItem -> {
			Item item = itemDao.getItem(excelItem.getBarcode());
			if (item == null) {
				throw new DomainObjectNotFoundException("file", "Не е намерен артикул с баркод " + excelItem.getBarcode());
			}
			newStockDao.insertNewStockFromFile(excelItem.getBarcode(), excelItem.getQuantity(), newStockImport);
		});
	}

	@Transactional
	private Integer submitNewStockImport(MultipartFile newStockFile) {
		Integer newStockImport = null;
		try {
			newStockImport = newStockDao.insertNewStockImport(dateService.getCurrentMillisBGTimezone(),
					employeeService.getLoggedInEmployee().getId(), newStockFile.getOriginalFilename());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newStockImport;
	}

	@Transactional
	@Override
	public List<ExcelItem> validateNewStockFile(MultipartFile newStockFile) throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<ExcelItem> uploadedNewStock = newStockExcelReaderService.parseExcelData(newStockFile);
		List<ExcelItem> unexistingItems = new ArrayList<>();
		
		uploadedNewStock.forEach(excelItem -> {
			Item item = itemDao.getItem(excelItem.getBarcode());
			if (item == null) {
				unexistingItems.add(excelItem);
			}
		});
		
		return unexistingItems;
	}
	
	@Transactional
	@Override
	public void approveNewStock(NewStock newStock) {
		warehouseDao.upsertStock(newStock.getItemId(), newStock.getQuantity());
		newStockDao.deleteNewStock(newStock.getId());
	}
	
	
	@Transactional
	@Override
	public void approveNewStock(List<NewStock> newStocks) {
		newStocks.forEach(newStock -> {
			approveNewStock(newStock);
		});
	}

	@Override
	public void printNewStockStickers() {
		List<NewStock> newStocks = getAllNewStocks();
		pdfGeneratorService.generatePdf(newStocks);
	}

}
