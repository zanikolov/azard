package com.kalafche.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.dao.RefundDao;
import com.kalafche.dao.SaleDao;
import com.kalafche.model.Employee;
import com.kalafche.model.Refund;
import com.kalafche.service.DateService;
import com.kalafche.service.EmployeeService;
import com.kalafche.service.ExpenseService;
import com.kalafche.service.RefundService;
import com.kalafche.service.StockService;

@Service
public class RefundServiceImpl implements RefundService {

	@Autowired
	private RefundDao refundDao;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	SaleDao saleDao;
	
	@Autowired
	KalafcheStoreDao storeDao;
	
	@Autowired
	ExpenseService expenseService;
	
	@Override
	public List<Refund> searchRefunds(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId) {
		
		if (storeIds.equals("0") || storeIds.equals("ANIKO") || storeIds.equals("AZARD")) {
			storeIds = storeDao.selectStoreIdsByOwner(storeIds);
		}
		
		List<Refund> refunds = refundDao.searchRefunds(startDateMilliseconds,
				endDateMilliseconds, storeIds, productCode, deviceBrandId, deviceModelId);
		
		return refunds;
	}

	@Transactional
	@Override
	public void submitRefund(Refund refund) {
		Employee loggedInEmployee = employeeService.getLoggedInEmployee();
		refund.setEmployeeId(loggedInEmployee.getId());
		refund.setTimestamp(dateService.getCurrentMillisBGTimezone());
		
		refundDao.insertRefund(refund);
		stockService.updateTheQuantitiyOfSoldStock(refund.getSaleItemId(), loggedInEmployee.getKalafcheStoreId());
		saleDao.updateRefundedSaleItem(refund.getSaleItemId());
		registerExpense(refund.getSaleItemId());
	}

	private void registerExpense(Integer saleItemId) {
		BigDecimal saleItemPrice = saleDao.getSaleItemPrice(saleItemId);
		expenseService.createExpense("REFUND", "Върнати пари при рекламация", saleItemPrice, null, null);
	}

}
