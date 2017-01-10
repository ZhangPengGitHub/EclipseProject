import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * HTMLControler
 * 
 * ExcelTest.java
 * @author Roc
 * @date 2016-7-4ÏÂÎç3:55:28
 * @Email zp0016@qq.com
 */
public class ExcelTest {
	public static void main(String[] args) throws IOException, WriteException, BiffException{
		{
		WritableWorkbook book = Workbook.createWorkbook(new File("test.xls"));
		WritableSheet sheet = book.createSheet("test1", 0);
		sheet.addCell(new Label(0, 0, "r1c1"));
		sheet.addCell(new Label(1, 0, "r1c2"));
		book.write();
		book.close();
		}
		{
		Workbook wb = Workbook.getWorkbook(new File("test.xls")); 
		WritableWorkbook book = Workbook.createWorkbook(new File("test.xls"),wb); 
		WritableSheet sheet=book.createSheet("test2",1); 
		sheet.addCell(new Label(0,0,"r1c1")); 
		book.write(); 
		book.close();
		System.out.println(wb.getVersion());
		}
	}
}
