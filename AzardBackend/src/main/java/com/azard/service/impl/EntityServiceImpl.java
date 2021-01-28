package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.StoreDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.StoreDto;
import com.azard.service.EntityService;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<StoreDto> getStores() {
		return storeDao.selectStores();
	}
	
	@Override
	public String getStoreIdsByOwner(String owner) {
		return storeDao.selectStoreIdsByOwner(owner);
	}

	@Override
	@Transactional
	public void createEntity(StoreDto store) {
		validateStoreCode(store);
		validateStoreName(store);
		storeDao.insertStore(store);
	}
	
	@Override
	@Transactional
	public void updateEntity(StoreDto store) {
		validateStoreCode(store);
		validateStoreName(store);
		storeDao.updateStore(store);
	}
	
	private void validateStoreName(StoreDto store) {
		if (storeDao.checkIfStoreNameExists(store)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}
	
	private void validateStoreCode(StoreDto store) {
		if (storeDao.checkIfStoreCodeExists(store)) {
			throw new DuplicationException("code", "Code duplication.");
		}
	}

}
