package com.azard.dao;

import java.util.List;

import com.azard.model.Waste;

public interface WasteDao {

	List<Waste> searchWastes(Long startDateMilliseconds,
			Long endDateMilliseconds, String storeIds, String productCode, Integer deviceBrandId, Integer deviceModelId);

	void insertWaste(Waste waste);

}
