package com.azard.service.impl;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.azard.dao.ItemDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Item;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {
	
	@Mock
	private ItemDao itemDao;
	
	@InjectMocks
	private ItemServiceImpl itemServiceImpl;

	@Test(expected = DuplicationException.class)
	public void testInsertItemOrUpdateBarcode_duplicateItemWithDifferentBarcode() throws Exception {	
		Item item = new Item();
		item.setBarcode("1111111111111");
		item.setModelId(1);
		item.setLeatherId(1);
		
		Item existingItem = new Item();
		existingItem.setBarcode("1111111111112");
		existingItem.setModelId(1);
		existingItem.setLeatherId(1);
		
		when(itemServiceImpl.getItemByLeatherIdAndModelId(1, 1)).thenReturn(existingItem);
		
		itemServiceImpl.insertItemOrUpdateBarcode(item);	
	}
	
	@Test(expected = DuplicationException.class)
	public void testInsertItemOrUpdateBarcode_duplicateBarcode() throws Exception {
		Item item = new Item();
		item.setBarcode("1111111111111");
		item.setModelId(1);
		item.setLeatherId(1);
		
		Item existingItem = new Item();
		existingItem.setBarcode("1111111111111");
		existingItem.setModelId(1);
		existingItem.setLeatherId(2);
		
		when(itemServiceImpl.getItemByLeatherIdAndModelId(1, 1)).thenReturn(null);
		when(itemServiceImpl.getItemByBarcode("1111111111111")).thenReturn(existingItem);
		
		itemServiceImpl.insertItemOrUpdateBarcode(item);	
	}
	
	@Test
	public void testInsertItemOrUpdateBarcode_update() throws Exception {
		Item item = new Item();
		item.setId(1);
		item.setBarcode("1111111111111");
		item.setModelId(1);
		item.setLeatherId(1);
		
		Item existingItem = new Item();
		existingItem.setModelId(1);
		existingItem.setLeatherId(1);
		
		when(itemServiceImpl.getItemByLeatherIdAndModelId(1, 1)).thenReturn(existingItem);
		
		itemServiceImpl.insertItemOrUpdateBarcode(item);
		
		Mockito.verify(itemDao, Mockito.times(1)).updateItem(1, "1111111111111", new BigDecimal(100)); 	
	}	
	
	@Test
	public void testInsertItemOrUpdateBarcode_insert() throws Exception {
		Item item = new Item();
		item.setId(1);
		item.setBarcode("1111111111111");
		item.setModelId(1);
		item.setLeatherId(1);
		
		when(itemServiceImpl.getItemByLeatherIdAndModelId(1, 1)).thenReturn(null);
		when(itemServiceImpl.getItemByBarcode("1111111111111")).thenReturn(null);
		
		itemServiceImpl.insertItemOrUpdateBarcode(item);
		
		Mockito.verify(itemDao, Mockito.times(1)).insertItem(1, 1, "1111111111111", new BigDecimal(10));
	}

}
