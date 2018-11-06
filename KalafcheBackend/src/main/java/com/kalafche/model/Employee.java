package com.kalafche.model;

import java.util.List;

public class Employee {

	private int id;
	private String name;
	private int kalafcheStoreId;
	private String kalafcheStoreName;
	private int jobResponsibilityId;
	private String jobResponsibilityName;
	private int userId;
	private String username;
	private String password;
	private List<AuthRole> roles;
	private boolean enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKalafcheStoreId() {
		return kalafcheStoreId;
	}

	public void setKalafcheStoreId(int kalafcheStoreId) {
		this.kalafcheStoreId = kalafcheStoreId;
	}

	public int getJobResponsibilityId() {
		return jobResponsibilityId;
	}

	public void setJobResponsibilityId(int jobResponsibilityId) {
		this.jobResponsibilityId = jobResponsibilityId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<AuthRole> getRoles() {
		return roles;
	}

	public void setRoles(List<AuthRole> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobResponsibilityName() {
		return jobResponsibilityName;
	}

	public void setJobResponsibilityName(String jobResponsibilityName) {
		this.jobResponsibilityName = jobResponsibilityName;
	}

	public String getKalafcheStoreName() {
		return kalafcheStoreName;
	}

	public void setKalafcheStoreName(String kalafcheStoreName) {
		this.kalafcheStoreName = kalafcheStoreName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
