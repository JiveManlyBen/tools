package com.benlevy.tools;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HTTPTools {

	private static final int timeout = 60000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static String post(String url) throws ClientProtocolException, IOException {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(timeout)
			    .setConnectTimeout(timeout)
			    .setConnectionRequestTimeout(timeout)
			    .setStaleConnectionCheckEnabled(true)
			    .build();
		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.setDefaultRequestConfig(defaultRequestConfig)
				.build();
		try {
			HttpPost httppost = new HttpPost(url);
			System.out.println(String.format("%s: Executing Request - %s",  new Date(), httppost.getRequestLine()));
			HttpResponse response = httpclient.execute(httppost);
			System.out.println(String.format("%s: Returned Status - %s", new Date(), response.getStatusLine()));
			ResponseHandler<String> basicRH = new BasicResponseHandler();
			String responseString = basicRH.handleResponse(response); 
			return responseString;
		}
		finally {
			httpclient.close();
		}
	}

}