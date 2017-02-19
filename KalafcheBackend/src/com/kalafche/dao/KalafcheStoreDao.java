package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.KalafcheStore;

public interface KalafcheStoreDao {

	public abstract List<KalafcheStore> getAllKalafcheStores();

	public abstract void insertKalafcheStore(KalafcheStore kalafcheStore);

}
