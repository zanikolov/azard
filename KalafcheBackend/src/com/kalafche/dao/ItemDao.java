package com.kalafche.dao;

import java.sql.SQLException;
import java.util.List;

import com.kalafche.model.Item;

public abstract interface ItemDao {
	public abstract List<Item> getAllItems();

	public abstract Item insertItem(Item item) throws SQLException;

	public abstract void updateItem(Item item, boolean isSuperAdmin);
}
