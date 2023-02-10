package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.BaseController;
import com.azard.model.Item;
import com.azard.model.ItemSpecificPricePerStore;
import com.azard.service.ItemService;

@CrossOrigin
@RestController
@RequestMapping({ "/item" })
public class ItemController  extends BaseController {
	
	@Autowired
	private ItemService itemService;

	@GetMapping
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}
	
	@GetMapping("/{barcode}")
	public Item getItemByBarcode(@PathVariable(value = "barcode") String barcode) {
		return itemService.getItemByBarcode(barcode);
	}
	
	@PutMapping
	public void insertItem(@RequestBody Item item) {
		itemService.submitItem(item);
	}
	
	@PostMapping
	public void updateItem(@RequestBody Item item) {
		itemService.updateItem(item);
	}
	
	@PutMapping("/upsert")
	public void insertItemOrUpdateBarcode(@RequestBody Item item) {
		itemService.insertItemOrUpdateBarcode(item);
	}

	@GetMapping("/specificPrice/{itemId}")
	public List<ItemSpecificPricePerStore> getItemSpecificPricePerStore(@PathVariable(value = "itemId", required = false) Integer itemId) {
		return itemService.getItemSpecificPrice(itemId);
	}
	
	@PostMapping("/exists")
	public Item checkIfItemExists(@RequestBody Item item) {
		return itemService.getItemByLeatherIdAndBrandIdAndModelId(item.getLeatherId(), item.getBrandId(), item.getModelId());
	}
}