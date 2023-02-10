package com.azard.service.fileutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azard.model.SaleItem;
import com.azard.model.SaleItemExcelReportRequest;
import com.azard.service.DateService;

@Service
public class SaleItemExcelReportServiceImpl implements SaleItemExcelReportService {

	@Autowired
	private DateService dateService;

	@Override
	public byte[] generateExcel(SaleItemExcelReportRequest saleItemExcelReportRequest) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(String.format("Продажби",
				dateService.convertMillisToDateTimeString(saleItemExcelReportRequest.getStartDate(), false),
				dateService.convertMillisToDateTimeString(saleItemExcelReportRequest.getEndDate(), false)));

		int rowNum = 0;		
		for (SaleItem saleItem : saleItemExcelReportRequest.getSaleItems()) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;

			Cell deviceCell = row.createCell(colNum++);
			deviceCell.setCellValue((String) saleItem.getModelName());

			Cell productCodeCell = row.createCell(colNum++);
			productCodeCell.setCellValue((String) saleItem.getLeatherCode());

			Cell productNameCell = row.createCell(colNum++);
			productNameCell.setCellValue((String) saleItem.getLeatherName());

			Cell storeNameCell = row.createCell(colNum++);
			storeNameCell.setCellValue((String) saleItem.getStoreName());

			Cell employeeNameCell = row.createCell(colNum++);
			employeeNameCell.setCellValue((String) saleItem.getEmployeeName());

			Cell saleTimestampCell = row.createCell(colNum++);
			saleTimestampCell
					.setCellValue((String) dateService.convertMillisToDateTimeString(saleItem.getSaleTimestamp(), true));

			Cell salePriceCell = row.createCell(colNum++);
			salePriceCell.setCellValue((String) saleItem.getSalePrice().toPlainString());
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
			workbook.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baos.toByteArray();
		
	}

}
