package com.azard.dao;

public interface WarehouseDao {

	public abstract void upsertStock(Integer itemId, Integer quantity);
	
}
