package com.azard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.dao.StockOrderDao;
import com.azard.exceptions.CommonException;
import com.azard.model.StockOrder;

@CrossOrigin
@RestController
@RequestMapping({ "/service/stockOrder" })
public class StockOrderController {

//	@Autowired
//	private StockOrderDao stockOrderDao;
//
//	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.GET })
//	public StockOrder getCurrentStockOrder() {
//		StockOrder stockOrder = null;
//		try {
//			stockOrder = this.stockOrderDao.getCurrentStockOrder();
//		} catch (CommonException e) {
//			e.printStackTrace();
//		}
//
//		return stockOrder;
//	}
//	
//	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.PUT })
//	public void inserStockOrder() {
//		stockOrderDao.insertStockOrder();
//	}
}
