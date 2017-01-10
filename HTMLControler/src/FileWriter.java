
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class FileWriter {
	public static void solution(Article[] a,String outpath) throws UnsupportedEncodingException, IOException{
		
		File orf = new File(outpath);
		FileOutputStream ros = new FileOutputStream(orf);
		
		for(int i=0;i<a.length;i++){
			System.out.println(a[i].getNum());
			System.out.println(a[i].getTitle());
			System.out.println(a[i].getDigest());
			System.out.println(a[i].getJournal());
			System.out.println(a[i].getYear());
			System.out.println(a[i].getCite());
			System.out.println(a[i].getAuthor());
			System.out.println(a[i].getKeyword());
			System.out.println(a[i].getInstution());
			
			ros.write(String.valueOf(a[i].getNum()).getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getTitle().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getDigest().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getJournal().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getYear().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(String.valueOf(a[i].getCite()).getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getKeyword().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getAuthor().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
			ros.write(a[i].getInstution().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
		}
	}
}
