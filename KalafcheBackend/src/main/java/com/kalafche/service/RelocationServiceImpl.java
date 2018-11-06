package com.kalafche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.RelocationDao;
import com.kalafche.dao.StockDao;
import com.kalafche.dao.impl.StockDaoImpl;
import com.kalafche.enums.RelocationStatus;
import com.kalafche.model.Relocation;

@Service
public class RelocationServiceImpl implements RelocationService {


	@Autowired
	DateService dateService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	StockDaoImpl stockDao;
	
	@Autowired
	RelocationDao relocationDao;

	@Transactional
	@Override
	public void submitRelocation(Relocation relocation) {
		relocation.setRelocationRequestTimestamp(dateService.getCurrentMillisBGTimezone());
		relocation.setEmployeeId(employeeService.getLoggedInEmployee().getId());
		relocation.setStatus(RelocationStatus.INITIATED);
		
		this.relocationDao.insertRelocation(relocation);
		this.stockDao.updateQuantityOfApprovedStock(relocation.getItemId(), relocation.getSourceStoreId(), relocation.getQuantity() * (-1));
	}
	
	@Transactional
	@Override
	public void changeRelocationStatus(Relocation relocation) {
		this.relocationDao.updateRelocationStatus(relocation.getId(), relocation.getStatus());

		if (relocation.getStatus().equals(RelocationStatus.REJECTED)) {
			processCompletedRelocation(relocation.getId(), relocation.getQuantity(), relocation.getItemId(),
					relocation.getSourceStoreId());
		} else if (relocation.getStatus().equals(RelocationStatus.DELIVERED)) {
			processCompletedRelocation(relocation.getId(), relocation.getQuantity(), relocation.getItemId(),
					relocation.getDestStoreId());
		}
	}

	private void processCompletedRelocation(int relocationId, int quantity, int itemId, int storeId) {
		this.relocationDao.updateRelocationCompleteTimestamp(relocationId, dateService.getCurrentMillisBGTimezone());
		stockDao.insertOrUpdateQuantityOfInStock(itemId, storeId, quantity);
	}

	@Override
	public void archiveRelocation(Integer stockRelocationId) {
		relocationDao.archiveStockRelocation(stockRelocationId);	
	}

	@Override
	public List<Relocation> getRelocations(Integer sourceStoreId, Integer destStoreId, boolean isCompleted) {
		return relocationDao.getRelocations(sourceStoreId, destStoreId, isCompleted);
	}

}