package com.kalafche.dao;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Product;

public abstract interface ProductDao {
	public abstract List<Product> getAllProducts();

	public abstract Product insertProduct(Product product) throws SQLException;

	public abstract void updateProduct(Product product, boolean isSuperAdmin);
}
