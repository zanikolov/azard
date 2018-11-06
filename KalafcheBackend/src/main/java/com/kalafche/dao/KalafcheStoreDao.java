package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.KalafcheStore;
import com.kalafche.model.StoreDto;

public interface KalafcheStoreDao {

	public abstract List<KalafcheStore> getAllKalafcheEntities();

	public abstract void insertKalafcheStore(KalafcheStore kalafcheStore);
	
	public abstract List<StoreDto> getAllStores();

}
