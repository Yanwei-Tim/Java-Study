package com.min.ssoappone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void testHttps2(View v){
		Log.d("aaaaaa", "testHttps2");
	}
	
	public void testHttps(View v) {
		new Thread(){
			public void run() {
				HttpClient httpClient = HttpClientHelper.getHttpClient();
				HttpPost httpPost = new HttpPost("https://cas.min.com/AppServer/LoginServlet");
				try {
					StringEntity formEntity = new StringEntity("userid=admin&password=123456");
					formEntity.setContentType("application/x-www-form-urlencoded");
					httpPost.setEntity(formEntity);
					HttpResponse response = httpClient.execute(httpPost);  
					
				     HttpEntity rspEntity = response.getEntity();
				     System.out.println("----------------------------------------");   
				     System.out.println(response.getStatusLine());   
				    if (rspEntity != null) {
				       System.out.println("Response content length: " + rspEntity.getContentLength());   
				     }   
				    // 显示结果   
				     BufferedReader reader = new BufferedReader(new InputStreamReader(rspEntity.getContent(), "UTF-8"));   
				     String line = null;   
				    while ((line = reader.readLine()) != null) {   
				       System.out.println(line);   
				     }   
				    if (rspEntity != null) {   
				    	rspEntity.consumeContent();   
				     }
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}catch ( ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void testMDM(View v) {
		new Thread(){
			public void run() {
				HttpClient httpClient = HttpClientHelper.getHttpClient();
				HttpGet httpGet = new HttpGet("https://mdm.unistrong.com/employee/login.jsp");
				try {
					HttpResponse response = httpClient.execute(httpGet);  
					
				     HttpEntity rspEntity = response.getEntity();
				     System.out.println("----------------------------------------");   
				     System.out.println(response.getStatusLine());   
				    if (rspEntity != null) {
				       System.out.println("Response content length: " + rspEntity.getContentLength());   
				     }   
				    // 显示结果   
				     BufferedReader reader = new BufferedReader(new InputStreamReader(rspEntity.getContent(), "UTF-8"));   
				     String line = null;   
				    while ((line = reader.readLine()) != null) {   
				       System.out.println(line);   
				     }   
				    if (rspEntity != null) {   
				    	rspEntity.consumeContent();   
				     }
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}catch ( ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
