package com.azard.dao;

import java.util.List;

import com.azard.model.Refund;

public interface RefundDao {

	void insertRefund(Refund refund);

	List<Refund> searchRefunds(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId);

}
