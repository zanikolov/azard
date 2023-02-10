package com.azard.model;

public class LoyalCustomer {

	private Integer id;
	private String name;
	private String phoneNumber;
	private Integer discountCodeId;
	private Integer discountCodeCode;
	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private String brandName;
	private Integer createdById;
	private String createdByName;
	private Long createdTimestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Integer getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

}
