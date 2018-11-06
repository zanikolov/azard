package com.kalafche.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalafche.dao.PartnerDao;
import com.kalafche.exceptions.DomainObjectNotFoundException;
import com.kalafche.model.Partner;
import com.kalafche.service.PartnerService;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	PartnerDao partnerDao;
	
	@Override
	public Partner getPartner(String code) {
		Partner partner = partnerDao.getPartnerByCode(code);
		
		if (partner == null) {
			throw new DomainObjectNotFoundException("partnerCode", "Unexisting partner.");
		} else {
			return partnerDao.getPartnerByCode(code);
		}
	}

	@Override
	public List<Partner> getAllPartners() {
		return partnerDao.getAllPartners();
	}

	@Override
	public void submitPartner(Partner partner) {
		partnerDao.insertPartner(partner);
	}
	
}
