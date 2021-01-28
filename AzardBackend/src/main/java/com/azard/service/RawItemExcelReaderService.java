package com.azard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.azard.model.RawItem;

public interface RawItemExcelReaderService {

	List<RawItem> parseExcelData(MultipartFile rawItemFile);

}
