package com.kalafche.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.ProductDao;
import com.kalafche.model.Product;

@Service
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {
	
	private static final String GET_ALL_PRODUCTS_QUERY = "select * from product order by code desc";
	private static final String INSERT_PRODUCT = "insert into product(name, online_name, code, description, price, purchase_price) values (?, ?, ?, ?, ?, ? * 1.956)";
	private static final String UPDATE_PRODUCT = "update product set name = ?, online_name = ?, description = ?, price = ? where id = ?";
//	private static final String UPDATE_PRODUCT_SUPERADMIN = "update product set " +
//			"name = ?, " +
//			"description = ?, " +
//			"price = ?, " +
//			"purchase_price = CASE " +
//			"   WHEN PURCHASE_PRICE != ? THEN ? * 1.956 " +
//			"   ELSE PURCHASE_PRICE " +
//			"END " +
//			"where id = ? ; ";

	private BeanPropertyRowMapper<Product> rowMapper;
	
	@Autowired
	public ProductDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Product> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Product>(Product.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return getJdbcTemplate().query(GET_ALL_PRODUCTS_QUERY, getRowMapper());
	}

	@Override
	public Product insertProduct(Product product) throws SQLException {
		//getJdbcTemplate().update(INSERT_PRODUCT, product.getName(), product.getCode(), product.getDescription(), product.getPrice());	
		
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(
						INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, product.getName());
			statement.setString(2, product.getOnlineName());
			statement.setString(3, product.getCode());
			statement.setString(4, product.getDescription());
			statement.setFloat(5, product.getPrice());
			statement.setFloat(6, product.getPurchasePrice());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException(
						"Creating product failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException(
							"Creating product failed, no ID obtained.");
				}
			}
		}
		
		return product;
	}
	
	@Override
	public void updateProduct(Product product, boolean isSuperAdmin) {
		if (isSuperAdmin) {
			//getJdbcTemplate().update(UPDATE_PRODUCT_SUPERADMIN, product.getName(), product.getDescription(), product.getPrice(), product.getPurchasePrice(), product.getPurchasePrice(), product.getId());
			getJdbcTemplate().update(UPDATE_PRODUCT, product.getName(), product.getOnlineName(), product.getDescription(), product.getPrice(), product.getId());
		} else {
			getJdbcTemplate().update(UPDATE_PRODUCT, product.getName(), product.getOnlineName(), product.getDescription(), product.getPrice(), product.getId());
		}
	}
}
