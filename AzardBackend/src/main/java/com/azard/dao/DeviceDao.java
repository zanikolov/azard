package com.azard.dao;

import java.util.List;

import com.azard.model.Model;

public interface DeviceDao {
	public abstract List<Model> getModelsByBrand(int brandId);

	public abstract void insertModel(Model model);

	public abstract List<Model> getAllModels();

	public abstract void updateModel(Model model);
	
	public abstract Boolean checkIfModelExists(Model model);
	
	public abstract Model selectModel(Integer modelId);

	public abstract List<Integer> getModelIdsForDailyRevision(Integer start, Integer count);

	public abstract List<Integer> getModelIdsForFullRevision();

	public abstract List<Model> getModelsByIds(List<Integer> modelIds);
	
}
