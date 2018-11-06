package com.kalafche.service.fileutil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NewStockExcelReaderServiceImpl implements NewStockExcelReaderService {

	@Override
	public List<ExcelItem> parseExcelData(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<ExcelItem> items = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        
        worksheet.forEach(row -> {
        	ExcelItem item=new ExcelItem();

        	row.forEach(cell -> {
        		printCellValue(cell);
        	});
        	System.out.println();
        	
            item.setBarcode(new BigDecimal(row.getCell(0).getNumericCellValue()).toPlainString());
            item.setName(row.getCell(2).getStringCellValue());
            item.setQuantity((int)row.getCell(8).getNumericCellValue());
            items.add(item);
        	
        });
		
		return items;
	}
	
	private static void printCellValue(Cell cell) {
	    switch (cell.getCellTypeEnum()) {
	        case STRING:
	            System.out.print(cell.getColumnIndex() + " " + cell.getStringCellValue() + "    ");
	            break;
	        case NUMERIC:
                System.out.print(cell.getColumnIndex() + " " + cell.getNumericCellValue() + "    ");
	            break;
	        case BLANK:
	            System.out.print(cell.getColumnIndex() + " " + "^^    ");
	            break;
	        default:
	            System.out.print(cell.getColumnIndex() + " " + "<>    ");
	    }
	}

}
