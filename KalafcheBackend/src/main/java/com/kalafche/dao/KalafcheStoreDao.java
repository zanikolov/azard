package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.StoreDto;

public interface KalafcheStoreDao {

	public abstract List<StoreDto> getAllKalafcheEntities();

	public abstract void insertKalafcheStore(StoreDto kalafcheStore);

	public abstract List<StoreDto> selectStores();

	public abstract String selectStoreIdsByOwner(String owner);

	public abstract StoreDto selectStore(String storeId);

}
