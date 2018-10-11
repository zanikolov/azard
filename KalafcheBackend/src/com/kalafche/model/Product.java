package com.kalafche.model;

import org.springframework.web.multipart.MultipartFile;

import com.kalafche.BaseModel;

public class Product extends BaseModel {
	private int id;
	private String name;
	private String onlineName;
	private String code;
	private String description;
	private float price;
	private float purchasePrice;
	private MultipartFile pic;

	public String getCode() {
		return code;
	}

	public void setCode(String productCode) {
		this.code = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return this.id;
	}

	public int setId(int id) {
		return this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPurchasePrice() {
		if (userHasRole("ROLE_SUPERADMIN")) {
			return purchasePrice;
		} else {
			return 0;
		}
	}

	public void setPurchasePrice(float purchasePrice) {
		if (userHasRole("ROLE_SUPERADMIN")) {
			this.purchasePrice = purchasePrice;
		}
	}

	public MultipartFile getPic() {
		return pic;
	}

	public void setPic(MultipartFile pic) {
		this.pic = pic;
	}

	public String getOnlineName() {
		return onlineName;
	}

	public void setOnlineName(String onlineName) {
		this.onlineName = onlineName;
	}
}
