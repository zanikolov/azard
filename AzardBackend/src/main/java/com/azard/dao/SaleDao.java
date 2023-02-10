package com.azard.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.azard.model.Sale;
import com.azard.model.SaleItem;
import com.azard.model.SalesByStore;

public abstract interface SaleDao {

	public abstract Integer insertSale(Sale sale) throws SQLException;

	public abstract List<Sale> searchSales(Long startDateMilliseconds, Long endDateMilliseconds,
			String storeIds);

	public abstract List<SaleItem> getSaleItemsBySaleId(Integer saleId);

	public abstract void insertSaleItem(SaleItem saleItem);

	public abstract List<SaleItem> searchSaleItems(Long startDateMilliseconds, Long endDateMilliseconds,
			String storeIds, Integer leatherIds, Integer brandId, Integer modelId);

	public abstract void updateRefundedSaleItem(Integer saleItemId);

	public abstract BigDecimal getSaleItemPrice(Integer saleItemId);

	public abstract List<SalesByStore> searchSaleByStore(Long startDateMilliseconds, Long endDateMilliseconds);
}
