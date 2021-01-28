package com.azard.service;

import java.math.BigDecimal;
import java.util.List;

import com.azard.model.Product;
import com.azard.model.ProductSpecificPrice;
import com.azard.model.ProductType;

public interface ProductService {

	List<Product> getAllProducts();

	void submitProduct(Product product);

	void updateProduct(Product product);

	Product getProduct(String code);

	List<ProductSpecificPrice> getProductSpecificPrice(Integer productId);

	List<ProductType> getProductTypes();

	void submitProductType(ProductType productType);

	void updateProductType(ProductType productType);

	ProductSpecificPrice getProductSpecificPrice(Integer productId, Integer storeId);

}
