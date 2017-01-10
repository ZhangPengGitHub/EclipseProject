/**
 * file_name: CiteTimeFromScholar.java
 * package_name: pers.guhun.scholarcrawler
 * project_name: crawler_test
 * data: 2016年6月15日下午2:39:09
 * email: 1534759775@qq.com
 *
 */
package pers.guhun.scholarcrawler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.guhun.crawler.CrawlerUtil;

/**
 * @author Gao
 *
 */
public class CiteTimeFromScholar {
	public static void main(String[] args) throws Exception {

		String urlStr = "https://scholar.google.com/scholar?q=Intra-Vehicle+Networks%3A+A+Review.";
		String result = getCiteTimeFromScholar(urlStr);
		if (!result.equals("")) {
			String[] split = result.split("\\+++");
			System.out.println("title:" + split[0]);
			System.out.println("citetime:" + split[1]);
		}
	}

	public static String getCiteTimeFromScholar(String urlStr) throws Exception {
		// String
		// urlStr="https://scholar.google.com/scholar?q=Intra-Vehicle+Networks%3A+A+Review.";
		String html = CrawlerUtil.getHtmlCode(urlStr);
		// System.out.println(html);
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
		
		return resultStr;

	}

}
