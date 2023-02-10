package com.azard.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.azard.model.NewStock;
import com.azard.service.fileutil.ExcelItem;

public interface NewStockService {
	
	List<NewStock> getAllNewStocks();

	void deleteNewStock(Integer newStockId);

	void submitNewStockFromFile(MultipartFile newStockFile, Integer storeId) throws EncryptedDocumentException, InvalidFormatException, IOException;
	
	List<ExcelItem> validateNewStockFile(MultipartFile newStockFile) throws EncryptedDocumentException, InvalidFormatException, IOException;

	void approveNewStock(NewStock newStock);

	void approveNewStock(List<NewStock> newStocks);

	byte[] printNewStockStickers();

	void submitNewStock(Integer leatherId, Integer modelId, Integer quantity, Integer storeId, BigDecimal price);

	List<NewStock> getNewStockByStoreId(Integer storeId);
	
}
