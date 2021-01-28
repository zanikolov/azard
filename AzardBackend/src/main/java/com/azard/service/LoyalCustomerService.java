package com.azard.service;

import java.util.List;

import com.azard.model.LoyalCustomer;

public interface LoyalCustomerService {

	List<LoyalCustomer> getAllLoyalCustomers();

	LoyalCustomer getLoyalCustomer(String code);

	void createLoyalCustomer(LoyalCustomer loyalCustomer);

	void updateLoyalCustomer(LoyalCustomer loyalCustomer);

}
