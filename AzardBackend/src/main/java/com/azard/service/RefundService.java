package com.azard.service;

import java.util.List;

import com.azard.model.Refund;

public interface RefundService {

	List<Refund> searchRefunds(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds, String productCode,
			Integer deviceBrandId, Integer deviceModelId);

	void submitRefund(Refund refund);

}
