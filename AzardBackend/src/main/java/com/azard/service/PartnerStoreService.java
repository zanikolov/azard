package com.azard.service;

import java.util.List;

import com.azard.model.PartnerStore;

public interface PartnerStoreService {

	List<PartnerStore> getAllPartnerStores();

	void createPartnerStore(PartnerStore partnerStore);

	void updatePartnerStore(PartnerStore partnerStore);

}
