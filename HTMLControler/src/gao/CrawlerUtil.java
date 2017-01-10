/**
 * file_name: CrawlerUtil.java
 * package_name: pers.guhun.crawler
 * project_name: crawler_test
 * data: 2016��6��15������10:52:35
 * email: 1534759775@qq.com
 *
 */
package pers.guhun.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Gao
 *
 */
public class CrawlerUtil {

	public static List<Pair> proxyList = new ArrayList<>();

	public static void main(String[] args) throws Exception {

		String string = "https://scholar.google.com/scholar?q=Parallel+Randomized+State-Space+Search.";
		getInfo(string);
		System.out.println("finished");
	}

	public static String getByAgent(String urlStr) throws Exception {
		// sleepRandom();
		String html = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpHost proxy = new HttpHost("127.0.0.1", 8787, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		HttpGet request = new HttpGet(urlStr);
		request.setConfig(config);

		System.out.println("Executing request " + request.getRequestLine() + " to " + "  " + " via " + proxy);

		CloseableHttpResponse response = httpclient.execute(request);
		// System.out.println("----------------------------------------");
		System.out.println("status:" + response.getStatusLine());
		if (response.getStatusLine().toString().contains("202")) {
			html = EntityUtils.toString(response.getEntity());
		}

		// System.out.println(EntityUtils.toString(response.getEntity()));

		response.close();
		httpclient.close();
		return html;
	}

	public static void getInfo(String string) {
		String html = "";
		// ����ip�Լ��˿�
		Pair pair = new Pair();

		try {
			pair = getProxy();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			html = getHtmlCode(string, pair.getIp(), pair.getPort());
			if (html.equals("")) {
				getInfo(string);
			}
		} catch (Exception e) {
			getInfo(string);
		}
		// System.out.println(html);
	}

	// �Ӵ����б���վ��ô���ip�Լ��˿�
	public static Pair getProxy() throws Exception {

		if (proxyList.size() == 0) {
			String urlProxy = "http://www.xicidaili.com/wn/";
			String html = getHtmlCodeFromURLByAgent(urlProxy);
			Document document = Jsoup.parse(html);
			String ipProxy = "";
			int port = 0;
			// <tr class="odd">
			Elements elements = document.select("tr[class=\"odd\"]");

			int i = 0;
			for (Element element : elements) {
				Elements elements2 = element.select("td");
				ipProxy = elements2.get(1).text().toString();
				port = Integer.parseInt(elements2.get(2).text().toString());
				Pair proxy = new Pair();
				proxy.setIp(ipProxy);
				proxy.setPort(port);
				proxyList.add(proxy);
				System.out.println(ipProxy + ":" + port);
				i++;
				if (i > 9) {
					break;
				}

			}
			System.out.println("proxy count:" + i);

		}
		Pair pair = proxyList.get(0);
		proxyList.remove(0);
		return pair;
	}

	/**
	 * ʹ�ô�����ȡ
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String getHtmlCode(String urlStr, String ip, int port) throws Exception {
		sleepRandom();
		String html = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpHost proxy = new HttpHost(ip, port, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		HttpGet request = new HttpGet(urlStr);
		request.setConfig(config);

		System.out.println("Executing request " + request.getRequestLine() + " to " + "  " + " via " + proxy);

		CloseableHttpResponse response = httpclient.execute(request);
		// System.out.println("----------------------------------------");
		System.out.println("status:" + response.getStatusLine());
		if (response.getStatusLine().toString().contains("200")) {
			html = EntityUtils.toString(response.getEntity());
		}

		// System.out.println(EntityUtils.toString(response.getEntity()));

		response.close();
		httpclient.close();
		return html;
	}

	/**
	 * ���˯��10-20s
	 * 
	 * @throws Exception
	 */
	public static void sleepRandom() throws Exception {
		Random random = new Random();
		int time = random.nextInt(10) + 10;
		time = time * 1000;
		System.out.println("sleep:" + time);
		Thread.sleep(time);
	}

	/**
	 * �ж�url�Ƿ��������
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static URL isconnect(String urlStr) throws Exception {

		sleepRandom();
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
			System.out.println("����ʧ��");
			url = null;
			return url;
		}
	}

	/**
	 * �ô���������վ
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String getHtmlCodeFromURLByAgent(String urlStr) throws Exception {
		sleepRandom();
		String type = "GET";
		HttpURLConnection conn = null;
		URL source = new URL(urlStr);
		conn = (HttpURLConnection) source.openConnection();
		conn.setRequestMethod(type);// �趨����ķ�����Ĭ����GET
		conn.setDoOutput(true);// http�����ڣ������Ҫ��Ϊtrue, Ĭ���������false;
		conn.setDoInput(true); // �����Ƿ��httpUrlConnection���룬Ĭ���������true;
		conn.setReadTimeout(60000);
		// �������İ�ȫ���ò�����Java������Ϊ�ͻ��˷���
		// ʲô��User Agent��?
		// User Agent������Ϊ�û�������� UA������һ�������ַ���ͷ��ʹ�÷������ܹ�ʶ��ͻ�ʹ�õĲ���ϵͳ���汾��CPU
		// ���͡���������汾���������Ⱦ���桢��������ԡ����������ȡ�
		// һЩ��վ����ͨ���ж� UA ������ͬ�Ĳ���ϵͳ����ͬ����������Ͳ�ͬ��ҳ�棬��˿������ĳЩҳ���޷���ĳ���������������ʾ����ͨ��αװ
		// UA �����ƹ���⡣

		conn.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, likeGecko) Chrome/51.0.2704.84 Safari/537.36");

		System.out.println(conn.getResponseCode());
		String html ="";
		if(conn.getResponseCode()==200)
		{
			 html=IOUtils.toString(conn.getInputStream());

		}
		return html;
	}
}
