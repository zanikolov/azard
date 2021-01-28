package com.azard.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.OrderedStockDao;
import com.azard.dao.StockOrderDao;
import com.azard.exceptions.CommonException;
import com.azard.model.OrderedStock;

@Service
public class OrderedStockService {

	@Autowired
	OrderedStockDao orderedStockDao;
	
	@Autowired
	StockOrderDao stockOrderDao;
	
	@Transactional
	public int addStockToOrder(OrderedStock orderedStock) throws CommonException, SQLException {
		//int currentUserId = authenticationService.getPrincipal().getId();
		long currentTime = System.currentTimeMillis();
		int stockOrderId = orderedStock.getStockOrderId();
		
		orderedStock.setCreatedBy(null);
		orderedStock.setCreateTimestamp(currentTime);
		
		int orderedStockId = orderedStockDao.insertOrderedStock(orderedStock);
		
		stockOrderDao.updateStockOrderUpdateTimestamp(stockOrderId, null, currentTime);
		
		return orderedStockId;
	}
	
}
