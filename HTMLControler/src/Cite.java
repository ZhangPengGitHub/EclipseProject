import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * HTMLControler
 * 
 * Cite.java
 * @author Roc
 * @date 2016-7-6ÏÂÎç8:29:32
 * @Email zp0016@qq.com
 */
public class Cite {
	public static Article[] readExcel() throws BiffException, IOException{
		Article[] temp = EC.selectEC();
		ArrayList<Article> result = new ArrayList<Article>();
		String path = "C:/Users/Roc/Desktop/information.xls";
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		Workbook w = Workbook.getWorkbook(fis);
		Sheet s = w.getSheet(0);
		System.out.println(s.getRows());
		for(int i=0;i<s.getRows();i++){
			Cell[] c = s.getRow(i);
			Article a = new Article();
			a.setNum(Integer.valueOf(c[0].getContents()));
			a.setTitle(c[1].getContents());
			a.setDigest(c[2].getContents());
			a.setJournal(c[3].getContents());
			a.setYear(c[4].getContents());
			//a.setCite(Integer.valueOf(c[5].getContents()));
			a.setKeyword(c[6].getContents());
			a.setAuthor(c[7].getContents());
			a.setInstution(c[8].getContents());
			a.setCountry(temp[i].getCountry());
			result.add(a);
			//System.out.println(i);
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static Article[] readCite() throws BiffException, IOException{
		Article[] result = readExcel();
		String path = "C:/Users/Roc/Desktop/cite.xls";
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		Workbook w = Workbook.getWorkbook(fis);
		Sheet s = w.getSheet(0);
		System.out.println(s.getRows());
		for(int i=0;i<s.getRows();i++){
			Cell[] c = s.getRow(i);
			for(int j=0;j<result.length;j++){
				if(c[1].getContents().equals(result[j].getTitle())){
					result[j].setCite(Integer.valueOf(c[2].getContents()));
				}
			}
		}
		return result;
	}
	public static void main(String[] args) throws RowsExceededException, WriteException, BiffException, IOException{
		EC.writeInfo(readCite());
	}
}
