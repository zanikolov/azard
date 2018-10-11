package com.kalafche.dao;

import java.util.Date;
import java.util.List;

import com.kalafche.model.Sale;

public abstract interface SaleDao {
	public abstract List<Sale> getAllSales();

	public abstract List<Sale> getAllSalesForPeriod(Date startPeriod,
			Date endPeriod);

	public abstract void insertSale(Sale sale);

	public abstract List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode,
			Integer deviceBrandId, Integer deviceModelId);
}
