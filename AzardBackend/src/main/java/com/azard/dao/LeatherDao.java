package com.azard.dao;

import java.util.List;

import com.azard.model.Leather;

public abstract interface LeatherDao {
	
	public abstract List<Leather> getAllLeathers();

	public abstract void insertLeather(Leather leather);

	public abstract void updateLeather(Leather leather);

	public abstract boolean checkIfLeatherNameExists(Leather leather);
	
}
