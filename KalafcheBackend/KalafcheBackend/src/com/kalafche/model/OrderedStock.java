package com.kalafche.model;

public class OrderedStock {

	private int id;
	private int itemId;
	private String itemName;
	private String itemProductCode;
	private int deviceModelId;
	private String deviceModelName;
	private int deviceBrandId;
	private String deviceBrandName;
	private int stockOrderId;
	private int quantity;
	private long createTimestamp;
	private int createdBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public int getStockOrderId() {
		return stockOrderId;
	}

	public void setStockOrderId(int stockOrderId) {
		this.stockOrderId = stockOrderId;
	}

	public long getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDeviceBrandName() {
		return deviceBrandName;
	}

	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}

	public int getDeviceBrandId() {
		return deviceBrandId;
	}

	public void setDeviceBrandId(int deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}

	public String getItemProductCode() {
		return itemProductCode;
	}

	public void setItemProductCode(String itemProductCode) {
		this.itemProductCode = itemProductCode;
	}
}
