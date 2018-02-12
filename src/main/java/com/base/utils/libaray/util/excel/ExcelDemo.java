package com.base.utils.libaray.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelDemo {

	public static void main(String[] args) throws Exception{
		testExport();
//		testExport2();
	}
	
	public static void testImport() throws  Exception{
		File file = new File("C:\\Users\\Administrator.USER-20150320GQ\\Desktop\\test.xlsx");
		ImportExcel impExcel = new ImportExcel(file, 1);
		int lastColumn = impExcel.getLastCellNum();
		Row headRow = impExcel.getRow(0);
		headRow.createCell(lastColumn+1).setCellValue("SKU");
		headRow.createCell(lastColumn+2).setCellValue("HSN");
		headRow.createCell(lastColumn+3).setCellValue("GST Rate");
		File outFile = new File("H:\\test.xls");
		if(!outFile.exists()){
			outFile.createNewFile();
		}
		OutputStream os = new FileOutputStream(outFile);
		impExcel.getWorkbook().write(os);
	}
	
	public static void testExport(){
//		List<String> headerList = new ArrayList<>();
//		headerList.add("创建时间");
//		headerList.add("重量");
//		headerList.add("件数");
//		ExportExcel excel = new ExportExcel("", headerList);
//		
//		Row row = excel.addRow();
//		excel.addCell(row, 0, new Date());
//		excel.addCell(row, 1, new Double(11.11));
//		excel.addCell(row, 2, new Integer(11));
//		
//		try {
//			excel.writeFile("D:\\4.xlsx");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public static void testExport2() throws Exception{
	
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		CellStyle cesssTyle = wb.createCellStyle();
		DataFormat df = wb.createDataFormat();
		cesssTyle.setDataFormat(df.getFormat("yyyy-m-d"));
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		Cell cell1 = row.createCell(0);
		Cell cell2 = row.createCell(1);
		Cell cell3 = row.createCell(2);
		
		cell1.setCellStyle(cesssTyle);
		cell1.setCellValue(new Date());
		cell2.setCellValue(new Double(11.11));
		cell3.setCellValue(new Integer(4444));
		OutputStream os = new FileOutputStream(new File("D:\\3.xlsx"));
		wb.write(os);
		
		
	}
}
