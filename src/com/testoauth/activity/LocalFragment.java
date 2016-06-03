package com.testoauth.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.testoauth.R;
import com.testoauth.im.main.ActionBarActivity;
import com.testoauth.object.TaskCase;
import com.testoauth.util.FileScan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LocalFragment extends Fragment {
	private ListView listview;
	private MyAdapter adapter;
	private ArrayList<TaskCase> list;//项目对象列表
	private ActionBarActivity aBactivity;

	private ArrayList<String> projectNameList;//项目名称
//	private ArrayList<String> nameList;//案例名称
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.local_list,container, false);
	aBactivity=(ActionBarActivity) getActivity();
	datasource();
	listview=(ListView) view.findViewById(R.id.locallist);
	listview.setAdapter(adapter);
	listview.setDividerHeight(0);
	listen(view);
	return view;
}
private void datasource() {
   list = new ArrayList<TaskCase>();
   
    try {
		projectNameList = FileScan.getFileList("");
		for(int i=0; i<projectNameList.size();i++){
			 String taskName = projectNameList.get(i);
			 List xmlList = FileScan.getFileXml(taskName);
			  if(xmlList.size()!=0){
				  TaskCase taskCase=new TaskCase();
				  taskCase.setTaseName(projectNameList.get(i));
				  list.add(taskCase);
			  }
			
		  }
	} catch (Exception e) {
		e.printStackTrace();
	}
  
	adapter=new MyAdapter();
}
public void listen(View view) {
	listview.setOnItemClickListener(listener);
	
}
private OnItemClickListener listener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long arg3) {
		
		Intent intent=new Intent(aBactivity,dataActivity.class);
		String  projectName=list.get(position).getTaseName();		
		intent.putExtra("项目名", projectName);
		startActivity(intent);
	
	}
	
};

class MyAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView=LayoutInflater.from(getActivity()).inflate(R.layout.local_list_item, null);		 
			viewHolder.tv=(TextView) convertView.findViewById(R.id.locallistitem);
			viewHolder.iv=(ImageView) convertView.findViewById(R.id.localimage);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tv.setText(list.get(position).getTaseName());
		viewHolder.iv.setImageResource(R.drawable.recommend);
		return convertView;
	}
	class ViewHolder{
		TextView tv;
		ImageView iv;
	}
}

}
