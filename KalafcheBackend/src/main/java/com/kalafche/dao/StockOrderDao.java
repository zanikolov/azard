package com.kalafche.dao;

import com.kalafche.exceptions.CommonException;
import com.kalafche.model.StockOrder;

public interface StockOrderDao {
	
	public abstract StockOrder getCurrentStockOrder() throws CommonException;

	public abstract void insertStockOrder();
	
	public abstract void updateStockOrderUpdateTimestamp(int orderId, int updater, long updateTimestamp);
	
}
