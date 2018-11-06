package com.kalafche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalafche.dao.ProductDao;
import com.kalafche.exceptions.DuplicationException;
import com.kalafche.model.Product;
import com.kalafche.model.ProductSpecificPrice;

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

}
