package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/getAllKalafcheStores")
	public List<KalafcheStore> getAllKalafcheEntities() {
		return kalafcheStoreDao.getAllKalafcheEntities();
	}
	
	@GetMapping("/getStores")
	public List<StoreDto> getAllStores() {
		return kalafcheStoreDao.getAllStores();
	}

	@PostMapping
	public void createKalafcheStore(@RequestBody KalafcheStore kalafcheStore) {
		this.kalafcheStoreDao.insertKalafcheStore(kalafcheStore);
	}
	
}
