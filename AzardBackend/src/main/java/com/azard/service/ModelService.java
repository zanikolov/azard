package com.azard.service;

import java.util.List;

import com.azard.model.Model;

public interface ModelService {

	List<Model> getModelsByBrand(Integer brandId);

	List<Model> getAllModels();

	void submitModel(Model model);

	void updateModel(Model model);

	List<Integer> getModelIdsForDailyRevision(Integer lastRevisedModelId, Integer count);

	List<Integer> getModelIdsForFullRevision();

	List<Model> getModelsByIds(List<Integer> modelIds);

}
