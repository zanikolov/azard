package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.Sale;

public abstract interface SaleDao {

	public abstract void insertSale(Sale sale);

	public abstract List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode,
			Integer deviceBrandId, Integer deviceModelId);
}
