package com.kalafche.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.BaseController;
import com.kalafche.dao.ProductDao;
import com.kalafche.model.Product;

@CrossOrigin
@RestController
@RequestMapping({ "/product" })
public class ProductController extends BaseController {
	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = { "/getAllProducts" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public List<Product> getAllProducts() {
		List<Product> products = this.productDao.getAllProducts();

		return products;
	}

    //public @ResponseBody void storeAd1(MultipartHttpServletRequest request) {
	
	@RequestMapping(value = { "/insertProduct" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public Product insertProduct(@RequestBody Product product) {
		Product insertedProduct = null;
		
		try {
			insertedProduct = this.productDao.insertProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insertedProduct;
	}
	
	@RequestMapping(value = { "/updateProduct" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void updateProduct(@RequestBody Product product) {

		this.productDao.updateProduct(product, userHasRole("ROLE_SUPERADMIN"));
	}
}