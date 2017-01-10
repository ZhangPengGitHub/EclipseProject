import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * HTMLControler
 * 
 * SimpleClient.java
 * 
 * @author Roc
 * @date 2016-6-27ÉÏÎç10:23:10
 * @Email zp0016@qq.com
 */
public class SimpleClient {
	public static void main(String[] args) throws IOException {
		int i=0;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://dx.doi.org/10.1109/ICSTW.2011.23");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
}
