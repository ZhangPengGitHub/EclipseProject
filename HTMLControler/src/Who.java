import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * HTMLControler
 * 
 * Who.java
 * @author Roc
 * @date 2016-7-4ÏÂÎç2:05:34
 * @Email zp0016@qq.com
 */
public class Who {
	private String name = "";
	private String institutionString = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitutionString() {
		return institutionString;
	}
	public void setInstitutionString(String institutionString) {
		this.institutionString = institutionString;
	}
	public static void solution() throws Exception{
		int num = 0;
		String[] url  = new String[26];
		String index = "abcdefghijklmnopqrstuvwxyz";
		ArrayList<Who> result = new ArrayList<Who>();
		for(int i=0;i<index.length();i++){
			url[i] = "http://crestweb.cs.ucl.ac.uk/resources/sbse_repository/whoswho/index.php?alph="+index.charAt(i);
			JsoupTest.getWho(result, url[i]);
		}
		
		WritableWorkbook book = Workbook.createWorkbook(new File("C:/Users/Roc/Desktop/who.xls"));
		WritableSheet sheet = book.createSheet("who", 0);
		for(Who who : result){
			System.out.println(num+"----"+who.getName()+"----"+who.getInstitutionString());
			sheet.addCell(new Label(0, num, who.getName()));
			sheet.addCell(new Label(1, num, who.getInstitutionString()));
			num++;
		}
		book.write();
		book.close();
	}
	public static void main(String[] args) throws Exception{
		solution();
	}
}
