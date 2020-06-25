package com.kalafche.service;

import java.util.List;

import com.kalafche.model.StoreDto;

public interface EntityService {

	List<StoreDto> getStores();

	String getStoreIdsByOwner(String owner);

	void createEntity(StoreDto store);

	void updateEntity(StoreDto store);

}
