package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.LoyalCustomerDao;
import com.azard.exceptions.DomainObjectNotFoundException;
import com.azard.exceptions.DuplicationException;
import com.azard.model.LoyalCustomer;
import com.azard.service.DateService;
import com.azard.service.EmployeeService;
import com.azard.service.LoyalCustomerService;

@Service
public class LoyalCustomerServiceImpl implements LoyalCustomerService {

	@Autowired
	LoyalCustomerDao loyalCustomerDao;
	
	@Autowired
	DateService dateService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Override
	public LoyalCustomer getLoyalCustomer(String code) {
		LoyalCustomer loyalCustomer = loyalCustomerDao.getLoyalCustomerByCode(code);
		
		if (loyalCustomer == null) {
			throw new DomainObjectNotFoundException("loyalCustomerCode", "Unexisting Loyal Customer.");
		} else {
			return loyalCustomer;
		}
	}

	@Override
	public List<LoyalCustomer> getAllLoyalCustomers() {
		return loyalCustomerDao.getAllLoyalCustomers();
	}

	@Override
	public void createLoyalCustomer(LoyalCustomer loyalCustomer) {
		validateDiscountCode(loyalCustomer);
		loyalCustomer.setCreatedTimestamp(dateService.getCurrentMillisBGTimezone());
		loyalCustomer.setCreatedById(employeeService.getLoggedInEmployee().getId());
		loyalCustomerDao.insertLoyalCustomer(loyalCustomer);
	}

	@Override
	public void updateLoyalCustomer(LoyalCustomer loyalCustomer) {
		loyalCustomerDao.updateLoyalCustomer(loyalCustomer);
	}
	
	private void validateDiscountCode(LoyalCustomer loyalCustomer) {
		if (loyalCustomerDao.checkIfLoyalCustomerDiscountCodeExists(loyalCustomer)) {
			throw new DuplicationException("code", "Code duplication.");
		}
	}
	
}
