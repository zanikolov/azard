package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.azard.model.Item;
import com.azard.model.RawItem;
import com.azard.service.ItemService;
import com.azard.service.RawItemExcelReaderService;
import com.azard.service.RawItemService;

@Service
public class RawItemServiceImpl implements RawItemService {

	@Autowired
	private RawItemExcelReaderService rawItemExcelReaderService;
	
	@Autowired
	private ItemService itemService;
	
	@Override
	@Transactional
	public List<RawItem> submitRawItemsFromFile(MultipartFile rawItemFile) {
		List<RawItem> rawItems = rawItemExcelReaderService.parseExcelData(rawItemFile);
		
		rawItems.forEach(rawItem -> {
			Item item = itemService.getItemByBarcode(rawItem.getBarcode());
			if (item != null) {
				rawItem.setBrandId(item.getBrandId());
				rawItem.setModelId(item.getModelId());
				rawItem.setLeatherId(item.getLeatherId());
				rawItem.setLeatherCode(item.getLeatherCode());
				rawItem.setLeatherName(item.getLeatherName());
			}
		});
		
		return rawItems;
	}

//	@Override
//	public void updateItemBarcode(Integer productId, Integer deviceModelId, String barcode) {
//		itemService.updateItemOnlyForIntegrationPurposes(productId, deviceModelId, barcode);
//	}

}
