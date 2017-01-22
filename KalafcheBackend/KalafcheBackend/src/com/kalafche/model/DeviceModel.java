package com.kalafche.model;

public class DeviceModel {
	private int id;
	private int deviceBrandId;
	private int deviceTypeId;
	private String name;

	public int getId() {
		return this.id;
	}
	
	public int setId(int id) {
		return this.id = id;
	}
	
	public int getDeviceBrandId() {
		return this.deviceBrandId;
	}
	
	public void setDeviceBrandId(int deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

}
