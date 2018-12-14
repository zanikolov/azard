package com.kalafche.model;

import java.math.BigDecimal;
import java.util.List;

public class TotalSumRequest {

	private List<BigDecimal> prices;
	private Integer discount;

	public List<BigDecimal> getPrices() {
		return prices;
	}

	public void setPrices(List<BigDecimal> prices) {
		this.prices = prices;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

}
