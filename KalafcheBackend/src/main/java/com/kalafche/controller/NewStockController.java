package com.kalafche.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kalafche.model.NewStock;
import com.kalafche.service.NewStockService;
import com.kalafche.service.fileutil.ExcelItem;

@CrossOrigin
@RestController
@RequestMapping({ "/newStock" })
public class NewStockController {
	
	@Autowired
	private NewStockService newStockService;

	@GetMapping
	public List<NewStock> getAllNewStock() {
		return newStockService.getAllNewStocks();
	}
	
	@PutMapping
	public void submitNewStock(@RequestBody NewStock newStock) {
		newStockService.submitNewStock(newStock.getProductId(), newStock.getDeviceModelId(), newStock.getQuantity());
	}
	
	@DeleteMapping
	public void deleteNewStock(@RequestParam(value = "newStockId") Integer newStockId) {		
		newStockService.deleteNewStock(newStockId);
	}
	
	@PostMapping("/import")
	public void submitNewStockFromFile( @RequestParam("newStockFile") MultipartFile newStockFile) throws IOException, EncryptedDocumentException, InvalidFormatException {
	      newStockService.submitNewStockFromFile(newStockFile);
	}
	
	@PostMapping("/validate")
	public List<ExcelItem> validateNewStockExcel( @RequestParam("newStockFile") MultipartFile newStockFile) throws IOException, EncryptedDocumentException, InvalidFormatException {
	      return newStockService.validateNewStockFile(newStockFile);
	}
	
	@PostMapping("/approve")
	public void approveNewStock(@RequestBody NewStock newStock) {
		newStockService.approveNewStock(newStock);
	}
	
	@PostMapping("/approveAll")
	public void approveNewStock(@RequestBody List<NewStock> newStocks) {
		newStockService.approveNewStock(newStocks);
	}
	
	
	@GetMapping("/printAll")
	public void printNewStockStickers() {
		newStockService.printNewStockStickers();
	}
	
}
