package com.azard.dao;

import java.sql.SQLException;
import java.util.List;

import com.azard.model.NewStock;

public interface NewStockDao {

	List<NewStock> getAllNewStocks();

	void deleteNewStock(Integer newStockId);

	void insertOrUpdateQuantityOfNewStock(Integer leatherId, Integer modelId, Integer quantity);

	void updateQuantityOfNewStock(Integer leatherId, Integer modelId, Integer quantity);

	void insertNewStockFromFile(String barcode, Integer quantity, Integer importId, Integer storeId);

	Integer insertNewStockImport(Long importTimestamp, Integer employeeId, String fileName) throws SQLException;

	void insertNewStock(Integer leatherId, Integer modelId, Integer quantity, Integer storeId);

	List<NewStock> getNewStockByStoreId(Integer storeId);

}
