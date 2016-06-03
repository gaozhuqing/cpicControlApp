package com.testoauth.adapter;



import java.util.ArrayList;


import com.example.testoauth.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private ArrayList<ArrayList<String>> mChilds;
	public LeftMenuAdapter(Context context,ArrayList<ArrayList<String>> mChilds){
		this.mContext = context;
		this.mChilds = mChilds;
		mInflater = LayoutInflater.from(mContext);
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		return mChilds.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View convertView,
			ViewGroup arg3) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_menu_list_child, null);
		}
		
		
		
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return mChilds.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View convertView, ViewGroup arg3) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_menu_list_group, null);
			
		}
		
		
		return convertView;
	} 

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	

}
