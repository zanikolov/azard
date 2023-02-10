package com.azard.model;

public class Model {
	private Integer id;
	private Integer brandId;
	private String name;

	public Integer getId() {
		return this.id;
	}
	
	public Integer setId(Integer id) {
		return this.id = id;
	}
	
	public Integer getBrandId() {
		return this.brandId;
	}
	
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
