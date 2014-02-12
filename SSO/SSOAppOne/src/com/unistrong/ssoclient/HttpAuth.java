package com.unistrong.ssoclient;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class HttpAuth {
	
	/***
	 * 向认证服务器认证
	 * @param authURL    认证服务器URL
	 * @param username	用户名
	 * @param password	密码
	 * @param deviceID		设备ID,暂时未使用
	 * @return Result
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static Result authPassword(String authURL, String username, String password, String deviceID) throws ClientProtocolException, IOException{
		HttpClient httpClient = HttpClientHelper.getHttpClient();
		HttpPost httpPost = new HttpPost(authURL);
		StringEntity formEntity = new StringEntity(String.format("userid=%s&password=%s&deviceid=%s", 
				HttpCodec.encodeHttp(username), HttpCodec.encodeHttp(password),HttpCodec.encodeHttp(deviceID)));
		formEntity.setContentType("application/x-www-form-urlencoded");
		httpPost.setEntity(formEntity);
		HttpResponse response = httpClient.execute(httpPost);  
		
		HttpEntity rspEntity = response.getEntity();
		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (rspEntity != null) {
			System.out.println("Response content length: "+ rspEntity.getContentLength());
			rspEntity.consumeContent();
		}
		
		String code = response.getFirstHeader("authcode").getValue();
		String ticket = response.getFirstHeader("authdata").getValue();
		String msg = response.getFirstHeader("authmsg").getValue();

		int iCode = Integer.parseInt(code);
		Result rst = new Result(iCode, HttpCodec.decodeHttp(msg));
		rst.setData(HttpCodec.decodeHttp(ticket));
		return rst;
	}
}

/*
public static Result authPassword(String username, String password, String deviceID) throws ClientProtocolException, IOException{
	HttpClient httpClient = HttpClientHelper.getHttpClient();
	HttpPost httpPost = new HttpPost("https://cas.min.com/AppServer/LoginServlet");
	StringEntity formEntity = new StringEntity(String.format("userid=%s&password=%s&deviceid=%s", username, password,deviceID));
	formEntity.setContentType("application/x-www-form-urlencoded");
	httpPost.setEntity(formEntity);
	HttpResponse response = httpClient.execute(httpPost);  
	
    HttpEntity rspEntity = response.getEntity();
    System.out.println("----------------------------------------");   
    System.out.println(response.getStatusLine());
   if (rspEntity != null) {
      System.out.println("Response content length: " + rspEntity.getContentLength());   
    }
   
   BufferedInputStream is = new BufferedInputStream(rspEntity.getContent());
   byte[] buf = new byte[1024];
   int count = 0;
   int readCount = 0;
   while((count = is.read(buf, readCount, 1024-readCount))!= -1){
   	readCount += count;
   }
   if (rspEntity != null) {
   	rspEntity.consumeContent();
    }
   is.close();
   Result result = parse(buf, readCount);
	return result;
}

@SuppressLint("NewApi")
private static Result parse(byte[] buffer, int length) throws IOException{
	 ByteArrayInputStream is = new ByteArrayInputStream(buffer, 0, length);
	 Properties properties=new Properties();         
	 properties.load(new InputStreamReader(is, "UTF-8"));
	 is.close();
	 String code = properties.getProperty("code");
	 String data = properties.getProperty("data");
	 String msg = properties.getProperty("msg");
	 if (code == null)
		 return new Result(-1, "协议错误");
	 
	 int iCode = Integer.parseInt(code);
	 Result rst = new Result(iCode, msg);
	 rst.setData(data);
	 return rst;
}
* */
