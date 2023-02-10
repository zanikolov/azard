package com.azard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.StockOrderDao;
import com.azard.dao.impl.StockDaoImpl;
import com.azard.exceptions.CommonException;
import com.azard.model.Stock;
import com.azard.model.StockOrder;
import com.azard.service.fileutil.PDFGeneratorService;

@Service
public class StockService {
	
	@Autowired
	StockDaoImpl stockDao;
	
	@Autowired
	StockOrderDao stockOrderDao;
	
	@Autowired
	PDFGeneratorService pdfGeneratorService;
	
	public void updateTheQuantitiyOfSoldStock(int itemId, int storeId) {				
		stockDao.updateTheQuantitiyOfSoldStock(itemId, storeId);
	}
	
	public void updateTheQuantitiyOfRefundStock(Integer saleItemId, int storeId) {				
		stockDao.updateTheQuantitiyOfRefundStock(saleItemId, storeId);
	}
	
	public void updateTheQuantitiyOfRevisedStock(Integer itemId, Integer revisionId, Integer difference) {				
		stockDao.updateTheQuantitiyOfRevisedStock(itemId, revisionId, difference);
	}
	
	public List<Stock> generateStockReport() {
		StockOrder stockOrder;
		int stockOrderId = 0;
		try {
			stockOrder = stockOrderDao.getCurrentStockOrder();
			stockOrderId = stockOrder.getId();
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Stock> stockList = stockDao.getAllStocksForReport(stockOrderId);
		
		return stockList;
	}

	public int getQuantitiyOfStockInWH(String productCode, Integer deviceModelId) {
		return stockDao.getQuantitiyOfStockInWH(productCode, deviceModelId);
	}

	public int getCompanyQuantityOfStock(String productCode, Integer deviceModelId) {
		return stockDao.getCompanyQuantityOfStock(productCode, deviceModelId);
	}

	public byte[] printStockStickersByStoreId(Integer storeId) {
		List<Stock> stocks = stockDao.getAllApprovedStocksForStickerPrinting(storeId);
		return pdfGeneratorService.generatePdf(stocks);
	}

	public List<Stock> getAllApprovedStocks(Integer userStoreId, Integer selectedStoreId, Integer brandId, Integer modelId, Integer leatherId, String barcode) {
		return stockDao.getAllApprovedStocks(userStoreId, selectedStoreId, brandId, modelId, leatherId, barcode);
	}
	
}
