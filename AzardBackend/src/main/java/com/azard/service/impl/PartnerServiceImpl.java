package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.PartnerDao;
import com.azard.exceptions.DomainObjectNotFoundException;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Partner;
import com.azard.service.PartnerService;

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
			return partner;
		}
	}

	@Override
	public List<Partner> getAllPartners() {
		return partnerDao.getAllPartners();
	}

	@Override
	public void createPartner(Partner partner) {
		validateName(partner);
		validateDiscountCode(partner);
		partnerDao.insertPartner(partner);
	}

	@Override
	public void updatePartner(Partner partner) {
		partnerDao.updatePartner(partner);
	}
	
	private void validateName(Partner partner) {
		if (partnerDao.checkIfPartnerNameExists(partner)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}
	
	private void validateDiscountCode(Partner partner) {
		if (partnerDao.checkIfPartnerDiscountCodeExists(partner)) {
			throw new DuplicationException("discountCodeId", "Code duplication.");
		}
	}
	
}
