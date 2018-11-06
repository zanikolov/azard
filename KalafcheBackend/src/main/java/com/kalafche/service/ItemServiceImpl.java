package com.kalafche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalafche.dao.ItemDao;
import com.kalafche.exceptions.DuplicationException;
import com.kalafche.model.Item;

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
	public Item getItemByProductIdAndDeviceModelId(Integer productId, Integer deviceModelId) {
		return itemDao.getItem(productId, deviceModelId);
	}

	@Override
	public void submitItem(Item item) {
		if (getItemByProductIdAndDeviceModelId(item.getProductId(), item.getDeviceModelId()) != null) {
			throw new DuplicationException("productCode", "Item duplicate.");
		}
		if (getItemByBarcode(item.getBarcode()) != null) {
			throw new DuplicationException("barcode", "Barcode duplicate.");
		}
		itemDao.insertItem(item.getProductId(), item.getDeviceModelId(), item.getBarcode());
	}

	@Override
	public void updateItem(Item item) {
		if (getItemByBarcode(item.getBarcode()) != null) {
			throw new DuplicationException("barcode", "Barcode duplicate.");
		}
		itemDao.updateItemBarcode(item.getId(), item.getBarcode());
	}

}
