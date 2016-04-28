package com.example.testoauth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Base64;

public class HttpUtils {
public static String postHttp(String url) throws Exception {
		
		

		String str = "console:test";
		String strBase64 = new String(Base64.encode(str.getBytes(),
				Base64.DEFAULT),"UTF-8").trim();	
		HttpClient client = new DefaultHttpClient();       
        // 实例化HTTP方法  
        HttpPost request = new HttpPost(url);  
        request.setHeader("Authorization", "Basic "+strBase64.toString());
        
          
        // 创建名/值组列表  
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
        parameters.add(new BasicNameValuePair("grant_type", "password"));  
        parameters.add(new BasicNameValuePair("username", "mango"));
        parameters.add(new BasicNameValuePair("password", "123"));  
          

        // 创建UrlEncodedFormEntity对象  
        UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(  
                parameters);  
        request.setEntity(formEntiry);  
        // 执行请求  
        HttpResponse response = client.execute(request);  

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()  
                .getContent()));  
        StringBuffer sb = new StringBuffer("");  
        String line = "";  
        String NL = System.getProperty("line.separator");  
        while ((line = in.readLine()) != null) {  
            sb.append(line + NL);  
        }  
        in.close();  
        String result = sb.toString();  
        return result;  

	}
	//http://139.196.177.74/api/runClient/AutoRunScene?device={device}
public static String getHttp(String url,String header){
		
		
		HttpGet getMethod = new HttpGet(url);
		
		getMethod.setHeader("Authorization", "Bearer "+header);
		HttpClient httpClient = new DefaultHttpClient();
		try {
			  
			HttpResponse response = httpClient.execute(getMethod); // 发起GET请求
			
			String htmlResponse1 = EntityUtils.toString(
					response.getEntity(), "utf-8");

			return htmlResponse1;  
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
   
}