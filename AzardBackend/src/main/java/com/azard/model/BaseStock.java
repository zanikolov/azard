package com.azard.model;

import java.math.BigDecimal;

public class BaseStock {

	private Integer id;
	private Integer itemId;
	private Integer leatherId;
	private String leatherName;
	private Integer brandId;
	private String brandName;
	private Integer modelId;
	private String modelName;
	private Integer quantity;
	private BigDecimal price;
	private String barcode;

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

	public Integer getLeatherId() {
		return leatherId;
	}

	public void setLeatherId(Integer leatherId) {
		this.leatherId = leatherId;
	}

	public String getLeatherName() {
		return leatherName;
	}

	public void setLeatherName(String leatherName) {
		this.leatherName = leatherName;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
