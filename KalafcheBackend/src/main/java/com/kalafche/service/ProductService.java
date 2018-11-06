package com.kalafche.service;

import java.math.BigDecimal;
import java.util.List;

import com.kalafche.model.Product;
import com.kalafche.model.ProductSpecificPrice;

public interface ProductService {

	List<Product> getAllProducts();

	void submitProduct(Product product);

	void updateProduct(Product product);

	Product getProduct(String code);

	List<ProductSpecificPrice> getProductSpecificPrice(Integer productId);

}
