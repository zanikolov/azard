package com.kalafche.dao;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Sale;
import com.kalafche.model.SaleItem;

public abstract interface SaleDao {

	public abstract Integer insertSale(Sale sale) throws SQLException;

	public abstract List<Sale> searchSales(Long startDateMilliseconds,
			Long endDateMilliseconds, String kalafcheStoreIds, String productCode,
			Integer deviceBrandId, Integer deviceModelId);

	public abstract List<SaleItem> getSaleItemsBySaleId(Integer saleId);

	public abstract void insertSaleItem(SaleItem saleItem);

	public abstract List<SaleItem> searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds,
			String kalafcheStoreIds, String productCode, Integer deviceBrandId, Integer deviceModelId);
}
