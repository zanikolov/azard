package com.azard.model;

import java.math.BigDecimal;

public class NewStock extends BaseStock {

	private Integer importId;
	private String importFileName;
	private Integer storeId;
	private BigDecimal price;

	public Integer getImportId() {
		return importId;
	}

	public void setImportId(Integer importId) {
		this.importId = importId;
	}

	public String getImportFileName() {
		return importFileName;
	}

	public void setImportFileName(String importFileName) {
		this.importFileName = importFileName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
