package com.kalafche.service.fileutil;

import java.util.List;

import com.kalafche.model.NewStock;

public interface PDFGeneratorService {

	void generatePdf(List<NewStock> newStocks);

}
