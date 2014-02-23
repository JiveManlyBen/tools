package com.benlevy.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class HTTPTools {

	private static final int timeout = 60000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static String get(String url) throws ClientProtocolException, IOException {
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
			HttpGet httpget = new HttpGet(url);
			System.out.println(String.format("%s: Executing Request - %s",  new Date(), httpget.getRequestLine()));
			HttpResponse response = httpclient.execute(httpget);
			System.out.println(String.format("%s: Returned Status - %s", new Date(), response.getStatusLine()));
			ResponseHandler<String> basicRH = new BasicResponseHandler();
			String responseString = basicRH.handleResponse(response); 
			return responseString;
		}
		finally {
			httpclient.close();
		}
	}

	public static String get(String url, Map<String, String> params) throws ClientProtocolException, IOException {
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
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for(Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
			HttpGet httpget = new HttpGet(url + "?" + paramsString);
			System.out.println(String.format("%s: Executing Request - %s",  new Date(), httpget.getRequestLine()));
			HttpResponse response = httpclient.execute(httpget);
			System.out.println(String.format("%s: Returned Status - %s", new Date(), response.getStatusLine()));
			ResponseHandler<String> basicRH = new BasicResponseHandler();
			String responseString = basicRH.handleResponse(response); 
			return responseString;
		}
		finally {
			httpclient.close();
		}
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