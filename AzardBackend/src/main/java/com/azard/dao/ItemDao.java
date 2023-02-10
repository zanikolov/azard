package com.azard.dao;

import java.math.BigDecimal;
import java.util.List;

import com.azard.model.Item;
import com.azard.model.ItemSpecificPricePerStore;

public interface ItemDao {

	public abstract Item getItem(Integer productId, Integer deviceModelId);
	
	public abstract Item getItem(String barcode);
	
	public abstract List<Item> getAllItems();

	public abstract void insertItem(Integer productId, Integer deviceModelId, String barcode, BigDecimal price);

	public abstract Item getItem(Integer id);

	public abstract BigDecimal getItemPriceByStoreId(Integer itemId, Integer storeId);

	public abstract void updateItemBarcode(Integer productId, Integer modelId, String barcode);

	void updateItemSpecificPricePerStore(ItemSpecificPricePerStore specificPrice);

	void deleteItemSpecificPricesPerStore(Integer itemId);

	void updateItem(Integer id, String barcode, BigDecimal price);

	List<ItemSpecificPricePerStore> getItemSpecificPricePerStore(Integer itemId);

	ItemSpecificPricePerStore getItemSpecificPricePerStore(Integer itemId, Integer storeId);

	public abstract void updateItemPrice(Integer id, BigDecimal price);

	public abstract Item getItem(Integer leatherId, Integer brandId, Integer modelId);
	
}
