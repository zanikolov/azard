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

import com.kalafche.dao.ItemDao;
import com.kalafche.model.Item;

@Service
public class ItemDaoImpl extends JdbcDaoSupport implements ItemDao {
	
	private static final String GET_ALL_ITEMS_QUERY = "select * from item order by product_code desc";
	private static final String INSERT_ITEM = "insert into item(name, online_name, product_code, description, price, purchase_price) values (?, ?, ?, ?, ?, ? * 1.956)";
	private static final String UPDATE_ITEM = "update item set name = ?, online_name = ?, description = ?, price = ? where id = ?";
	private static final String UPDATE_ITEM_SUPERADMIN = "update item set " +
			"name = ?, " +
			"description = ?, " +
			"price = ?, " +
			"purchase_price = CASE " +
			"   WHEN PURCHASE_PRICE != ? THEN ? * 1.956 " +
			"   ELSE PURCHASE_PRICE " +
			"END " +
			"where id = ? ; ";

	private BeanPropertyRowMapper<Item> rowMapper;
	
	@Autowired
	public ItemDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}
	
	private BeanPropertyRowMapper<Item> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new BeanPropertyRowMapper<Item>(Item.class);
			rowMapper.setPrimitivesDefaultedForNullValue(true);
		}
		
		return rowMapper;
	}
	
	@Override
	public List<Item> getAllItems() {
		List<Item> items = getJdbcTemplate().query(GET_ALL_ITEMS_QUERY, getRowMapper());

		return items;
	}

	@Override
	public Item insertItem(Item item) throws SQLException {
		//getJdbcTemplate().update(INSERT_ITEM, item.getName(), item.getProductCode(), item.getDescription(), item.getPrice());	
		
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement(
						INSERT_ITEM, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getOnlineName());
			statement.setString(3, item.getProductCode());
			statement.setString(4, item.getDescription());
			statement.setFloat(5, item.getPrice());
			statement.setFloat(6, item.getPurchasePrice());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException(
						"Creating item failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					item.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException(
							"Creating item failed, no ID obtained.");
				}
			}
		}
		
		return item;
	}
	
	@Override
	public void updateItem(Item item, boolean isSuperAdmin) {
		if (isSuperAdmin) {
			//getJdbcTemplate().update(UPDATE_ITEM_SUPERADMIN, item.getName(), item.getDescription(), item.getPrice(), item.getPurchasePrice(), item.getPurchasePrice(), item.getId());
			getJdbcTemplate().update(UPDATE_ITEM, item.getName(), item.getOnlineName(), item.getDescription(), item.getPrice(), item.getId());
		} else {
			getJdbcTemplate().update(UPDATE_ITEM, item.getName(), item.getOnlineName(), item.getDescription(), item.getPrice(), item.getId());
		}
	}
}
