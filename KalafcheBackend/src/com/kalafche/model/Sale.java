package com.kalafche.model;

public class Sale {
	private int id;
	private int stockId;
	private String productName;
	private String productCode;
	private int kalafcheStoreId;
	private String kalafcheStoreName;
	private int deviceModelId;
	private String deviceModelName;
	private int deviceBrandId;
	private String deviceBrandName;
	private long saleTimestamp;
	private int partnerId;
	private int employeeId;
	private String employeeName;
	private float cost;
	private float salePrice;
	private String partnerCode;

	public int getId() {
		return this.id;
	}

	public int setId(int id) {
		return this.id = id;
	}

	public long getSaleTimestamp() {
		return this.saleTimestamp;
	}

	public void setSaleTimestamp(long saleTimestamp) {
		this.saleTimestamp = saleTimestamp;
	}

	public int getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public float getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(int deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public int getDeviceBrandId() {
		return deviceBrandId;
	}

	public void setDeviceBrandId(int deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}

	public String getDeviceBrandName() {
		return deviceBrandName;
	}

	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getKalafcheStoreName() {
		return kalafcheStoreName;
	}

	public void setKalafcheStoreName(String kalafcheStoreName) {
		this.kalafcheStoreName = kalafcheStoreName;
	}

	public int getKalafcheStoreId() {
		return kalafcheStoreId;
	}

	public void setKalafcheStoreId(int kalafcheStoreId) {
		this.kalafcheStoreId = kalafcheStoreId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
