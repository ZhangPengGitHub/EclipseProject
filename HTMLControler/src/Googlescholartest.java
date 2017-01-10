
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Googlescholartest {

	public static String getCiteTimeFromScholar(String html) throws Exception {
		Document document = Jsoup.parse(html);
		// <div class="gs_ri"> 一篇论文相关信息
		Elements paperElements = document.select("div[class=\"gs_ri\"]");
		String title = "";
		int citeTime = 0;
		String resultStr = "";
		if (paperElements.size() > 0) {
			// <h3 class="gs_rt"> 论文题目
			Element titleElement = paperElements.first().select("h3[class=\"gs_rt\"]").first();
			title = titleElement.text();
			// <a href="/scholar?cites 论文应用数量
			Elements citeElements = paperElements.first().select("a[href^=\"/scholar?cites\"]");
			//scholar上有一些搜索结果没有被引用次数
			if (citeElements.size() > 0) {
				Element citeElement = citeElements.first();
				String cite = citeElement.text();
				String[] split = cite.split(" ");
				citeTime = Integer.parseInt(split[split.length - 1]);
			}
		}
		resultStr = title + "+++" + String.valueOf(citeTime);
		System.out.println(resultStr);
		return resultStr;
	}
	
	public static void main(String[] args) throws Exception{
		String url="https://scholar.google.com/scholar?q=Automated+Refactoring+for+Testability.";
		String str = GetHtml.getHtmlCodeFromURLByAgent(url);
		String result = getCiteTimeFromScholar(str);
		
		if (!result.equals("")) {
			String[] split = result.split("\\+++");
			
			System.out.println("title:" + split[0]);
			System.out.println("citetime:" + split[1]);
		}
	}
}
