package com.testoauth.activity;

import com.callbackdemo.CpicAPI;
import com.callbackdemo.GetMethod;

import com.example.testoauth.R;
import com.testoauth.object.ViewTool;
import com.testoauth.viewMethod.actionMethod;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	
	private EditText userNameEdit;
	private EditText userPwdEdit;
	private Button loginBtn;
	private ProgressDialog dialog;
	private String userName=null;
	private String userPwd=null;
	private String result;
	private Context context;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 init();
		viewInit();	
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 userName = userNameEdit.getText().toString();
				 userPwd = userPwdEdit.getText().toString();
				if(userName!=null&&userPwd!=null){
					GetMethod.StartLogin(cpicAPI,context, userName, userPwd);
				}	
			}
		});
		
	}
	
	private void init() {
		context = this;
		actionMethod aM=new actionMethod();
	    ViewTool vt = aM.actionTool(LoginActivity.this);
	    vt.getRl().setBackgroundColor(Color.BLUE);
	    vt.getTv().setText("登录");
	    vt.getTv().setTextSize(17);
	    vt.getIv().setBackgroundResource(R.drawable.button_selector_back);
	    vt.getIv().setOnClickListener(listen);
	}

	CpicAPI cpicAPI = new CpicAPI() {
		
		@Override
		public void onLoginSuccess(String arg0) {
			// TODO Auto-generated method stub
			result = arg0;
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
					if(result.equals("null")){
						Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent();
						intent.putExtra("userName", userNameEdit.getText().toString());
						setResult(0x12, intent);
						SharedPreferences sp=getSharedPreferences("gao", MODE_PRIVATE);
						Editor et=sp.edit();
						et.putString("userName", userName);
						et.putString("userPwd", userPwd);
						et.commit();					
					}
					
					finish();
				}
			});
		
		}
		
		@Override
		public void onLoginStart() {
			// TODO Auto-generated method stub
			dialog.show();
		} 
		
		@Override
		public void onLoginFailed() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void OnLoginEnd() {
			// TODO Auto-generated method stub
			
		}
	};
	//返回监听
	private OnClickListener listen=new  OnClickListener() {
        @Override
        public void onClick(View v) {
        	finish();
        }
    };  
	
	public void viewInit(){
		userNameEdit = (EditText) findViewById(R.id.userName);
		userPwdEdit = (EditText) findViewById(R.id.userPwd);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("正在登录,请稍等");
		dialog.setCancelable(false);
	}


	
	
}
