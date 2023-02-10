package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.ItemDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Item;
import com.azard.model.ItemSpecificPricePerStore;
import com.azard.service.ItemService;
import com.mysql.cj.core.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;
	
	@Override
	public List<Item> getAllItems() {
		return itemDao.getAllItems();
	}

	@Override
	public Item getItemByBarcode(String barcode) {
		return itemDao.getItem(barcode);
	}
	
	@Override
	public Item getItemByLeatherIdAndModelId(Integer leatherId, Integer modelId) {
		return itemDao.getItem(leatherId, modelId);
	}

	@Override
	public void submitItem(Item item) {
		if (getItemByLeatherIdAndModelId(item.getLeatherId(), item.getModelId()) != null) {
			throw new DuplicationException("modelId", "Item duplicate.");
		}
		if (getItemByBarcode(item.getBarcode()) != null) {
			throw new DuplicationException("barcode", "Barcode duplicate.");
		}
		itemDao.insertItem(item.getLeatherId(), item.getModelId(), item.getBarcode(), item.getPrice());
	}

	@Override
	public void updateItem(Item item) {
		Item itemInDB = getItemByBarcode(item.getBarcode());
		if (itemInDB != null && itemInDB.getLeatherId() == item.getLeatherId() && itemInDB.getModelId() == item.getModelId()) {
			throw new DuplicationException("barcode", "Barcode duplicate.");
		}
		itemDao.updateItem(item.getId(), item.getBarcode(), item.getPrice());
		itemDao.deleteItemSpecificPricesPerStore(item.getId());
		item.getSpecificPrices().forEach(specificPrice -> itemDao.updateItemSpecificPricePerStore(specificPrice));
	}

	@Override
	public void insertItemOrUpdateBarcode(Item item) {
		Item existingItem = getItemByLeatherIdAndModelId(item.getLeatherId(), item.getModelId());
		if (existingItem != null) {
			if (StringUtils.isNullOrEmpty(existingItem.getBarcode())) {
				updateItem(item);
				return;
			} else {
				throw new DuplicationException("modelId", "Item duplicate.");
			}
		} else if (getItemByBarcode(item.getBarcode()) != null) {
			throw new DuplicationException("barcode", "Barcode duplicate.");
		} else {
			itemDao.insertItem(item.getLeatherId(), item.getModelId(), item.getBarcode(), item.getPrice());
		}
	}
	
	@Override
	public ItemSpecificPricePerStore getItemSpecificPrice(Integer productId, Integer storeId) {
		return itemDao.getItemSpecificPricePerStore(productId, storeId);
	}
	
	@Override
	public List<ItemSpecificPricePerStore> getItemSpecificPrice(Integer productId) {
		return itemDao.getItemSpecificPricePerStore(productId);
	}

	@Override
	public Item getItemByLeatherIdAndBrandIdAndModelId(Integer leatherId, int brandId, int modelId) {
		return itemDao.getItem(leatherId, brandId, modelId);
	}
	
}
