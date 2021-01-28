package com.azard.service.fileutil;

import java.util.List;

import com.azard.model.BaseStock;

public interface PDFGeneratorService {

	byte[] generatePdf(List<? extends BaseStock> newStocks);

}
