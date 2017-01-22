package com.kalafche.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.BaseController;
import com.kalafche.dao.ItemDao;
import com.kalafche.model.Item;

@CrossOrigin
@RestController
@RequestMapping({ "/service/item" })
public class ItemController extends BaseController {
	@Autowired
	private ItemDao itemDao;

	@RequestMapping(value = { "/getAllItems" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Item> getAllItems() {
		List<Item> items = this.itemDao.getAllItems();

		return items;
	}

    //public @ResponseBody void storeAd1(MultipartHttpServletRequest request) {
	
	@RequestMapping(value = { "/insertItem" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public Item insertItem(@RequestBody Item item) {
		Item insertedItem = null;
		
		try {
			insertedItem = this.itemDao.insertItem(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insertedItem;
	}
	
	@RequestMapping(value = { "/updateItem" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void updateItem(@RequestBody Item item) {

		this.itemDao.updateItem(item, userHasRole("ROLE_SUPERADMIN"));
	}
}
