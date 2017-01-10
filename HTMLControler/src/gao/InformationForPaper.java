/**
 * @file: InformationForPaper.java
 * @package: pers.guhun.acmcrawler
 * @Description: TODO 
 * @author: gao   
 * @date: 2016��6��17�� ����8:48:37
 * @email: 1534759775@qq.com 
 * @version V1.0
 */
package pers.guhun.acmcrawler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.guhun.crawler.CrawlerUtil;

/**
 * @ClassName: InformationForPaper
 */
public class InformationForPaper {

	public static int id = 0;

	public static void main(String[] args) throws Exception {

		getAllInfo();

	}

	public static void getAllInfo() throws Exception {

		File fileRoot = new File("icse-test\\");
		File[] files = fileRoot.listFiles();
		System.out.println(files.length);
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
			//��Ҫ������Ϣ�ĵ�ַ
			File file = files[i];
			List<String> list = FileUtils.readLines(file);

			//��������Ϣ��ĵ�ַ�浽finish�ļ���
			String fileDownName = "icse-finish\\";
			File fileDown = new File(fileDownName + files[i].getName());
			List<String> listDown = new ArrayList<>();

			if (fileDown.exists()) {
				listDown = FileUtils.readLines(fileDown);
			}

			for (int j = 0; j < list.size(); j++) {

				String urlStr = list.get(j);
				if (!listDown.contains(urlStr)) {
					System.out.println(urlStr);
					getInformations(urlStr, file.getName());

					FileUtils.writeStringToFile(fileDown, urlStr + "\r\n", true);
				}

			}
		}

	}

	/*
	 * ͨ�����������URL�����Ҫ����Ϣ
	 * 
	 * @param urlString:��������URL
	 */
	public static void getInformations(String urlString, String name) throws Exception {

		// ������Ϣ
		String html = CrawlerUtil.getHtmlCodeFromURLByAgent(urlString);
		if (html.equals("")) {
			return;
		}
		Document document = Jsoup.parse(html);

		// ƥ�����±���
		Elements titleElements = document.select("meta[name=\"citation_title\"]");
		String title = "";
		if (titleElements.size() > 0) {
			title = titleElements.first().attr("content");
		}

		// ƥ��ժҪ
		String abstractStr = "";
		String[] splitHtml = html.split("\n");
		String abstractUrl = "";
		String abstractTemp = "";
		for (int m = 0; m < splitHtml.length; m++) {
			if (splitHtml[m].contains("tab_abstract.cfm")) {
				// ժҪ��벿��url
				abstractTemp = splitHtml[m];
				break;
			}
		}
		abstractUrl = abstractTemp.substring(abstractTemp.indexOf("tab_abstract"), abstractTemp.indexOf("&usebody"));
		abstractUrl = "http://dl.acm.org/" + abstractUrl;
		System.out.println(abstractUrl);
		// ��ժҪҳ��ȡ
		abstractStr = getAbstract(abstractUrl);

		// ƥ��publisher
		Elements publisherElements = document.select("meta[name=\"citation_conference\"]");
		String publisher = "";
		if (publisherElements.size() > 0) {
			publisher = publisherElements.first().attr("content");
		}

		// ƥ�䷢�����
		Elements yearElements = document.select("meta[name=\"citation_date\"]");
		String yearTemp = yearElements.first().attr("content");
		// ���һ�������
		String year = "";
		if (yearElements.size() > 0) {
			year = yearTemp.split("/")[yearTemp.split("/").length - 1];
		}

		// ƥ��ؼ���
		// <meta name="citation_keywords" content="
		Elements keywordsElements = document.select("meta[name=\"citation_keywords\"]");
		String keywords = "";
		if (keywordsElements.size() > 0) {
			String keywordsTemp = keywordsElements.first().attr("content");
			keywordsTemp = keywordsTemp.replaceAll("\n", "");
			String[] split = keywordsTemp.split(";");
			for (int i = 0; i < split.length; i++) {
				keywords += split[i].trim() + "|";
			}
		}
		System.out.println(keywords);

		// ƥ������author

		// <td valign="top" nowrap="nowrap">
		Elements authorElements = document.select("td[valign=\"top\"]");
		String author = "";
		if (authorElements.size() > 0) {
			for (Element element : authorElements) {
				if (element.select("a").size() > 0 && !element.text().equals("")) {
					author += element.text() + "|";
				}
			}
		}

		// ƥ�����
		// <td valign="bottom">
		Elements institutionElements = document.select("td[valign=\"bottom\"]");
		String institution = "";
		if (institutionElements.size() > 0) {
			for (Element element : institutionElements) {
				institution += element.text() + "|";
			}
		}

		// ƥ��������
		// <td class="small-text" colspan="2" valign="top"
		// style="padding-left:30px;">
		String citeTime = "0";
		Elements citeTimeElements = document.select("td[style=\"padding-left:30px;\"]");
		if (citeTimeElements.size() > 0) {
			// �������������ڵĶ�����Ϣ
			String temp = citeTimeElements.first().text();
			String[] splitTemp = temp.split("Citation Count:");
			citeTime = splitTemp[1].trim();
		}

		id++;
		String resultInfo = id + "\r\n" + title + "\r\n" + abstractStr + "\r\n" + publisher + "\r\n" + year + "\r\n"
				+ citeTime + "\r\n" + keywords + "\r\n" + author + "\r\n" + institution + "\r\n";
		FileUtils.writeStringToFile(new File("E:\\experiments\\ICSE\\ICSE_ACM_crawler_data_" + name + ".txt"),
				resultInfo, true);
		System.out.println(id);
	}

	// ��ACM��ժҪҳ��ȡժҪ
	public static String getAbstract(String urlStr) throws Exception {
		String abstractStr = "";
		String html = CrawlerUtil.getHtmlCodeFromURLByAgent(urlStr);
		if (!html.equals("")) {
			Document document = Jsoup.parse(html);
			// <div style="display:inline">
			Elements elements = document.select("div[style=\"display:inline\"]");
			if (elements.size() > 0) {
				abstractStr = elements.first().text();
			}
		}
		return abstractStr;
	}

}
