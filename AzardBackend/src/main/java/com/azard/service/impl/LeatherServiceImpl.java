package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.LeatherDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Leather;
import com.azard.service.LeatherService;
import com.azard.service.StockService;

@Service
public class LeatherServiceImpl implements LeatherService {

	@Autowired
	LeatherDao leatherDao;
	
	@Autowired
	StockService stockService;
	
	@Override
	public List<Leather> getAllLeathers() {
		return leatherDao.getAllLeathers();
	}

	@Override
	@Transactional
	public void submitLeather(Leather leather) {
		validateLeatherName(leather);
		leatherDao.insertLeather(leather);
	}

	@Override
	@Transactional
	public void updateLeather(Leather leather) {
		validateLeatherName(leather);
		leatherDao.updateLeather(leather);
	}
	
	private void validateLeatherName(Leather leather) {
		if (leatherDao.checkIfLeatherNameExists(leather)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}

}
