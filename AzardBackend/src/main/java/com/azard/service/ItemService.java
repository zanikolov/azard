package com.azard.service;

import java.util.List;

import com.azard.model.Item;
import com.azard.model.ItemSpecificPricePerStore;

public interface ItemService {

	List<Item> getAllItems();

	Item getItemByBarcode(String barcode);

	void submitItem(Item item);

	void updateItem(Item item);

	Item getItemByLeatherIdAndModelId(Integer productId, Integer deviceModelId);

	void insertItemOrUpdateBarcode(Item item);

	ItemSpecificPricePerStore getItemSpecificPrice(Integer productId, Integer storeId);

	List<ItemSpecificPricePerStore> getItemSpecificPrice(Integer productId);

	Item getItemByLeatherIdAndBrandIdAndModelId(Integer leatherId, int brandId, int modelId);

}
