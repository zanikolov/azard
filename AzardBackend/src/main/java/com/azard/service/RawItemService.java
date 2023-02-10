package com.azard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.azard.model.RawItem;

public interface RawItemService {

	List<RawItem> submitRawItemsFromFile(MultipartFile rawItemFile);

}
