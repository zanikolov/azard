package com.azard.service;

import java.util.List;

import com.azard.model.Partner;

public interface PartnerService {

	Partner getPartner(String code);

	List<Partner> getAllPartners();

	void createPartner(Partner partner);

	void updatePartner(Partner partner);

}
