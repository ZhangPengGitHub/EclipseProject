import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * HTMLControler
 * 
 * JsoupTest.java
 * 
 * @author Roc
 * @date 2016-6-27上午9:32:40
 * @Email zp0016@qq.com
 */
public class JsoupTest {
	public static String InstitutionIEEE(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("meta[name=\"citation_author_institution\"]");
		for (Element el : els) {
			result.append(el.attr("content") + "|");
		}
		return result.toString();
	}

	public static String KeywordIEEE(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("meta[name=\"citation_keywords\"]");
		for (Element el : els) {
			result.append(el.attr("content").replace("\n", "")
					.replace("\t", "").replace("; ", "|").replace(";", "|")
					.trim());
		}
		return result.toString();
	}

	public static String InstitutionACM(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("a[title=\"Institutional Profile Page\"]");
		for (Element el : els) {
			result.append(el.text() + "|");
		}
		return result.toString();
	}

	public static String KeywordACM(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("meta[name=\"citation_keywords\"]");
		// for(Element el : els){
		// result.append(el.text()+"|");
		// }
		if (els.size() > 0) {
			Element el = els.first();
			result.append(el.attr("content").replace("; ", "|"));
		}
		return result.toString();
	}

	public static String InstitutionSpringer(String html) throws Exception {
		int num = 0;
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("ul[class=\"author-affiliations\"] span[class=\"affiliation\"]");
		if (els.size() > 0) {
			for (Element el : els) {
				result.append(el.text() + "|");
			}
		} else {
			els = doc.select("ul[class=\"author__affiliations\"] span");
			for (Element el : els) {
				num++;
				if (num % 2 == 0) {
					result.append(el.text() + "|");
				} else {
					result.append(el.text() + ", ");
				}

			}
		}
		return result.toString();
	}

	public static String KeywordSpringer(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("span[class=\"Keyword\"]");
		if (els.size() > 0) {
			for (Element el : els) {
				result.append(el.text() + "|");
			}
		} else {
			els = doc.select("ul[class=\"abstract-about-subject\"] a");
			for (Element el : els) {
				result.append(el.text() + "|");
			}
		}

		return result.toString();
	}

	public static String InstitutionScience(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("ul[class=\"affiliation authAffil\"] span");
		for (Element el : els) {
			result.append(el.text() + "|");
		}

		return result.toString();
	}

	public static String KeywordScience(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("li[class=\"svKeywords\"] span");
		for (Element el : els) {
			result.append(el.text() + "|");
		}
		return result.toString();
	}

	public static String KeywordWiley(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc
				.select("ul[class=\"keywordList\"][id=\"abstractKeywords1\"] li");
		for (Element el : els) {
			result.append(el.text().replace(";", "") + "|");
		}
		return result.toString();
	}

	public static String InstitutionWiley(String html) throws Exception {
		StringBuffer result = new StringBuffer();
		// String url = "http://dx.doi.org/10.1145/2372251.2372260";
		// String html = GetHtml.getHtmlCodeFromURLByAgent(url);
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("ol[id=\"authorsAffiliations\"] p");
		for (Element el : els) {
			result.append(el.text() + "|");
		}
		return result.toString();
	}
	public static void getWho(ArrayList result,String urlString) throws Exception {
		int i = 0;
		String html = "";
		Who who = new Who();
		if (null != isconnect(urlString)){
			html = GetHtml.getHtmlCodeFromURLByAgent(urlString);
		}
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("tr td");
		for (Element el : els) {
			i++;
			if(i>=4){
				if((i-2)%3==0){
					who = new Who();
					who.setName(el.text());
				}
				if((i)%3==0){
					who.setInstitutionString(el.text());
					result.add(who);
				}
			}	
		}
		els = doc.select("a[href^=\"index.php?\"]");
		for (Element el : els) {
			if(el.text().equals("Next")){
				System.out.println("Next");
				getWho(result,"http://crestweb.cs.ucl.ac.uk/resources/sbse_repository/whoswho/"+el.attr("href"));
			}
		}
	}
	public static void sleepRandom() throws Exception {
		Random random = new Random();
		int time = random.nextInt(10) + 10;
		time = time * 1000;
		System.out.println("sleep:" + time);
		Thread.sleep(time);
	}
	
	/**
	 * 判断url是否可以连接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static URL isconnect(String urlStr) throws Exception {

		//sleepRandom();
		URL url;
		int responseCode = -1;

		HttpURLConnection connection;

		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");

			responseCode = connection.getResponseCode();
			// System.out.println(responseCode);

			if (responseCode != 200) {
				url = null;
			}
			return url;
		} catch (Exception e) {
			System.out.println("连接失败");
			url = null;
			return url;
		}
	}
	public static void main(String[] args) throws Exception {
		String html = GetHtml
				.getHtmlCodeFromURLByAgent("http://dx.doi.org/10.1007/s10515-014-0148-0");
		System.out.println(InstitutionSpringer(html));
		System.out.println(KeywordSpringer(html));
	}
}
