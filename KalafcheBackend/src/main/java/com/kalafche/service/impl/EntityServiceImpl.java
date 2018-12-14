package com.kalafche.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.StoreDto;
import com.kalafche.service.EntityService;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	KalafcheStoreDao storeDao;
	
	@Override
	public List<StoreDto> getStores() {
		return storeDao.selectStores();
	}
	
	@Override
	public String getStoreIdsByOwner(String owner) {
		return storeDao.selectStoreIdsByOwner(owner);
	}

}
