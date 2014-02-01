package com.benlevy.tools;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HTTPTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public static String post(String url) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		System.out.println(String.format("[%tT] URL: %s", new Date(), url));
		HttpPost httppost = new HttpPost(url);
		CloseableHttpResponse response = null;
        try {
			response = client.execute(httppost);
	        System.out.println(String.format("[%tT] Returned Status: %s", new Date(), response.getStatusLine()));
		    HttpEntity entity = response.getEntity();
		    String responseString = EntityUtils.toString(entity); 
		    System.out.println("\nResponse:\n\n" + responseString);
		    EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null)
					response.close();
				client.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
