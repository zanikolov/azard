package com.azard.model;

import java.math.BigDecimal;

public class SaleItem {

	private Integer id;
	private Integer saleId;
	private Long saleTimestamp;
	private Integer itemId;
	private Integer leatherId;
	private String leatherCode;
	private String leatherName;
	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private String brandName;
	private BigDecimal itemPrice;
	private BigDecimal salePrice;
	private Integer storeId;
	private String storeName;
	private Integer employeeId;
	private String employeeName;
	private Boolean isRefunded;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public Long getSaleTimestamp() {
		return saleTimestamp;
	}

	public void setSaleTimestamp(Long saleTimestamp) {
		this.saleTimestamp = saleTimestamp;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getLeatherId() {
		return leatherId;
	}

	public void setLeatherId(Integer leatherId) {
		this.leatherId = leatherId;
	}

	public String getLeatherCode() {
		return leatherCode;
	}

	public void setLeatherCode(String leatherCode) {
		this.leatherCode = leatherCode;
	}

	public String getLeatherName() {
		return leatherName;
	}

	public void setLeatherName(String leatherName) {
		this.leatherName = leatherName;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
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

	public Boolean getIsRefunded() {
		return isRefunded;
	}

	public void setIsRefunded(Boolean isRefunded) {
		this.isRefunded = isRefunded;
	}

}
