package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azard.dao.ProductDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Product;
import com.azard.model.ProductSpecificPrice;
import com.azard.model.ProductType;
import com.azard.service.ProductService;
import com.azard.service.StockService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;
	
	@Autowired
	StockService stockService;
	
	@Override
	public List<Product> getAllProducts() {
		return productDao.getAllProducts();
	}

	@Override
	@Transactional
	public void submitProduct(Product product) {
		validateProductCode(product);
		validateProductName(product);
		productDao.insertProduct(product);
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		validateProductCode(product);
		validateProductName(product);
		productDao.updateProduct(product);
		productDao.deleteProductSpecificPrices(product.getId());
		product.getSpecificPrices().forEach(specificPrice -> productDao.updateProductSpecificPrice(specificPrice));
	}

	@Override
	public Product getProduct(String code) {
		return productDao.getProduct(code);
	}
	
	@Override
	public List<ProductSpecificPrice> getProductSpecificPrice(Integer productId) {
		return productDao.getProductSpecificPrice(productId);
	}
	
	private void validateProductCode(Product product) {
		if (productDao.checkIfProductCodeExists(product)) {
			throw new DuplicationException("code", "Code duplication.");
		}
	}
	
	private void validateProductName(Product product) {
		if (productDao.checkIfProductNameExists(product)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}

	@Override
	public List<ProductType> getProductTypes() {
		return productDao.getAllProductTypes();
	}

	@Override
	@Transactional
	public void submitProductType(ProductType productType) {
		validateProductTypeName(productType);
		productDao.insertProductType(productType);
	}

	@Override
	@Transactional
	public void updateProductType(ProductType productType) {
		validateProductTypeName(productType);
		productDao.updateProductType(productType);
	}
	
	private void validateProductTypeName(ProductType productType) {
		if (productDao.checkIfProductTypeNameExists(productType)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}
	
	@Override
	public ProductSpecificPrice getProductSpecificPrice(Integer productId, Integer storeId) {
		return productDao.getProductSpecificPrice(productId, storeId);
	}

}
