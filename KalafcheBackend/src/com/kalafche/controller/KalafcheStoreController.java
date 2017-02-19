package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.KalafcheStoreDao;
import com.kalafche.model.KalafcheStore;

@CrossOrigin
@RestController
@RequestMapping({ "/service/kalafcheStore" })
public class KalafcheStoreController {
	@Autowired
	private KalafcheStoreDao kalafcheStoreDao;

	@RequestMapping(value = { "/getAllKalafcheStores" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<KalafcheStore> getAllKalafcheStores() {
		List<KalafcheStore> kalafcheStores = this.kalafcheStoreDao.getAllKalafcheStores();

		return kalafcheStores;
	}

	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void createKalafcheStore(@RequestBody KalafcheStore kalafcheStore) {

		this.kalafcheStoreDao.insertKalafcheStore(kalafcheStore);
	}
}
