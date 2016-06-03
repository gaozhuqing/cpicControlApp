package com.testoauth.im.main;

import com.example.testoauth.R;
import com.slidingmenu.lib.SlidingMenu;
import com.testoauth.activity.LoginActivity;
import com.testoauth.object.ViewTool;
import com.testoauth.viewMethod.actionMethod;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends FragmentActivity  {

private MainInterfaceFragment mInterfaceFragment=null;
private MainContentMenuFragment mContentMenuFragment=null;
private SlidingMenu menu;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.sliding_menu_content);
		actionMethod aM=new actionMethod();
	    ViewTool vt = aM.actionTool(MainActivity.this);
	    vt.getRl().setBackgroundColor(Color.RED);
	    vt.getTv().setText("移动自动化");
	    vt.getIv().setBackgroundResource(R.drawable.button_selector_menu);
	    mContentMenuFragment=new MainContentMenuFragment();
	 // 主视图的Fragment添加
	 		getSupportFragmentManager().beginTransaction()
	 				.replace(R.id.content_frame, mContentMenuFragment).commit();
	 		
	 	mInterfaceFragment=new MainInterfaceFragment();	 
	    menu=new SlidingMenu(this);
	    menu.setMode(SlidingMenu.LEFT);
	  	menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		  
		// menu视图的Fragment添加
				menu.setMenu(R.layout.sliding_menu_menu);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.menu_frame, mInterfaceFragment).commit();
		   
		   
				vt.getIv().setOnClickListener(upButtonListen);
}
//菜单栏图标点击
private OnClickListener upButtonListen=new  OnClickListener() {
    @Override
    public void onClick(View v) {
    	if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			menu.showMenu();
		}
    }
};

public void toLogin(int requestCode){
	Intent intent=new Intent(MainActivity.this,LoginActivity.class);
	startActivityForResult(intent, requestCode);
}


@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if(data!=null){
	String userName=data.getExtras().getString("userName");
	
	if(requestCode == 0x11 && resultCode == 0x12){
		
		mInterfaceFragment.changeText(userName);
		
	}
	}
}
@Override
	public void onBackPressed() {
	if(mContentMenuFragment!=null && mContentMenuFragment.canGoBack()){
		mContentMenuFragment.goBack();
	}
//		super.onBackPressed();
	}
}
