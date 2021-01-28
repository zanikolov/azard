package com.azard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azard.BaseController;
import com.azard.model.Product;
import com.azard.model.ProductSpecificPrice;
import com.azard.model.ProductType;
import com.azard.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping({ "/product" })
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> getAllProducts() {
		List<Product> products = this.productService.getAllProducts();

		return products;
	}
	
	@GetMapping("/{code}")
	public Product getProductByCode(@PathVariable(value = "code") String code) {
		return productService.getProduct(code);
	}
	
	@PutMapping
	public void insertProduct(@RequestBody Product product) {
		productService.submitProduct(product);
	}
	
	@PostMapping
	public void updateProduct(@RequestBody Product product) {
		this.productService.updateProduct(product);
	}

	@GetMapping("/specificPrice/{productId}")
	public List<ProductSpecificPrice> getProductSpecificPrice(@PathVariable(value = "productId", required = false) Integer productId) {
		return productService.getProductSpecificPrice(productId);
	}

	@GetMapping("/type")
	public List<ProductType> getProductTypes(@PathVariable(value = "productId", required = false) Integer productId) {
		return productService.getProductTypes();
	}
	
	@PutMapping("/type")
	public void insertProductType(@RequestBody ProductType productType) {
		productService.submitProductType(productType);
	}
	
	@PostMapping("/type")
	public void updateProductType(@RequestBody ProductType productType) {
		this.productService.updateProductType(productType);
	}
	
}