package com.kalafche.service;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;
import com.kalafche.model.SaleReport;
import com.kalafche.model.TotalSumReport;
import com.kalafche.model.TotalSumRequest;

public interface SaleService {

	public void submitSale(Sale sale) throws SQLException;

	public SaleReport searchSales(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds);

	public List<SaleItem> getSaleItems(Integer saleId);

	public SaleReport searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId);

	public TotalSumReport calculateTotalSum(TotalSumRequest totalSumRequest);

}
