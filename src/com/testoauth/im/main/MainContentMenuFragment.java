package com.testoauth.im.main;

import com.example.testoauth.R;
import com.testoauth.util.Helper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@SuppressLint("SetJavaScriptEnabled") public class MainContentMenuFragment extends Fragment {
	private MainActivity mActivity = null;
	// 主界面
		private WebView webview = null;
		private static ProgressDialog mDialog;
		private View view;
	@SuppressLint("InflateParams") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view=inflater.inflate(R.layout.web, null);
		mActivity =(MainActivity) this.getActivity();
		webview = (WebView) view.findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);  
        webview.requestFocus();  
//      webview.loadUrl("file:///android_asset/risktest.html");  
        webview.loadUrl("http://139.196.177.74/");  
        // 设置web视图客户端  
        webview.setWebViewClient(new MyWebViewClient());  
        webview.setDownloadListener(new MyWebViewDownLoadListener());
      
		return view;
	}
public	class  MyWebViewClient extends WebViewClient {  
	
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			return false;
		}

		 public void onPageStarted(WebView view, String url, Bitmap favicon) {  
			 Log.i("123","onPageStarted");  
			 mDialog = Helper.showProgress(mActivity);  
			 mDialog.show();
	        } 
		 	@Override//监听按键事件
	        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
	      
	        return super.shouldOverrideKeyEvent(view, event);
	        }
	  
	        public void onPageFinished(WebView view, String url) {  
	        	 Log.i("123","onPageFinished");  
	            closeProgress();  
	        }  
	  
	        public void onReceivedError(WebView view, int errorCode,  
	                String description, String failingUrl) {  
	        	 Log.i("123","onReceivedError");  
	            closeProgress();  
	        }  
	        
	}

private class MyWebViewDownLoadListener implements DownloadListener {

	@Override
	public void onDownloadStart(String url, String userAgent, String contentDisposition,
			String mimetype, long contentLength) {
		
		
	}
	
}

private void closeProgress(){   
    if(mDialog!=null){   
        mDialog.dismiss();   
        mDialog=null;   
    }   
}   
//返回
public  boolean canGoBack() {
    return webview != null && webview.canGoBack();
}
//webview返回上一页
public  void goBack() {
    if (webview != null) {
    	webview.goBack();
    }
}


}
