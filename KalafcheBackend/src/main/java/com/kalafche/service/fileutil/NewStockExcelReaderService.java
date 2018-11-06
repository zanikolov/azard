package com.kalafche.service.fileutil;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

public interface NewStockExcelReaderService {

	List<ExcelItem> parseExcelData(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException;
}
