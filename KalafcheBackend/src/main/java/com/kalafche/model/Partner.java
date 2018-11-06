package com.kalafche.model;

public class Partner {
	private Integer id;
	private String code;
	private String name;
	private Integer partnerStoreId;
	private String partnerStoreName;
	private String phoneNumber;

	public Integer getId() {
		return this.id;
	}
	
	public Integer setId(Integer id) {
		return this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPartnerStoreId() {
		return partnerStoreId;
	}

	public void setPartnerStoreId(Integer partnerStoreId) {
		this.partnerStoreId = partnerStoreId;
	}

	public String getPartnerStoreName() {
		return partnerStoreName;
	}

	public void setPartnerStoreName(String partnerStoreName) {
		this.partnerStoreName = partnerStoreName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
