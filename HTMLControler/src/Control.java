import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * HTMLControler
 * 
 * Control.java
 * @author Roc
 * @date 2016-7-14上午9:03:39
 * @Email zp0016@qq.com
 */
public class Control {
	public static void main(String[] args) throws IOException{
		String path = "C:/Users/Roc/Desktop/小论文/实验步骤/步骤三/Others.txt";
		FileInputStream fis = new FileInputStream(new File(path));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String tempStr = "";
		int num = 0;
		int i=0;
		Article article = new Article();
		ArrayList<Article> al = new ArrayList<Article>();
		while((tempStr = br.readLine())!=null){
			i++;
			switch(i){
			case 1:article.setNum(++num);break;
			case 2:article.setTitle(tempStr);break;
			case 3:article.setDigest(tempStr);break;
			case 4:article.setJournal(tempStr);break;
			case 5:article.setYear(tempStr);break;
			case 6:article.setCite(Integer.valueOf(tempStr));break;
			case 7:article.setKeyword(tempStr);break;
			case 8:article.setAuthor(tempStr);break;
			case 9:article.setInstution(tempStr);break;
			case 10:article.setUrl(tempStr);al.add(article);article = new Article();i=0;break;
			default:break;
			}
		}
		FileOutputStream ros = new FileOutputStream(new File(path));
		for(int j=0;j<al.size();j++){
			article = al.get(j);
			ros.write(String.valueOf(article.getNum()).getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getTitle().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getDigest().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getJournal().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getYear().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(String.valueOf(article.getCite()).getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getKeyword().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getAuthor().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getInstution().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(article.getUrl().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
		}
	}
}
