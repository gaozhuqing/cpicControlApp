package com.testoauth.im.main;
import java.util.ArrayList;
import com.example.testoauth.R;
import com.testoauth.adapter.LeftMenuAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

public class MainInterfaceFragment extends Fragment {
	
	private ArrayList<ArrayList<String>> mChilds;
	private ExpandableListView mMenuListView;
	private RelativeLayout login_layout;
	private OnChangeViewListener mOnChangeViewListener;
	private MainActivity mActivity = null;
	private TextView tv;
	private LeftMenuAdapter mAdapter;
	String name = null;
	
@SuppressLint("InflateParams") @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
   View view=inflater.inflate(R.layout.main_menu,null);
   inint(view);
   data();
	mMenuListView.setAdapter(mAdapter);
	for (int i = 0; i <2; i++) {
		mMenuListView.expandGroup(i);//默认展开
	}
	mMenuListView.setDividerHeight(0);
	
	listen();
	loadData(mActivity);
	return view;
}
/*
 * 初始化控件
 */
public void inint(View view){
	mActivity =(MainActivity) this.getActivity();
	mMenuListView = (ExpandableListView)view.findViewById(R.id.menu_list);
	login_layout = (RelativeLayout) view.findViewById(R.id.login_layout);
	tv=(TextView) view.findViewById(R.id.user_name);
}
/*
 * 数据处理
 */
public void data(){
	mChilds = new ArrayList<ArrayList<String>>();
	ArrayList<String> mCh1 = new ArrayList<String>();
	ArrayList<String> mCh2 = new ArrayList<String>();
	mCh1.add("列表1");mCh1.add("列表2");mCh1.add("列表3");mCh1.add("列表4");
	mCh2.add("按钮1");mCh2.add("按钮2");mCh2.add("按钮3");
	mChilds.add(mCh1);mChilds.add(mCh2);
	mAdapter = new LeftMenuAdapter(mActivity,mChilds);
}
/*
 * 自定义监听接口
 */
    public interface OnChangeViewListener {
	  public abstract void onChangeView(int groupPosition, int childPosition);
   }
    public void setOnChangeViewListener(
		OnChangeViewListener onChangeViewListener) {
	mOnChangeViewListener = onChangeViewListener;
}
    public void listen() {
	
	setOnChangeViewListener(new OnChangeViewListener() {
		
		@Override
		public void onChangeView(int groupPosition, int childPosition) {
			if(groupPosition==0){
				if(childPosition==0){
				Intent intent=new Intent(mActivity,ActionBarActivity.class);
				startActivity(intent);
				}
				if(childPosition==1){
					
				}
			}
			
		}
	});	
	//group监听
		mMenuListView.setOnGroupClickListener(new OnGroupClickListener() {

			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		//登录监听
	login_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(name !=null){
				Toast.makeText(mActivity, "已登录", Toast.LENGTH_SHORT).show();
				}else{
					mActivity.toLogin(0x11);
				}
			}
		});
		//子项child监听
		mMenuListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (mOnChangeViewListener != null) {
					mOnChangeViewListener.onChangeView(groupPosition,
							childPosition);
				}
				return true;
			}
		});
		
}

    private void loadData(MainActivity mActivity) {
       @SuppressWarnings("static-access")
	SharedPreferences sp=mActivity.getSharedPreferences("gao", mActivity.MODE_PRIVATE);
       String userName=sp.getString("userName", null);
       String userPwd=sp.getString("userPwd", null);
       if(userName!=null&&userPwd!=null){
	        this.name=userName;
	        tv.setText(userName);
        }
 }

     public void changeText(String name){
	      this.name = name;
	      tv.setText(name);
	
}


}
