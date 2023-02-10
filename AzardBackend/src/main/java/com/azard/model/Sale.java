package com.azard.model;

import java.math.BigDecimal;
import java.util.List;

public class Sale {

	private Integer id;
	private Integer itemId;
	private Integer stockId;
	private String leatherName;
	private Integer leatherId;
	private Integer storeId;
	private String storeName;
	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private long saleTimestamp;
	private Integer partnerId;
	private Integer employeeId;
	private String employeeName;
	private BigDecimal amount;
	private String partnerCode;
	private List<SaleItem> saleItems;
	private Boolean isCashPayment;
	private Integer discountCodeId;
	private Integer discountCodeCode;

	public Sale() {
	}

	public Sale(Integer itemId, Integer employeeId, Integer storeId, Integer saleTimestamp, Integer partnerId) {
		this.itemId = itemId;
		this.employeeId = employeeId;
		this.storeId = storeId;
		this.saleTimestamp = saleTimestamp;
		this.partnerId = partnerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getLeatherName() {
		return leatherName;
	}

	public void setLeatherName(String leatherName) {
		this.leatherName = leatherName;
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

	public long getSaleTimestamp() {
		return saleTimestamp;
	}

	public void setSaleTimestamp(long saleTimestamp) {
		this.saleTimestamp = saleTimestamp;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public List<SaleItem> getSaleItems() {
		return saleItems;
	}

	public void setSaleItems(List<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public Boolean getIsCashPayment() {
		return isCashPayment;
	}

	public void setIsCashPayment(Boolean isCashPayment) {
		this.isCashPayment = isCashPayment;
	}

	public Integer getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(Integer discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public Integer getDiscountCodeCode() {
		return discountCodeCode;
	}

	public void setDiscountCodeCode(Integer discountCodeCode) {
		this.discountCodeCode = discountCodeCode;
	}

	public Integer getLeatherId() {
		return leatherId;
	}

	public void setLeatherId(Integer leatherId) {
		this.leatherId = leatherId;
	}

}
