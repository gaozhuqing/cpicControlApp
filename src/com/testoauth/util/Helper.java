package com.testoauth.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Helper {
	
	
	public static boolean havePackage(String appPackegeName,Context context){
		PackageManager pm = context.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
            	
            	if((packageInfo.applicationInfo.packageName).equals(appPackegeName)){
            		return true;
            	}

            } 
 
        }
		return false;

	}
	
	public  static String timeChangeMethod(String  timeDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = new Date();
		Date  pastTime=df.parse(timeDate);
		Date currentTime =	df.parse(df.format(time));
		long diff= currentTime.getTime() - pastTime.getTime();
		  long days = diff / (1000 * 60 * 60 * 24);
		  
		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		  String updateTime = days+"天"+hours+"小时"+minutes+"分";
		  return updateTime;
		} catch (Exception e) {
			return null;
		}
		
		
	}
	public static void runTest(String packageName,Context context) throws Exception{
		
		//判断被测软件是否安装
		
		if(!Helper.havePackage(packageName, context)){
			Toast.makeText(context, "被测软件尚未安装", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!Helper.havePackage("com.WebControl.control", context)){
			Toast.makeText(context, "自动化测试尚未安装", Toast.LENGTH_SHORT).show();
			return;
		} 
		
		
		Toast.makeText(context, "正在启动案例，请耐心等待", Toast.LENGTH_SHORT).show();
		//判断WebControl是否安装
		CommandUtil commandUtil = new CommandUtil();
		commandUtil.executeCommand("am instrument --user 0 -w com.WebControl.control/android.test.InstrumentationTestRunner");
		
		//flag++;
		
		
	}
	
	//进度条加载
	public static ProgressDialog showProgress(Context context){   
		 ProgressDialog mDialog ;
	 
	        mDialog = new ProgressDialog(context);     
	        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条     
	        mDialog.setMessage("正在加载 ，请等待...");     
	        mDialog.setIndeterminate(false);//设置进度条是否为不明确     
	        mDialog.setCancelable(true);//设置进度条是否可以按退回键取消     
	        mDialog.setCanceledOnTouchOutside(false);   
	        
	        mDialog.setOnDismissListener(new OnDismissListener() {   
	               
	            @Override  
	            public void onDismiss(DialogInterface dialog) {   
	                  
	            	dialog=null;   
	            }   
	        });   
	      
	           
	    
		return mDialog;   
	}   
}
