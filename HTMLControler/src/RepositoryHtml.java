import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RepositoryHtml {
	public static ArrayList solution() throws IOException{
		ArrayList<Article> result = new ArrayList<Article>();
		
		String inpath = "C:/Users/Roc/Desktop/repository.html";
		File irf = new File(inpath);
		FileInputStream ris = new FileInputStream(irf);
		BufferedReader rbr = new BufferedReader(new InputStreamReader(ris));
		
		String sl;
		Article a = new Article();
		boolean bflag = false;
		int iflag = 0;
		int trflag = 0;
		boolean tflag = true;
		boolean jflag = true;
		boolean aflag = true;
		int num = 0;
		
		while((sl=rbr.readLine())!=null){
			if(sl.contains("class=\"entry\">")){
				bflag = true;
				num++;
				a.setNum(num);
			}
			if(bflag && sl.contains("<td")){
				iflag++;
			}
			if(bflag && sl.contains("<tr")){
				trflag++;
			}
			if(bflag && trflag >= 2){
				if(sl.contains("<td colspan=\"7\"><b>Abstract</b>:")){
					a.setDigest(sl.replace("<td colspan=\"7\"><b>Abstract</b>:", "").replace("</td>", "").replace("&amp;", "&").trim());
				}else if(sl.contains("<td colspan=\"7\"><b>BibTeX</b>:")){
					result.add(a);
					
					a = new Article();
					bflag = false;
					iflag = 0;
					trflag = 0;
					tflag = true;
					jflag = true;
					aflag = true;
				}
			}
			if(bflag && iflag==2 && aflag && !sl.trim().equals("") && !sl.trim().equals("<td>")){
				a.setAuthor(sl.replace("&amp;", "|").replace(",", "|").trim()+"|");
				aflag = false;
			}
			if(bflag && iflag==3 && tflag && !sl.trim().equals("") && !sl.trim().equals("<td>")){
				a.setTitle(sl.replace("&amp;", "&").trim());
				tflag = false;
			}
			if(bflag && iflag==3 && sl.trim().contains("DOI</a>")){
				a.setUrl(sl.replace("[<a href=\"", "").replace("\">DOI</a>]", "").trim());
			}
			if(bflag && iflag==3 && sl.trim().contains("URL</a>")){
				a.setUrl(sl.replace("[<a href=\"", "").replace("\">URL</a>]", "").trim());
			}
			if(bflag && iflag==4){
				a.setYear(sl.replace("<td style=\"text-align: center;\">", "").replace("</td>", "").trim());
			}
			if(bflag && iflag==5 && jflag && !sl.trim().equals("") && !sl.trim().equals("<td>")){
				a.setJournal(sl.replace("&nbsp;", "").replace("&amp;", "&").trim());
				jflag = false;
			}
			if(bflag && iflag==6 && !sl.trim().equals("") && (sl.replace("<td>", "").replace("</td>", "").trim().equals("Article") || sl.replace("<td>", "").replace("</td>", "").trim().equals("InProceedings"))){
				a.setArticle(true);
			}
		}
		return result;
	}
	public static ArrayList solutionwithJsoup() throws IOException{
		ArrayList<Article> result = new ArrayList<Article>();
		
		String inpath = "C:/Users/Roc/Desktop/repository.html";
		File in = new File(inpath);
		Document doc = Jsoup.parse(in, "UTF-8");
		Elements els = doc.select("tr[class=\"entry\"]");
		for(Element el : els){
			Elements childels = el.select("td");
			int num = 0;
			Article a = new Article();
			a.setNum(++num);
		}
		return result;
	}
	public static void printArticleNumbers() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			boolean isArticle = a.isArticle();
			if(isArticle){
				num++;
			}
		}
		System.out.println(num);
	}
	public static Article[] selectIEEEOthers() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			String str = a.getUrl();
			boolean isArticle = a.isArticle();
			if(isArticle && !str.equals("") && str.contains("http://ieeexplore.ieee.org/")){
				a.setNum(++num);
				result.add(a);
				System.out.println(str);
			}
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static Article[] selectDoiOthers() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			String str = a.getUrl();
			boolean isArticle = a.isArticle();
			if(isArticle && !str.equals("") && str.contains("http://dx.doi.org/") 
					                        && !(str.contains("10.1145")
					                        || str.contains("10.1002")
					                        || str.contains("10.1109")
					                        || str.contains("10.1007")
					                        || str.contains("10.1016"))){
				a.setNum(++num);
				result.add(a);
				System.out.println(str);
			}
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static Article[] selectOthers() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			String str = a.getUrl();
			boolean isArticle = a.isArticle();
			if(isArticle && !str.equals("") && !str.contains("http://dx.doi.org/") 
					                        && !(str.contains("10.1145")
                                            || str.contains("10.1002")
                                            || str.contains("10.1109")
                                            || str.contains("10.1007")
                                            || str.contains("10.1016"))){
				a.setNum(++num);
				result.add(a);
				System.out.println(str);
			}
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static Article[] selectNoUrl() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			String str = a.getUrl();
			boolean isArticle = a.isArticle();
			if(isArticle && str.equals("")){
				a.setNum(++num);
				result.add(a);
				System.out.println(str);
			}
		}
		return (Article[]) result.toArray(new Article[0]);
	}
	public static Article[] selectArticle() throws IOException{
		int num = 0;
		ArrayList<Article> result = new ArrayList();
		ArrayList temp = solution();
		for(int i=0;i<temp.size();i++){
			Article a = (Article) temp.get(i);
			String str = a.getUrl();
			boolean isArticle = a.isArticle();
			if(isArticle && !str.equals("") && (str.contains("10.1145")
					                        || str.contains("10.1002")
					                        || str.contains("10.1109")
					                        || str.contains("10.1007")
					                        || str.contains("10.1016"))){
				a.setNum(++num);
				result.add(a);
				System.out.println(str);
			}
		}
		return (Article[]) result.toArray(new Article[0]);
	}

	
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
//		String outpath = "C:/Users/Roc/Desktop/result.txt";
//		Article[] a = selectArticle();
//		String outpath = "C:/Users/Roc/Desktop/resultEC.txt";
//		Article[] a = selectEC();
//		String outpath = "C:/Users/Roc/Desktop/resultDoiOthers.txt";
//		Article[] a = selectDoiOthers();
//		String outpath = "C:/Users/Roc/Desktop/resultOthers.txt";
//		Article[] a = selectOthers();
//		String outpath = "C:/Users/Roc/Desktop/resultNoUrl.txt";
//		Article[] a = selectNoUrl();
		String outpath = "C:/Users/Roc/Desktop/resultIEEE.txt";
		Article[] a = selectIEEEOthers();
		
//		FileWriter.solution(a, outpath);
		
		File orf = new File(outpath);
		FileOutputStream ros = new FileOutputStream(orf);
		
		for(int i=0;i<a.length;i++){
			System.out.println(a[i].getNum());
			System.out.println(a[i].getTitle());
			System.out.println(a[i].getDigest());
			System.out.println(a[i].getJournal());
			System.out.println(a[i].getYear());
			System.out.println(a[i].getCite());
			System.out.println(a[i].getKeyword());
			System.out.println(a[i].getAuthor());
			System.out.println(a[i].getInstution());
			System.out.println(a[i].getUrl());
			
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
			ros.write(a[i].getUrl().getBytes("utf-8"));
			ros.write("\r\n".getBytes());
		}
//		printArticleNumbers();
		long endTime = System.currentTimeMillis();
		System.out.println((endTime-startTime) + "ms");
	}
}
