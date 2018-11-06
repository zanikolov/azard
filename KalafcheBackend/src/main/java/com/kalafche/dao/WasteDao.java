package com.kalafche.dao;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Waste;

public interface WasteDao {

	List<Waste> searchWastes(Long startDateMilliseconds,
			Long endDateMilliseconds, String storeIds, String productCode, Integer deviceBrandId, Integer deviceModelId);

	Integer insertWaste(Waste waste) throws SQLException;

}
