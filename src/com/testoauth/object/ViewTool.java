package com.testoauth.object;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewTool {
	private RelativeLayout rl;
	private ImageView iv;
	private TextView tv;
	private Button bt;
	public ViewTool() {
		
	}
	
	public Button getBt() {
		return bt;
	}

	public void setBt(Button bt) {
		this.bt = bt;
	}

	public RelativeLayout getRl() {
		return rl;
	}
	public void setRl(RelativeLayout rl) {
		this.rl = rl;
	}
	public ImageView getIv() {
		return iv;
	}
	public void setIv(ImageView iv) {
		this.iv = iv;
	}
	public TextView getTv() {
		return tv;
	}
	public void setTv(TextView tv) {
		this.tv = tv;
	}
	
}
