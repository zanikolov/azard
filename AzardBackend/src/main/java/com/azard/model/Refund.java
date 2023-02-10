package com.azard.model;

import java.math.BigDecimal;

public class Refund {

	private Integer id;
	private Integer saleItemId;
	private String leatherName;
	private String leatherCode;
	private Integer storeId;
	private String storeName;
	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private long timestamp;
	private long saleTimestamp;
	private Integer employeeId;
	private String employeeName;
	private BigDecimal price;
	private String description;

	public Refund() {
	}

	public Refund(Integer saleItemId, String description) {
		this.saleItemId = saleItemId;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSaleItemId() {
		return saleItemId;
	}

	public void setSaleItemId(Integer saleItemId) {
		this.saleItemId = saleItemId;
	}

	public String getLeatherName() {
		return leatherName;
	}

	public void setLeatherName(String leatherName) {
		this.leatherName = leatherName;
	}

	public String getLeatherCode() {
		return leatherCode;
	}

	public void setLeatherCode(String leatherCode) {
		this.leatherCode = leatherCode;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getSaleTimestamp() {
		return saleTimestamp;
	}

	public void setSaleTimestamp(long saleTimestamp) {
		this.saleTimestamp = saleTimestamp;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
