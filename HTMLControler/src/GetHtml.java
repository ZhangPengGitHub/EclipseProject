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
 * @date 2016-6-27����1:59:46
 * @Email zp0016@qq.com
 */
public class GetHtml {
	/**
	 * �ô���������վ
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
			conn.setRequestMethod(type);// �趨����ķ�����Ĭ����GET   
			conn.setDoOutput(true);// http�����ڣ������Ҫ��Ϊtrue, Ĭ���������false;   
			conn.setDoInput(true); // �����Ƿ��httpUrlConnection���룬Ĭ���������true;
			conn.setReadTimeout(60000);
			//�������İ�ȫ���ò�����Java������Ϊ�ͻ��˷���
			//ʲô��User Agent��?
			//User Agent������Ϊ�û�������� UA������һ�������ַ���ͷ��ʹ�÷������ܹ�ʶ��ͻ�ʹ�õĲ���ϵͳ���汾��CPU ���͡���������汾���������Ⱦ���桢��������ԡ����������ȡ�����
			//һЩ��վ����ͨ���ж� UA ������ͬ�Ĳ���ϵͳ����ͬ����������Ͳ�ͬ��ҳ�棬��˿������ĳЩҳ���޷���ĳ���������������ʾ����ͨ��αװ UA �����ƹ���⡣
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
