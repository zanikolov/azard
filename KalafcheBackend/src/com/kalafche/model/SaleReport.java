package com.kalafche.model;

import java.util.List;

public class SaleReport {
	
	private List<Sale> sales;
	private int warehouseQuantity;
	private int companyQuantity;
	
	public SaleReport(List<Sale> sales, int warehouseQuantity, int companyQuantity) {
		this.sales = sales;
		this.warehouseQuantity = warehouseQuantity;
		this.companyQuantity = companyQuantity;
	}
	
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	public int getWarehouseQuantity() {
		return warehouseQuantity;
	}
	public void setWarehouseQuantity(int warehouseQuantity) {
		this.warehouseQuantity = warehouseQuantity;
	}
	public int getCompanyQuantity() {
		return companyQuantity;
	}
	public void setCompanyQuantity(int companyQuantity) {
		this.companyQuantity = companyQuantity;
	}
	
}
