package com.azard.dao;

import java.util.List;

import com.azard.model.StoreDto;

public interface StoreDao {

	public abstract List<StoreDto> getAllEntities();

	public abstract void insertStore(StoreDto store);

	public abstract List<StoreDto> selectStores();

	public abstract String selectStoreIdsByOwner(String owner);

	public abstract StoreDto selectStore(String storeId);

	public abstract Boolean checkIfStoreNameExists(StoreDto store);

	public abstract Boolean checkIfStoreCodeExists(StoreDto store);

	public abstract void updateStore(StoreDto store);

}
