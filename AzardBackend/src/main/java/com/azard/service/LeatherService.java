package com.azard.service;

import java.util.List;

import com.azard.model.Leather;

public interface LeatherService {

	List<Leather> getAllLeathers();

	void submitLeather(Leather product);

	void updateLeather(Leather product);

}
