package com.kalafche.model;

import com.kalafche.BaseModel;

public class StockRelocation extends BaseModel {
	private int id;
	private int stockId;
	private String itemName;
	private String itemProductCode;
	private int fromKalafcheStoreId;
	private String fromKalafcheStoreName;
	private int toKalafcheStoreId;
	private String toKalafcheStoreName;
	private int deviceModelId;
	private String deviceModelName;
	private int deviceBrandId;
	private String deviceBrandName;
	private long relocationRequestTimestamp;
	private long relocationCompleteTimestamp;
	private int employeeId;
	private String employeeName;
	private boolean approved;
	private boolean rejected;
	private boolean arrived;
	private int quantity;
	private float itemPrice;
	private float relocationPrice;
	private boolean archived;

	public int getId() {
		return this.id;
	}

	public int setId(int id) {
		return this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getFromKalafcheStoreId() {
		return fromKalafcheStoreId;
	}

	public void setFromKalafcheStoreId(int fromKalafcheStoreId) {
		this.fromKalafcheStoreId = fromKalafcheStoreId;
	}

	public String getFromKalafcheStoreName() {
		return fromKalafcheStoreName;
	}

	public void setFromKalafcheStoreName(String fromKalafcheStoreName) {
		this.fromKalafcheStoreName = fromKalafcheStoreName;
	}

	public int getToKalafcheStoreId() {
		return toKalafcheStoreId;
	}

	public void setToKalafcheStoreId(int toKalafcheStoreId) {
		this.toKalafcheStoreId = toKalafcheStoreId;
	}

	public String getToKalafcheStoreName() {
		return this.toKalafcheStoreName;
	}

	public void setToKalafcheStoreName(String toKalafcheStoreName) {
		this.toKalafcheStoreName = toKalafcheStoreName;
	}

	public long getRelocationRequestTimestamp() {
		return relocationRequestTimestamp;
	}

	public void setRelocationRequestTimestamp(long relocationRequestTimestamp) {
		this.relocationRequestTimestamp = relocationRequestTimestamp;
	}

	public long getRelocationCompleteTimestamp() {
		return relocationCompleteTimestamp;
	}

	public void setRelocationCompleteTimestamp(long relocationCompleteTimestamp) {
		this.relocationCompleteTimestamp = relocationCompleteTimestamp;
	}

	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	public float getRelocationPrice() {
		if (userHasRole("ROLE_SUPERADMIN")) {
			return relocationPrice;
		} else {
			return 0;
		}
	}

	public void setRelocationPrice(float relocationPrice) {
		if (userHasRole("ROLE_SUPERADMIN")) {
			this.relocationPrice = relocationPrice;
		}
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public String getItemProductCode() {
		return itemProductCode;
	}

	public void setItemProductCode(String itemProductCode) {
		this.itemProductCode = itemProductCode;
	}
}
