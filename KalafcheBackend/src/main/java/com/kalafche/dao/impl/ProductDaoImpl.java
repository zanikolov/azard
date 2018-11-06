package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.ProductDao;
import com.kalafche.model.Product;
import com.kalafche.model.ProductSpecificPrice;

@Service
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {
	
	private static final String GET_ALL_PRODUCTS_QUERY = "select id, name, code, price, fabric from product order by code desc";
	private static final String INSERT_PRODUCT = "insert into product(name, code, description, price, fabric) values (?, ?, ?, ?, ?)";
	private static final String UPDATE_PRODUCT = "update product set name = ?, description = ?, fabric = ?, price = ? where id = ?";
	private static final String GET_PRODUCT_BY_CODE_QUERY = "select * from product where code = ?";
	private static final String CHECK_IF_PRODUCT_NAME_EXISTS = "select count(*) from product where name = ?";
	private static final String CHECK_IF_PRODUCT_CODE_EXISTS = "select count(*) from product where code = ?";
	private static final String ID_CLAUSE = " and id <> ?";
	private static final String GET_PRODUCT_SPECIFIC_PRICE = "select " +
			"? as product_id, " +
			"psp.specific_price as price, " +
			"concat(ks.city, ', ', ks.name) as store_name, " +
			"ks.id as store_id " +
			"from kalafche_store ks " +
			"left join product_specific_price psp on psp.store_id = ks.id " +
			"and psp.product_id = ? " +
			"where ks.is_store is true ";
	private static final String UPSERT_PRODUCT_SPECIFIC_PRICE = "insert into product_specific_price "
			+ "(product_id, store_id, specific_price) values "
			+ "(?, ?, ?) "
			+ "on duplicate key update specific_price = ?";
	
	private static final String DELETE_PRODUCT_SPECIFIC_PRICE = "delete from product_specific_price "
			+ " where product_id = ? ";

	private BeanPropertyRowMapper<Product> rowMapper;
	private BeanPropertyRowMapper<ProductSpecificPrice> specificPriceRowMapper;
	
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

	private BeanPropertyRowMapper<ProductSpecificPrice> getSpecificPriceRowMapper() {
		if (specificPriceRowMapper == null) {
			specificPriceRowMapper = new BeanPropertyRowMapper<ProductSpecificPrice>(ProductSpecificPrice.class);
			specificPriceRowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return specificPriceRowMapper;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return getJdbcTemplate().query(GET_ALL_PRODUCTS_QUERY, getRowMapper());
	}

	@Override
	public void insertProduct(Product product) {
		getJdbcTemplate().update(INSERT_PRODUCT, product.getName(), product.getCode(), product.getDescription(), product.getPrice(), product.getFabric());	
	}
	
	@Override
	public void updateProduct(Product product) {
		getJdbcTemplate().update(UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getFabric(), product.getPrice(), product.getId());
	}

	@Override
	public Product getProduct(String code) {
		List<Product> products = getJdbcTemplate().query(GET_PRODUCT_BY_CODE_QUERY, getRowMapper(), code);
		return products.isEmpty() ? null : products.get(0);
	}

	@Override
	public boolean checkIfProductCodeExists(Product product) {
		Integer exists = null;
		if (product.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_PRODUCT_CODE_EXISTS, Integer.class, product.getCode());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_PRODUCT_CODE_EXISTS + ID_CLAUSE, Integer.class, product.getCode(), product.getId());
		}
			
		return exists != null && exists > 0 ;
	}
	
	@Override
	public boolean checkIfProductNameExists(Product product) {
		Integer exists = null;
		if (product.getId() == null) {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_PRODUCT_NAME_EXISTS, Integer.class, product.getName());
		} else {
			exists = getJdbcTemplate().queryForObject(CHECK_IF_PRODUCT_NAME_EXISTS + ID_CLAUSE, Integer.class, product.getName(), product.getId());
		}
		
		return exists != null && exists > 0 ;
	}

	@Override
	public List<ProductSpecificPrice> getProductSpecificPrice(Integer productId) {
		return getJdbcTemplate().query(GET_PRODUCT_SPECIFIC_PRICE, getSpecificPriceRowMapper(), productId, productId);
	}

	@Override
	public void updateProductSpecificPrice(ProductSpecificPrice specificPrice) {
		getJdbcTemplate().update(UPSERT_PRODUCT_SPECIFIC_PRICE, 
				specificPrice.getProductId(), specificPrice.getStoreId(), specificPrice.getPrice(), specificPrice.getPrice());
	}
	
	@Override
	public void deleteProductSpecificPrices(Integer productId) {
		getJdbcTemplate().update(DELETE_PRODUCT_SPECIFIC_PRICE, productId);
	}
}
