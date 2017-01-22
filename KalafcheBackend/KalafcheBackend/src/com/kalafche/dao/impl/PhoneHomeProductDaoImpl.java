package com.kalafche.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.kalafche.dao.PhoneHomeProductDao;
import com.kalafche.model.PhoneHomeProduct;

@Service
public class PhoneHomeProductDaoImpl extends JdbcDaoSupport implements
		PhoneHomeProductDao {

	private static final String GET_PHONE_HOME_PRODUCTS = "select i.PRODUCT_CODE as code, " +
			"CONCAT(i.PRODUCT_CODE, '-', db.NAME, ' ', dm.NAME) as unique_key, " +
			"CONCAT('Калъфчета и протектори', '>', db.NAME, '>', dm.NAME) as category, " +
			"CONCAT(db.NAME, ' ', dm.NAME, ' ', i.ONLINE_NAME) as name, " +
			"CONCAT(i.DESCRIPTION, ' Продуктов код: ', i.PRODUCT_CODE) as description, " +
			"i.PRICE, " +
			"CONCAT(i.PRODUCT_CODE, '.jpg,', i.PRODUCT_CODE, ' (2).jpg,', i.PRODUCT_CODE, ' (3).jpg,', i.PRODUCT_CODE, ' (4).jpg,', i.PRODUCT_CODE, ' (5).jpg,', i.PRODUCT_CODE, ' (6).jpg') as images, " +
			"CONCAT(i.TAG, ' ', db.NAME, ' ', dm.NAME) as tag " +
			"from stock st " +
			"join item i on st.ITEM_ID = i.ID " +
			"join device_model dm on dm.ID = st.DEVICE_MODEL_ID " +
			"join device_brand db on db.id = dm.DEVICE_BRAND_ID " +
			//"where st.KALAFCHE_STORE_ID = 4 " +
			"where st.KALAFCHE_STORE_ID = 5 "
			+ "and dm.ID = 255 or dm.ID = 256 " +
			//"and i.PRODUCT_CODE not in (123, 125, 141, 142, 143, 152, 182, 223, 231, 232, 233, 236, 237, 238, 239, 241, 243, 245, 247, 402, 432, 503, 506, 521, 522, 524, 525, 526, 533, 539, 542, 550, 641, 811, 812, 813, 814, 831, 871, 872, 873) ";
			"and i.PRODUCT_CODE not in (123, 125, 141, 142, 143, 152, 182, 223, 231, 232, 233, 236, 237, 238, 239, 245, 401, 402, 451, 453, 454, 432, 503, 506, 521, 522, 524, 525, 526, 533, 539, 550, 641, 811, 812, 813, 814, 831, 871, 872, 873) ";

	private static final String GET_BRANDS_FOR_SALE = "select i.PRODUCT_CODE as code, " +
			"CONCAT(i.PRODUCT_CODE, '-', db.NAME, ' ', dm.NAME) as unique_key, " +
			"CONCAT('Калъфчета и протектори', '>', db.NAME, '>', dm.NAME) as category, " +
			"CONCAT(db.NAME, ' ', dm.NAME, ' ', i.ONLINE_NAME) as name, " +
			"CONCAT(i.DESCRIPTION, ' Продуктов код: ', i.PRODUCT_CODE) as description, " +
			"i.PRICE, " +
			"cast(round((i.price / 5) * 4, 2) as decimal(6, 2)) as sale_price, " +
			"CONCAT(i.PRODUCT_CODE, '.jpg,', i.PRODUCT_CODE, ' (2).jpg,', i.PRODUCT_CODE, ' (3).jpg,', i.PRODUCT_CODE, ' (4).jpg,', i.PRODUCT_CODE, ' (5).jpg,', i.PRODUCT_CODE, ' (6).jpg') as images, " +
			"CONCAT(i.TAG, ' ', db.NAME, ' ', dm.NAME) as tag " +
			"from stock st " +
			"join item i on st.ITEM_ID = i.ID " +
			"join device_model dm on dm.ID = st.DEVICE_MODEL_ID " +
			"join device_brand db on db.id = dm.DEVICE_BRAND_ID " +
			"where (st.KALAFCHE_STORE_ID = 4 " +
			"or st.KALAFCHE_STORE_ID = 5) " +
			"and i.PRODUCT_CODE not in (123, 125, 141, 142, 143, 152, 182, 223, 231, 232, 233, 236, 237, 238, 239, 241, 243, 245, 247, 402, 432, 503, 506, 521, 522, 524, 525, 526, 533, 539, 542, 550, 641, 811, 812, 813, 814, 831, 871, 872, 873) " +
			"and i.PRODUCT_CODE not in (123, 125, 141, 142, 143, 152, 182, 223, 231, 232, 233, 236, 237, 238, 239, 245, 401, 402, 451, 453, 454, 432, 503, 506, 521, 522, 524, 525, 526, 533, 539, 550, 641, 811, 812, 813, 814, 831, 871, 872, 873) " +
			"and db.id = 3 " +
			"and st.QUANTITY != 0 ";
	
	private BeanPropertyRowMapper<PhoneHomeProduct> rowMapper;

	@Autowired
	public PhoneHomeProductDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	private BeanPropertyRowMapper<PhoneHomeProduct> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<PhoneHomeProduct>(
					PhoneHomeProduct.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}

		return rowMapper;
	}

	@Override
	public List<PhoneHomeProduct> getPhoneHomeProductsByCode(int code) {
		List<PhoneHomeProduct> products = getJdbcTemplate().query(GET_PHONE_HOME_PRODUCTS, getRowMapper());

		System.out.println(">>>>> " + products.size());
		
		return products;
	}

	@Override
	public List<PhoneHomeProduct> getPhoneHomeProductsForSaleByBrand(int code) {
		List<PhoneHomeProduct> products = getJdbcTemplate().query(GET_BRANDS_FOR_SALE, getRowMapper());

		System.out.println(">>>>> " + products.size());
		
		return products;
	}
	
}
