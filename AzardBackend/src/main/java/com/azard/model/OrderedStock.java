package com.azard.model;

public class OrderedStock {

	private int id;
	private int leatherId;
	private String leatherName;
	private String leatherCode;
	private int modelId;
	private String modelName;
	private int brandId;
	private String brandName;
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

	public int getLeatherId() {
		return leatherId;
	}

	public void setLeatherId(int leatherId) {
		this.leatherId = leatherId;
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

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getStockOrderId() {
		return stockOrderId;
	}

	public void setStockOrderId(int stockOrderId) {
		this.stockOrderId = stockOrderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

}
