package com.kalafche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalafche.dao.StockOrderDao;
import com.kalafche.dao.impl.StockDaoImpl;
import com.kalafche.exceptions.CommonException;
import com.kalafche.model.Stock;
import com.kalafche.model.StockOrder;

@Service
public class StockService {
	
	@Autowired
	StockDaoImpl stockDao;
	
	@Autowired
	StockOrderDao stockOrderDao;
	
	public void updateTheQuantitiyOfSoldStock(int itemId, int kalafcheStoreId) {				
		stockDao.updateTheQuantitiyOfSoldStock(itemId, kalafcheStoreId);
	}
	
	public void updateTheQuantitiyOfRefundStock(Integer saleItemId, int storeId) {				
		stockDao.updateTheQuantitiyOfRefundStock(saleItemId, storeId);
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
	
}
