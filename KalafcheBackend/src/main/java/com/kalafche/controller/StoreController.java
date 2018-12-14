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
import com.kalafche.model.StoreDto;
import com.kalafche.service.EntityService;

@CrossOrigin
@RestController
@RequestMapping({ "/store" })
public class StoreController {
	
	@Autowired
	private KalafcheStoreDao kalafcheStoreDao;
	
	@Autowired
	EntityService entityService;

	@GetMapping("/getAllKalafcheStores")
	public List<StoreDto> getAllKalafcheEntities() {
		return kalafcheStoreDao.getAllKalafcheEntities();
	}

	@PostMapping
	public void createKalafcheStore(@RequestBody StoreDto kalafcheStore) {
		this.kalafcheStoreDao.insertKalafcheStore(kalafcheStore);
	}

	@GetMapping("/stores")
	public List<StoreDto> getStores() {
		return entityService.getStores();
	}
	
}
