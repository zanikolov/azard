package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.ColorDao;
import com.kalafche.model.Color;

@CrossOrigin
@RestController
@RequestMapping({ "/service/color" })
public class ColorController {
	
	@Autowired
	private ColorDao colorDao;

	@RequestMapping(value = { "/getAllColors" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Color> getAllColors() {
		List<Color> colors = this.colorDao.getAllColors();

		return colors;
	}
}
