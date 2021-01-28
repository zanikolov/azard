package com.azard.service.fileutil;

import com.azard.model.SaleItemExcelReportRequest;

public interface SaleItemExcelReportService {

	byte[] generateExcel(SaleItemExcelReportRequest saleItemExcelReportRequest);
}
