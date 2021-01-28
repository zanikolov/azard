package com.azard.service;

import java.util.List;

import com.azard.model.Item;

public interface ItemService {

	List<Item> getAllItems();

	Item getItemByBarcode(String barcode);

	void submitItem(Item item);

	void updateItem(Item item);

	Item getItemByProductIdAndDeviceModelId(Integer productId, Integer deviceModelId);

	void updateItemOnlyForIntegrationPurposes(Integer productId, Integer deviceModelId, String barcode);

	void insertItemOrUpdateBarcode(Item item);

}
