package com.azard.model.comparator;

import java.util.Comparator;

import com.azard.model.SaleItem;

public class SaleItemByItemPriceComparator implements Comparator<SaleItem> {

	@Override
	public int compare(SaleItem saleItem1, SaleItem saleItem2) {
		return -1 * saleItem1.getItemPrice().compareTo(saleItem2.getItemPrice());
	}

}
