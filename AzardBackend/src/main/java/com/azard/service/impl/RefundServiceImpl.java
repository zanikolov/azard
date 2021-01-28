package com.azard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.RefundDao;
import com.azard.dao.SaleDao;
import com.azard.dao.StoreDao;
import com.azard.model.Employee;
import com.azard.model.Refund;
import com.azard.service.DateService;
import com.azard.service.EmployeeService;
import com.azard.service.ExpenseService;
import com.azard.service.RefundService;
import com.azard.service.StockService;

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
	StoreDao storeDao;
	
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
		stockService.updateTheQuantitiyOfRefundStock(refund.getSaleItemId(), loggedInEmployee.getStoreId());
		saleDao.updateRefundedSaleItem(refund.getSaleItemId());
		registerExpense(refund.getSaleItemId());
	}

	private void registerExpense(Integer saleItemId) {
		BigDecimal saleItemPrice = saleDao.getSaleItemPrice(saleItemId);
		expenseService.createExpense("REFUND", "Върнати пари на клиент", saleItemPrice, null, null);
	}

}
