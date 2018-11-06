package com.kalafche.model;

import java.math.BigDecimal;
import java.util.List;

public class SaleReport {

	private List<Sale> sales;
	private List<SaleItem> saleItems;
	private Integer warehouseQuantity;
	private Integer companyQuantity;
	private BigDecimal totalAmount;
	private Integer count;

	public SaleReport() {
	}
	
	public SaleReport(List<Sale> sales, List<SaleItem> saleItems, Integer warehouseQuantity, Integer companyQuantity) {
		this.sales = sales;
		this.setSaleItems(saleItems);
		this.warehouseQuantity = warehouseQuantity;
		this.companyQuantity = companyQuantity;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public List<SaleItem> getSaleItems() {
		return saleItems;
	}

	public void setSaleItems(List<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public Integer getWarehouseQuantity() {
		return warehouseQuantity;
	}

	public void setWarehouseQuantity(Integer warehouseQuantity) {
		this.warehouseQuantity = warehouseQuantity;
	}

	public Integer getCompanyQuantity() {
		return companyQuantity;
	}

	public void setCompanyQuantity(Integer companyQuantity) {
		this.companyQuantity = companyQuantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
