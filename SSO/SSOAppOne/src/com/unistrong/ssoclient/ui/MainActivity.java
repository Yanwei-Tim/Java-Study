package com.unistrong.ssoclient.ui;

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

import com.min.ssoappone.R;
import com.unistrong.ssoclient.HttpAuth;
import com.unistrong.ssoclient.HttpClientHelper;
import com.unistrong.ssoclient.Result;

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
	
	// 不加密测试百度
	public void testBidu(View v){
		new Thread(){
			public void run() {
				HttpClient httpClient = HttpClientHelper.getDefaultHttpClient();
				HttpGet httpGet = new HttpGet("http://www.baidu.com");
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
				    reader.close();
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
	
	public void testHttps(View v) {
		new Thread(){
			public void run() {
				try {
					Result  result = HttpAuth.authPassword("https://auth.unistrong.com/AuthPassword", "compass1", "111111", "12354656");
					System.out.println("testHttps result: " + result.toString());
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
