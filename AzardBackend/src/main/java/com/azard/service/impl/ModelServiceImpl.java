package com.azard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.dao.DeviceDao;
import com.azard.exceptions.DuplicationException;
import com.azard.model.Model;
import com.azard.service.ModelService;

@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	DeviceDao modelDao;
	
	@Override
	public List<Model> getModelsByBrand(Integer brandId) {
		return modelDao.getModelsByBrand(brandId);
	}

	@Override
	public List<Model> getAllModels() {
		return modelDao.getAllModels();
	}

	@Override
	public void submitModel(Model model) {
		validateDeviceModel(model);
		modelDao.insertModel(model);
	}

	@Override
	public void updateModel(Model model) {
		validateDeviceModel(model);
		modelDao.updateModel(model);
	}

	private void validateDeviceModel(Model model) {
		if (modelDao.checkIfModelExists(model)) {
			throw new DuplicationException("name", "Name duplication.");
		}
	}

	@Override
	public List<Integer> getModelIdsForDailyRevision(Integer lastRevisedDevieModelId, Integer count) {
		return modelDao.getModelIdsForDailyRevision(lastRevisedDevieModelId, count);
	}

	@Override
	public List<Integer> getModelIdsForFullRevision() {
		return modelDao.getModelIdsForFullRevision();
	}

	@Override
	public List<Model> getModelsByIds(List<Integer> deviceModelIds) {
		return modelDao.getModelsByIds(deviceModelIds);
	}
	
}
