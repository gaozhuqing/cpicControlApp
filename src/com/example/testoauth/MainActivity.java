package com.example.testoauth;

import java.io.BufferedReader;

import java.io.InputStreamReader;

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
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;

import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	private EditText userNameEdit;
	private EditText userPwdEdit;
	private Button loginBtn;

	String bearer = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewInit();
		
		String userName;
		String userPwd;
		userName = userNameEdit.getText().toString();
		userPwd = userPwdEdit.getText().toString();
		//gaozhuqing
	}
	
	public void viewInit(){
		userNameEdit = (EditText) findViewById(R.id.userName);
		userPwdEdit = (EditText) findViewById(R.id.userPwd);
	}

//	class myThread extends Thread {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			try {
//				bearer = postHttp("http://139.196.177.74/token");
//				JSONObject jsonObject;
//				String token;
//				jsonObject = new JSONObject(bearer);
//				token = jsonObject.get("access_token").toString();
//				Log.i("123", getHttp("http://139.196.177.74/api/runClient/AutoRunScene?device=222", token));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			super.run();
//		}
//	}

	
	
}
