import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * HTMLControler
 * 
 * GetHtml.java
 * @author Roc
 * @date 2016-6-27下午1:59:46
 * @Email zp0016@qq.com
 */
public class GetHtml {
	/**
	 * 用代理连接网站
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String getHtmlCodeFromURLByAgent(String urlStr) throws Exception {
		String type = "GET";
		HttpURLConnection conn = null;
		URL source = new URL(urlStr);
		conn = (HttpURLConnection) source.openConnection();
		conn.setRequestMethod(type);// 设定请求的方法，默认是GET
		conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
		conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
		conn.setReadTimeout(60000);
		// 服务器的安全设置不接受Java程序作为客户端访问
		// 什么是User Agent呢?
		// User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU
		// 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
		// 一些网站常常通过判断 UA 来给不同的操作系统、不同的浏览器发送不同的页面，因此可能造成某些页面无法在某个浏览器中正常显示，但通过伪装
		// UA 可以绕过检测。

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
	
	public static String getHtml(String url) throws IOException{
		
		String encoding="utf-8";
		String type="GET";
		InputStream in;
		StringBuilder builder = new StringBuilder();
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String str = "";
		try {
			URL source = new URL(url);
			conn = (HttpURLConnection)source.openConnection();
			conn.setRequestMethod(type);// 设定请求的方法，默认是GET   
			conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;   
			conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setReadTimeout(60000);
			//服务器的安全设置不接受Java程序作为客户端访问
			//什么是User Agent呢?
			//User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。　　
			//一些网站常常通过判断 UA 来给不同的操作系统、不同的浏览器发送不同的页面，因此可能造成某些页面无法在某个浏览器中正常显示，但通过伪装 UA 可以绕过检测。
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");		
			//conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in, encoding));
			
			while((str = reader.readLine()) != null) {
				if(str.contains("Cited by")){
					break;
				}
			}
			
		} catch (IOException e) {
			throw e;
		} finally {
			if(reader != null) {
				reader.close();
			}
			conn.disconnect();
		}
		return str;
	}
}
