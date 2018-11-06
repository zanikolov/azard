package com.kalafche.model;

import java.math.BigDecimal;

public class Stock {
	private int id;
	private int itemId;
	private int deviceBrandId;
	private int deviceModelId;
	private String deviceModelName;
	private int productId;
	private String productCode;
	private String productName;
	private String productDescription;
	private BigDecimal productPrice;
	private int kalafcheStoreId;
	private String kalafcheStoreName;
	private int quantity;
	private int orderedQuantity;
	private boolean approved;
	private int approver;
	private int quantityInStock;
	private int extraQuantity;
	private BigDecimal specificPrice;

	public int getId() {
		return id;
	}

	public int setId(int id) {
		return this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getDeviceBrandId() {
		return deviceBrandId;
	}

	public void setDeviceBrandId(int deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}

	public int getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(int deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public int getKalafcheStoreId() {
		return kalafcheStoreId;
	}

	public void setKalafcheStoreId(int kalafcheStoreId) {
		this.kalafcheStoreId = kalafcheStoreId;
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

	public int getApprover() {
		return approver;
	}

	public void setApprover(int approver) {
		this.approver = approver;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getKalafcheStoreName() {
		return kalafcheStoreName;
	}

	public void setKalafcheStoreName(String kalafcheStoreName) {
		this.kalafcheStoreName = kalafcheStoreName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public int getExtraQuantity() {
		return extraQuantity;
	}

	public void setExtraQuantity(int extraQuantity) {
		this.extraQuantity = extraQuantity;
	}

	public BigDecimal getSpecificPrice() {
		return specificPrice;
	}

	public void setSpecificPrice(BigDecimal specificPrice) {
		this.specificPrice = specificPrice;
	}

}
