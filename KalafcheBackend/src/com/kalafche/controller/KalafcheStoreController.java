package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.KalafcheStore;
import com.kalafche.model.StoreDto;

@CrossOrigin
@RestController
@RequestMapping({ "/service/kalafcheStore" })
public class KalafcheStoreController {
	@Autowired
	private KalafcheStoreDao kalafcheStoreDao;

	@RequestMapping(value = { "/getAllKalafcheStores" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<KalafcheStore> getAllKalafcheEntities() {
		List<KalafcheStore> kalafcheStores = this.kalafcheStoreDao.getAllKalafcheEntities();

		return kalafcheStores;
	}
	
	@RequestMapping(value = { "/getStores" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<StoreDto> getAllStores() {
		List<StoreDto> storeDtos = this.kalafcheStoreDao.getAllStores();

		return storeDtos;
	}

	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void createKalafcheStore(@RequestBody KalafcheStore kalafcheStore) {

		this.kalafcheStoreDao.insertKalafcheStore(kalafcheStore);
	}
}
