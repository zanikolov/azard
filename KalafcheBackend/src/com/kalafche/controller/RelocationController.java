package com.kalafche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.model.Relocation;
import com.kalafche.service.RelocationService;

@CrossOrigin
@RestController
@RequestMapping({ "/relocation" })
public class RelocationController {
	
	@Autowired
	RelocationService relocationService;


	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.PUT })
	public void createRelocation(@RequestBody Relocation relocation) {
		relocationService.submitRelocation(relocation);
	}	
	
	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void updateRelocation(@RequestBody Relocation relocation) {
		relocationService.changeRelocationStatus(relocation);
	}	
	
	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Relocation> getRelocations(@RequestParam(value = "sourceStoreId", required = false) Integer sourceStoreId,
			@RequestParam(value = "destStoreId", required = false) Integer destStoreId,
			@RequestParam(value = "isCompleted") boolean isCompleted) {
		List<Relocation> stockRelocations = this.relocationService.getRelocations(sourceStoreId, destStoreId, isCompleted);

		return stockRelocations;
	}
	
	@RequestMapping(value = { "/archive" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void archiveRelocation(@RequestBody Integer relocationId) {
		this.relocationService.archiveRelocation(relocationId);
	}
	
}
