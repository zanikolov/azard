package com.azard.service;

import java.sql.SQLException;
import java.util.List;

import com.azard.model.PastPeriodSaleReport;
import com.azard.model.PastPeriodTurnover;
import com.azard.model.Sale;
import com.azard.model.SaleItem;
import com.azard.model.SaleReport;
import com.azard.model.TotalSumReport;
import com.azard.model.TotalSumRequest;

public interface SaleService {

	public void submitSale(Sale sale) throws SQLException;

	public SaleReport searchSales(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds);

	public List<SaleItem> getSaleItems(Integer saleId);

	public SaleReport searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds, String storeIds,
			String productCode, Integer deviceBrandId, Integer deviceModelId, Integer productTypeId);

	public TotalSumReport calculateTotalSum(TotalSumRequest totalSumRequest);

	public SaleReport searchSalesByStores(Long startDateMilliseconds, Long endDateMilliseconds, String productCode,
			Integer deviceBrandId, Integer deviceModelId, Integer productTypeId);

	public PastPeriodSaleReport searchSalesForPastPeriodsByStores(String month);

}
