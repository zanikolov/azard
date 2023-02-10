package com.azard.model;

import java.math.BigDecimal;
import java.util.List;

public class Item {

	private int id;
	private int brandId;
	private int modelId;
	private String modelName;
	private String purchasePrice;
	private Integer leatherId;
	private String leatherName;
	private String leatherCode;
	private String barcode;
	private BigDecimal price;
	private List<ItemSpecificPricePerStore> specificPrices;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
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

	public String getLeatherCode() {
		return leatherCode;
	}

	public void setLeatherCode(String leatherCode) {
		this.leatherCode = leatherCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public List<ItemSpecificPricePerStore> getSpecificPrices() {
		return specificPrices;
	}

	public void setSpecificPrices(List<ItemSpecificPricePerStore> specificPrices) {
		this.specificPrices = specificPrices;
	}

}
