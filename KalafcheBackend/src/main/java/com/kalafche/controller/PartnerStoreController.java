package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.PartnerStoreDao;
import com.kalafche.model.PartnerStore;

@CrossOrigin
@RestController
@RequestMapping({ "/service/partnerStore" })
public class PartnerStoreController {

	@Autowired
	private PartnerStoreDao partnerStoreDao;

	@GetMapping("/getAllPartnerStores")
	public List<PartnerStore> getAllPartnerStores() {
		List<PartnerStore> partnerStores = this.partnerStoreDao.getAllPartnerStores();

		return partnerStores;
	}
	
	@PostMapping("/insertPartnerStore")
	public void insertPartnerStore(@RequestBody PartnerStore partnerStore) {
		this.partnerStoreDao.insertPartnerStore(partnerStore);
	}
}
