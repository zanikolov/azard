package com.azard.model;

import java.math.BigDecimal;

public class RevisionItem {

	private Integer id;
	private Integer revisionId;
	private Integer itemId;
	private Integer leatherId;
	private String leatherCode;
	private String leatherName;
	private String barcode;
	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private BigDecimal itemPrice;
	private Integer balance;
	private Integer expected;
	private Integer actual;
	private Boolean partOfTheCurrentRevision = true;
	private Boolean synced;

	public RevisionItem() {
	}

	public RevisionItem(Integer revisionId, Item item, Integer expected, Integer actual) {
		this(revisionId, item.getId(), item.getLeatherId(), item.getLeatherCode(), item.getLeatherName(),
				item.getBarcode(), item.getModelId(), item.getModelName(), item.getBrandId(), item.getPrice(), 0, 0,
				false);
	}

	public RevisionItem(Integer revisionId, Integer itemId, Integer leatherId, String leatherCode, String leatherName,
			String barcode, Integer deviceModelId, String deviceModelName, Integer deviceBrandId,
			BigDecimal productPrice, Integer expected, Integer actual, Boolean synced) {
		this.revisionId = revisionId;
		this.itemId = itemId;
		this.leatherId = leatherId;
		this.leatherCode = leatherCode;
		this.leatherName = leatherName;
		this.barcode = barcode;
		this.modelId = modelId;
		this.modelName = modelName;
		this.brandId = brandId;
		this.itemPrice = itemPrice;
		this.expected = expected;
		this.actual = actual;
		this.synced = synced;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Integer revisionId) {
		this.revisionId = revisionId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getLeatherId() {
		return leatherId;
	}

	public void setLeatherId(Integer leatherId) {
		this.leatherId = leatherId;
	}

	public String getLeatherCode() {
		return leatherCode;
	}

	public void setLeatherCode(String leatherCode) {
		this.leatherCode = leatherCode;
	}

	public String getLeatherName() {
		return leatherName;
	}

	public void setLeatherName(String leatherName) {
		this.leatherName = leatherName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getExpected() {
		return expected;
	}

	public void setExpected(Integer expected) {
		this.expected = expected;
	}

	public Integer getActual() {
		return actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	public Boolean getPartOfTheCurrentRevision() {
		return partOfTheCurrentRevision;
	}

	public void setPartOfTheCurrentRevision(Boolean partOfTheCurrentRevision) {
		this.partOfTheCurrentRevision = partOfTheCurrentRevision;
	}

	public Boolean getSynced() {
		return synced;
	}

	public void setSynced(Boolean synced) {
		this.synced = synced;
	}

}
