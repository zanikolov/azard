package com.azard.dao;

import com.azard.exceptions.CommonException;
import com.azard.model.StockOrder;

public interface StockOrderDao {
	
	public abstract StockOrder getCurrentStockOrder() throws CommonException;

	public abstract void insertStockOrder();
	
	public abstract void updateStockOrderUpdateTimestamp(Integer orderId, Integer updater, Long updateTimestamp);
	
}
