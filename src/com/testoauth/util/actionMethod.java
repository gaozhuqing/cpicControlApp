package com.testoauth.util;

import com.example.testoauth.R;
import com.testoauth.activity.dataActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class actionMethod {
	

public ViewTool actionTool(Activity activity){
	
	ViewTool vt=new ViewTool();
	Log.i("123", activity.getClass().toString());
	 ActionBar actionBar=activity.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
	    actionBar.setCustomView(R.layout.actionbar_crime_fragment);
	    View actionView = actionBar.getCustomView();
	    RelativeLayout    rl= (RelativeLayout) actionView.findViewById(R.id.relative);
	    ImageView  iv=(ImageView) actionView.findViewById(R.id.actionBarUp);
	    TextView tv=(TextView) actionView.findViewById(R.id.actionBarTitle);
	    vt.setRl(rl);
	    vt.setIv(iv);
	    vt.setTv(tv);
		return vt;
	}
	
}
