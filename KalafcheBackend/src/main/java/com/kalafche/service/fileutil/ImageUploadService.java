package com.kalafche.service.fileutil;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

	void uploadImage(Integer wasteId, MultipartFile wasteImage);

	void uploadWasteImage(Integer wasteId, MultipartFile wasteImage) throws IllegalStateException, IOException, GeneralSecurityException;

	public String getWasteImageId(Integer wasteId) throws IOException, GeneralSecurityException;
}
