import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * HTMLControler
 * 
 * EC.java
 * @author Roc
 * @date 2016-7-3ÏÂÎç2:12:07
 * @Email zp0016@qq.com
 */
public class EC {
	final static String[] ecKeyWord = {"Evolutionary computation",
            "Ant colony optimization",
            "Artificial Bee Colony Algorithm",
            "Artificial immune systems",
            "Artificial life",
            "digital organism",
            "Bees algorithm",
            "Cultural algorithms",
            "Differential evolution",
            "Dual-phase evolution",
            "Evolutionary algorithms",
            "Evolutionary programming",
            "Evolution strategy",
            "Evolution solution",
            "Gene expression programming",
            "Genetic algorithm",
            "Genetic programming",
            "Harmony search",
            "Hill climbing",
            "Learnable Evolution Model",
            "Learning classifier systems",
            "Particle swarm optimization",
            "Self-organization",
            "Simulated annealing",
            "Swarm intelligence"};
	public static Article[] readExcel() throws BiffException, IOException{
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
			if(c[4].getContents().equals("To appear")){
				a.setYear("2016");
			}
			else{
				a.setYear(c[4].getContents());
			}
			//a.setCite(Integer.valueOf(c[5].getContents()));
			a.setKeyword(c[6].getContents());
			a.setAuthor(c[7].getContents());
			a.setInstution(c[8].getContents());
			a.setCountry(c[9].getContents());
			result.add(a);
			//System.out.println(i);
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static int selectMax(int[] para){
		int index = 0;
		for(int i=1;i<para.length;i++){
			if(para[index]<para[i]){
				index = i;
			}
		}
		return index;
	}
	public static Article[] selectEC() throws IOException, BiffException{
		int num = 0;
		boolean flag = false;
		
		int[] ec = new int[ecKeyWord.length];
		Article[] re = readExcel();
		ArrayList<Article> result = new ArrayList<Article>();
		for(int i=0;i<re.length;i++){
			ec = new int[ecKeyWord.length];
//			for(int j=0;j<ec.length;j++){
//				ec[j] = 0;
//			}
			String title = re[i].getTitle().toLowerCase().replace(" ", "");
			String digest = re[i].getDigest().toLowerCase().replace(" ", "");
			String keyword = re[i].getKeyword().toLowerCase().replace(" ", "");
			for(int j=0;j<ecKeyWord.length;j++){
				String eckeyword = ecKeyWord[j].toLowerCase().replace(" ", "");
				ec[j] = title.split(eckeyword,-1).length + digest.split(eckeyword,-1).length + keyword.split(eckeyword,-1).length - 3;
				if(ec[j]>0){
					flag = true;
				}
			}
			
			if(flag){
				re[i].setNumber(ec);
				re[i].setEc(ecKeyWord[selectMax(ec)]);
				re[i].setNum(++num);
				result.add(re[i]);
				flag = false;
			}
		}
		System.out.println("----------");
		int[] result1 = new int[ecKeyWord.length];
		int[] result2 = new int[re.length];
		int[] result3 = new int[ecKeyWord.length];
		for(int i=0;i<result1.length;i++){
			result1[i] = 0;
		}
		for(int i=0;i<result2.length;i++){
			result2[i] = 0;
		}
		for(int i=0;i<result3.length;i++){
			result3[i] = 0;
		}
		int sum1 = 0;
		int sum2 = 0;
		for(int i=0;i<re.length;i++){
			int[] number = re[i].getNumber();
			for(int j=0;j<number.length;j++){
				if(number[j]>0){
					result1[j]++;
					result2[i]++;
					sum1++;
				}
			}
		}
		for(int i=0;i<re.length;i++){
			String eckey = re[i].getEc();
			for(int j=0;j<ecKeyWord.length;j++){
				if(eckey.equals(ecKeyWord[j])){
					result3[j]++;
				}
			}
		}
		for(int i=0;i<result1.length;i++){
			System.out.println(ecKeyWord[i]+":"+result1[i]);
		}
		System.out.println(sum1);
//		for(int i=0;i<result2.length;i++){
//			if(result2[i]>0){
//				System.out.println(re[i].getTitle()+":"+result2[i]);
//				sum2++;
//			}
//		}
//		System.out.println(sum2);
		for(int i=0;i<result3.length;i++){
			System.out.println(ecKeyWord[i]+":"+result3[i]);
		}
//		return re;
		return result.toArray(new Article[0]);
	}
	public static void writeEC(Article[] para) throws IOException, RowsExceededException, WriteException{
		WritableWorkbook book = Workbook.createWorkbook(new File("C:/Users/Roc/Desktop/ec.xls"));
		WritableSheet sheet = book.createSheet("EC", 0);
		for(int i=0;i<ecKeyWord.length;i++){
			sheet.addCell(new Label(i+1, 0, ecKeyWord[i]));
		}
		for(int i=0;i<para.length;i++){
			sheet.addCell(new Label(0, i+1, para[i].getTitle()));
			int[] num = para[i].getNumber();
			for(int j=0;j<num.length;j++){
				sheet.addCell(new Number(j+1, i+1, num[j]));
			}
		}
		book.write();
		book.close();
	}
	public static void writeInfo(Article[] para) throws IOException, RowsExceededException, WriteException{
		WritableWorkbook book = Workbook.createWorkbook(new File("C:/Users/Roc/Desktop/information.xls"));
		WritableSheet sheet = book.createSheet("information", 0);
		for(int i=0;i<para.length;i++){
			sheet.addCell(new Number(0, i, para[i].getNum()));
			sheet.addCell(new Label(1, i, para[i].getTitle()));
			sheet.addCell(new Label(2, i, para[i].getDigest()));
			sheet.addCell(new Label(3, i, para[i].getJournal()));
			sheet.addCell(new Label(4, i, para[i].getYear()));
			sheet.addCell(new Number(5, i, para[i].getCite()));
			sheet.addCell(new Label(6, i, para[i].getKeyword()));
			sheet.addCell(new Label(7, i, para[i].getAuthor()));
			sheet.addCell(new Label(8, i, para[i].getInstution()));
			sheet.addCell(new Label(9, i, para[i].getCountry()));
		}
		book.write();
		book.close();
	}
	public static void main(String[] args) throws BiffException, IOException, WriteException{
		Article[] para = selectEC();
		writeEC(para);
//		writeInfo(para);
	}
}
