import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class IEEE {
	public static Article[] getInformation(Article[] temp) throws Exception{
		ArrayList<Article> result = new ArrayList();
		
		String keyword = "";
		boolean flag = false;
		
		for(int i=0;i<temp.length;i++){
			int time = (int) (Math.random()*10000+10);
			Thread.sleep(time);
			System.out.println(i);
//			if(i>5){
//				break;
//			}
			String institution = "";
			if (null != isconnect(temp[i].getUrl())){
				String html = GetHtml.getHtmlCodeFromURLByAgent(temp[i].getUrl());
				institution = JsoupTest.InstitutionIEEE(html);
				keyword = JsoupTest.KeywordIEEE(html);
				temp[i].setInstution(institution);
				temp[i].setKeyword(keyword);
			}
			if(null != isconnect(temp[i].getCiteUrl())){
				String str = GetHtml.getHtmlCodeFromURLByAgent(temp[i].getCiteUrl());
				String res = Googlescholartest.getCiteTimeFromScholar(str);
				if (!res.equals("")) {
					String[] split = res.split("\\+++");
					temp[i].setCite(Integer.valueOf(split[1]));
					System.out.println(split[1]);
				}
			}
			result.add(temp[i]);
		}
		return result.toArray(new Article[0]);
	}
	public static Article[] setUrl() throws IOException, InterruptedException{
		int num=0;
		Article[] a = RepositoryHtml.selectIEEEOthers();
		ArrayList<Article> urlList = new ArrayList();
		ArrayList<Article> result = new ArrayList();
		for(int i=0;i<a.length;i++){
			if(a[i].getUrl().contains("http://ieeexplore.ieee.org/")){
				urlList.add(a[i]);
			}
		}
		System.out.println(urlList.size());
		for(Article temp : urlList){
			String citeUrl = "https://scholar.google.com/scholar?q="+temp.getTitle().replace(" ", "+")+".";
			temp.setCiteUrl(citeUrl);
			result.add(temp);
		}
		System.out.println(result.size());
		return result.toArray(new Article[0]);
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
	public static void main(String[] args) throws Exception{
		Article[] temp = setUrl();
		Article[] a = getInformation(temp);
		String outpath = "C:/Users/Roc/Desktop/IEEEExplore.txt";
		FileWriter.solution(a,outpath);
	}
}
