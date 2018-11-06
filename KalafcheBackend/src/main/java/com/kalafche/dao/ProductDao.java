package com.kalafche.dao;

import java.util.List;

import com.kalafche.model.Product;
import com.kalafche.model.ProductSpecificPrice;

public abstract interface ProductDao {
	public abstract List<Product> getAllProducts();

	public abstract void insertProduct(Product product);

	public abstract void updateProduct(Product product);

	public abstract Product getProduct(String code);

	public abstract boolean checkIfProductCodeExists(Product product);

	public abstract boolean checkIfProductNameExists(Product product);

	public abstract List<ProductSpecificPrice> getProductSpecificPrice(Integer productId);

	public abstract void updateProductSpecificPrice(ProductSpecificPrice specificPrice);

	public abstract void deleteProductSpecificPrices(Integer productId);
	
}
