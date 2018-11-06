package com.kalafche.service;

import java.util.List;

import com.kalafche.model.Item;
import com.kalafche.model.Product;

public interface ItemService {

	List<Item> getAllItems();

	Item getItemByBarcode(String barcode);

	void submitItem(Item item);

	void updateItem(Item item);

	Item getItemByProductIdAndDeviceModelId(Integer productId, Integer deviceModelId);

}
