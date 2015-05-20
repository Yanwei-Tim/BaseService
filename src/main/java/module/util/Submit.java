package module.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public abstract class Submit {
	private static HttpClient httpClient = new DefaultHttpClient();

	public static String get(String url, String charset) throws Exception {
		String responseContent = null;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			responseContent = EntityUtils.toString(entity, charset);
			EntityUtils.consume(entity);
		}
		return responseContent;
	}
}