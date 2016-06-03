package com.testoauth.viewMethod;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testoauth.R;
import com.testoauth.object.ViewTool;

public class dataActivityMethod {

	public ViewTool actionTool(Activity activity){
	ViewTool vt=new ViewTool();
	 ActionBar actionBar=activity.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);	
	    actionBar.setCustomView(R.layout.data_actionbar);
	    View actionView = actionBar.getCustomView();
	    RelativeLayout    rl= (RelativeLayout) actionView.findViewById(R.id.relative);
	    ImageView  iv=(ImageView) actionView.findViewById(R.id.actionBarUp);
	    TextView tv=(TextView) actionView.findViewById(R.id.actionBarTitle);
	    Button bt=(Button) actionView.findViewById(R.id.moreBtn);
	    vt.setRl(rl);
	    vt.setIv(iv);
	    vt.setTv(tv);
	    vt.setBt(bt);
		return vt;
	}
}
