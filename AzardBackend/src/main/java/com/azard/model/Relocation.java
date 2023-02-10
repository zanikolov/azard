package com.azard.model;

import java.math.BigDecimal;

import com.azard.BaseModel;
import com.azard.enums.RelocationStatus;

public class Relocation extends BaseModel {
	private int id;
	private int stockId;
	private int itemId;
	private String leatherName;
	private String leatherCode;
	private BigDecimal itemPrice;
	private int sourceStoreId;
	private String sourceStoreName;
	private String sourceStoreCode;
	private int destStoreId;
	private String destStoreName;
	private String destStoreCode;
	private int modelId;
	private String modelName;
	private int brandId;
	private long relocationRequestTimestamp;
	private long relocationCompleteTimestamp;
	private int employeeId;
	private String employeeName;
	private boolean approved;
	private boolean rejected;
	private boolean arrived;
	private int quantity;
	private BigDecimal relocationAmount;
	private boolean archived;
	private RelocationStatus status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getSourceStoreId() {
		return sourceStoreId;
	}
	public void setSourceStoreId(int sourceStoreId) {
		this.sourceStoreId = sourceStoreId;
	}
	public String getSourceStoreName() {
		return sourceStoreName;
	}
	public void setSourceStoreName(String sourceStoreName) {
		this.sourceStoreName = sourceStoreName;
	}
	public String getSourceStoreCode() {
		return sourceStoreCode;
	}
	public void setSourceStoreCode(String sourceStoreCode) {
		this.sourceStoreCode = sourceStoreCode;
	}
	public int getDestStoreId() {
		return destStoreId;
	}
	public void setDestStoreId(int destStoreId) {
		this.destStoreId = destStoreId;
	}
	public String getDestStoreName() {
		return destStoreName;
	}
	public void setDestStoreName(String destStoreName) {
		this.destStoreName = destStoreName;
	}
	public String getDestStoreCode() {
		return destStoreCode;
	}
	public void setDestStoreCode(String destStoreCode) {
		this.destStoreCode = destStoreCode;
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
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public long getRelocationRequestTimestamp() {
		return relocationRequestTimestamp;
	}
	public void setRelocationRequestTimestamp(long relocationRequestTimestamp) {
		this.relocationRequestTimestamp = relocationRequestTimestamp;
	}
	public long getRelocationCompleteTimestamp() {
		return relocationCompleteTimestamp;
	}
	public void setRelocationCompleteTimestamp(long relocationCompleteTimestamp) {
		this.relocationCompleteTimestamp = relocationCompleteTimestamp;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public boolean isRejected() {
		return rejected;
	}
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	public boolean isArrived() {
		return arrived;
	}
	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getRelocationAmount() {
		return relocationAmount;
	}
	public void setRelocationAmount(BigDecimal relocationAmount) {
		this.relocationAmount = relocationAmount;
	}
	public boolean isArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	public RelocationStatus getStatus() {
		return status;
	}
	public void setStatus(RelocationStatus status) {
		this.status = status;
	}

}
