package com.kalafche.model;

public class Partner {
	private int id;
	private String code;
	private String name;
	private int partnerStoreId;
	private String partnerStoreName;
	private String phoneNumber;

	public int getId() {
		return this.id;
	}
	
	public int setId(int id) {
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

	public int getPartnerStoreId() {
		return partnerStoreId;
	}

	public void setPartnerStoreId(int partnerStoreId) {
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
