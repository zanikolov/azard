package com.azard.service;

import java.sql.SQLException;
import java.util.List;

import com.azard.model.DiscountCampaign;
import com.azard.model.DiscountCode;
import com.azard.model.DiscountType;

public interface DiscountService {

	DiscountCampaign createDiscountCampaign(DiscountCampaign discountCampaign) throws SQLException;

	DiscountCampaign getDiscountCampaign(Integer discountCampaignId);

	List<DiscountType> getDiscountTypes();

	DiscountCode createDiscountCode(DiscountCode discountCode) throws SQLException;

	List<DiscountCampaign> getAllDiscountCampaigns();

	void updateDiscountCampaign(DiscountCampaign discountCampaign);

	void updateDiscountCode(DiscountCode discountCode);

	List<DiscountCode> getDiscountCodes();

	DiscountCode getDiscountCode(Integer code);

	List<DiscountCode> getAvailableDiscountCodesForPartnerCampaign();
	
	List<DiscountCode> getAvailableDiscountCodesForLoyalCampaign();

}
