package com.kalafche.service.impl;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.azard.dao.ItemDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Item;
import com.azard.service.impl.ItemServiceImpl;
import com.azard.service.impl.SaleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SaleServiceImplTest {
	
	//@InjectMocks
	private SaleServiceImpl saleServiceImpl;
	
	@Test
	public void testSearchSalesForPastPeriodsByStores() throws Exception {
		saleServiceImpl = new SaleServiceImpl();
		//saleServiceImpl.searchSalesForPastPeriodsByStores(7);
	}

}
