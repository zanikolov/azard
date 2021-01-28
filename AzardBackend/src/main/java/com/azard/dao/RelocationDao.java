package com.azard.dao;

import java.util.List;

import com.azard.enums.RelocationStatus;
import com.azard.model.Relocation;

public abstract interface RelocationDao {
	
	public abstract List<Relocation> getRelocations(Integer sourceStoreId, Integer destStoreId, boolean isCompleted);

	public abstract void insertRelocation(Relocation stockRelocation);

	public abstract void archiveStockRelocation(Integer stockRelocationId);
	
	public abstract void updateRelocationStatus(Integer relocationId, RelocationStatus status);
	
	public abstract RelocationStatus getRelocationStatus(Integer relocationId);

	public abstract void updateRelocationCompleteTimestamp(Integer relocationId, long completeTimestamp);
	
}
