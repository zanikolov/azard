package com.azard.service;

import java.util.List;

import com.azard.model.Relocation;

public interface RelocationService {

	public abstract void submitRelocation(Relocation relocation);

	public abstract void changeRelocationStatus(Relocation relocation);

	public abstract void archiveRelocation(Integer stockRelocationId);

	public abstract List<Relocation> getRelocations(Integer sourceStoreId, Integer destStoreId, boolean isCompleted);
	
}
