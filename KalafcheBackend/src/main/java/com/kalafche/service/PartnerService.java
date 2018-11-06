package com.kalafche.service;

import java.util.List;

import com.kalafche.model.Partner;

public interface PartnerService {

	Partner getPartner(String code);

	List<Partner> getAllPartners();

	void submitPartner(Partner partner);

}
