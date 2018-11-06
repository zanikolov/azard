package com.kalafche.service.fileutil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kalafche.model.NewStock;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
	
	public static final String FONT = "fonts/FreeSans.ttf";
	//public static final String FONT = "C:\\Zahari\\Projects\\FreeSans.ttf";
	
	@Override
	public void generatePdf(List<NewStock> newStocks) {
		Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 7);
		
		Rectangle rect = new Rectangle(PageSize.A4);		
		System.out.println(">>>>>>>  " + rect.getWidth());
		System.out.println(">>>>>>>  " + rect.getHeight());
		
		Document document = new Document(rect, 0, 0, 0, 0);
	    try
	    {
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Zahari\\Projects\\KalafcheTest.pdf"));
	        document.open();
	 
	        PdfPTable table = configureTable();

	        newStocks.forEach(stock -> {
	        	
	        	//for (int i = 0; i < stock.getQuantity(); i++) {
		        	PdfPTable stockTable = new PdfPTable(2);
	
		        	
		        	//Empty cells
		        	stockTable.addCell(configureCell("      ", font, 2));
		        	stockTable.addCell(configureCell("      ", font, 2));
		        	
		        	//Product Type
		        	//stockTable.addCell(configureCell("Артикул", font));
		        	stockTable.addCell(configureCell(stock.getProductTypeName(), font, 2));
		        	
		        	//Product
		        	//stockTable.addCell(configureCell("Модел", font, null));
		        	stockTable.addCell(configureCell(stock.getProductCode() + " " + stock.getDeviceModelName(), font, 2));
		        	
		        	//Fabric
		        	//stockTable.addCell(configureCell("Състав", font, null));
		        	stockTable.addCell(configureCell(stock.getProductFabric(), font, 2));
		        	
		        	//Manufacturer
		        	stockTable.addCell(configureCell("Произв.", font, null));
		        	stockTable.addCell(configureCell("Идеа шоу", font, null));
		        	
		        	//Distributer
		        	stockTable.addCell(configureCell("Дистриб.", font, null));
		        	stockTable.addCell(configureCell("Азаника ЕООД", font, null));
		        	
		        	//Origin
		        	stockTable.addCell(configureCell("Произход", font, null));
		        	stockTable.addCell(configureCell("Полша", font, null));
		        	
		        	//Price
		        	stockTable.addCell(configureCell("Цена", font, null));
		        	stockTable.addCell(configureCell(stock.getProductPrice() + "лв", font, null));
		        	
		        	try {
		        		stockTable.addCell(createBarcode(writer, stock.getBarcode()));
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	PdfPCell cell = new PdfPCell();
		        	cell.setPadding(0);
		        	cell.addElement(stockTable);
		        	cell.setFixedHeight(PageSize.A4.getHeight() / 7);
		        	cell.setRotation(90);
		        	cell.setBorder(Rectangle.NO_BORDER);
		        	
		        	table.addCell(cell);
	        	//}
	        });
	        
	 
	        document.add(table);
	 
	        document.close();
	        writer.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }		
	}

	private PdfPCell configureCell(String text, Font font, Integer colspan) {
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
//		cell.setUseAscender(true);
//		cell.setUseDescender(true);
		
		cell.setNoWrap(true);
		
		cell.setFixedHeight(12f);
		
		if (colspan != null) {
			cell.setColspan(colspan);
		}
		
		return cell;
	}

	private PdfPTable configureTable() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		float[] columnWidths = {2f, 2f, 2f};
		table.setWidths(columnWidths);
		return table;
	}
	
    public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {
        BarcodeEAN barcode = new BarcodeEAN();
        barcode.setCodeType(Barcode.EAN13);
        barcode.setCode(code);
        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK), true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        return cell;
    }
	
}
