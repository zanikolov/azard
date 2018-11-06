package com.kalafche.service;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;
import com.kalafche.model.SaleReport;

public interface SaleService {

	public void submitSale(Sale sale) throws SQLException;

	public SaleReport searchSales(Long startDateMilliseconds, Long endDateMilliseconds, String kalafcheStoreIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId);

	public List<SaleItem> getSaleItems(Integer saleId);

	public SaleReport searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds, String kalafcheStoreIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId);

}
