package com.testoauth.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.testoauth.R;
import com.testoauth.activity.dataActivity.MyAdapter.Holder;
import com.testoauth.object.TestCase;
import com.testoauth.object.ViewTool;
import com.testoauth.util.CreateFiles;
import com.testoauth.util.FileScan;
import com.testoauth.util.FindXML;
import com.testoauth.util.Helper;
import com.testoauth.viewMethod.dataActivityMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class dataActivity extends Activity {
	private ListView listview;
	private MyAdapter adapter;
	private List<String> caseNameList;//案例列表
	private boolean cbVisible;//checkbox隐藏标志位
	private List<Boolean> caseCheck; //勾选项标志位
	private Button bt;
	private String path = "/sdcard";
	private String projectName;
	private FindXML find = new FindXML();
	private Context context;
	private MyBroadcastReceiver mybr;
	private ArrayList<TestCase> testCaseList;//批量执行案例列表

	

  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.data_list);
	init();
	brodcast();
	
	listview.setAdapter(adapter);
	listview.setDividerHeight(0);//item无分割线
	listview.setOnItemClickListener(itemListen);
	listview.setOnItemLongClickListener(itemLontListen);
}
  //注册广播
private void brodcast() {
	mybr = new MyBroadcastReceiver();
	IntentFilter filter = new IntentFilter();
	filter.addAction("com.WebControl.receiver");
	this.registerReceiver(mybr, filter);
	
}
//初始化
private void init() {
	
	Intent intent=getIntent();
	 projectName = intent.getStringExtra("项目名");
	ArrayList<String> fileXmlList = null;
	try {
		fileXmlList = FileScan.getFileXml(projectName);
	} catch (Exception e) {
	
		e.printStackTrace();
	}	
	
	
	context = this;
	listview=(ListView) findViewById(R.id.data_list);
	dataActivityMethod aM=new dataActivityMethod();
    ViewTool vt = aM.actionTool(dataActivity.this);
    
    vt.getIv().setBackgroundResource(R.drawable.button_selector_back);
    vt.getTv().setText("结果");
    vt.getTv().setTextSize(16);
    vt.getRl().setBackgroundColor(Color.RED);
    vt.getIv().setOnClickListener(listen);
    vt.getBt().setBackgroundResource(R.drawable.button_selector_more);
    vt.getBt().setOnClickListener(btListen);
	
	bt=vt.getBt();
	caseNameList = new ArrayList<String>();
	caseCheck = new ArrayList<Boolean>();
	testCaseList= new ArrayList<TestCase>();
	
	
	
	for(int i=0;i<fileXmlList.size();i++){
		caseNameList.add(fileXmlList.get(i));
	}
	
	for (int i = 0; i < caseNameList.size(); i++) {
		caseCheck.add(false);
	}
	
	cbVisible = false;
	
	adapter=new MyAdapter();
}

//返回监听
private OnClickListener listen=new  OnClickListener() {
    @Override
    public void onClick(View v) {
    	finish();
    }
};  
//菜单按钮button监听
private OnClickListener btListen=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		openOptionsMenu(bt);
		
	}
};

//item监听
private OnItemClickListener itemListen=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long arg0) {

		
		if(!cbVisible){
			
			//调试执行
			final int  m = position;
				 AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("是否开始调试");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						try {
							
							runTestCase(m);
							
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			
			
		}else{
			Holder holder = (Holder) view.getTag();
			boolean flag = holder.isCheck.isChecked();
			caseCheck.set(position, !flag);
			holder.isCheck.toggle();
			  
		}
	}
	
};

//执行案例
public void runTestCase( int m) throws Exception{
	
	FindXML find = new FindXML();
	TestCase testcase = find.findCase(projectName +"/"+caseNameList.get(m)+".xml");
	CreateFiles cf = new CreateFiles();
	cf.CreateText("test.xml");
	cf.print(testcase.getContent(), "test.xml");	
	//Helper.runTest("com.cpic.cmp",context);

}

//长按实现多选
private OnItemLongClickListener itemLontListen =  new OnItemLongClickListener() {

	@Override
	public boolean onItemLongClick(AdapterView<?> madapter, View view, int position, long arg0) {
		
		Holder holder = (Holder) view.getTag();
		cbVisible = true;
		caseCheck.set(position, true);
		
		adapter.notifyDataSetChanged();
		return true;
	}
};

public class MyAdapter extends BaseAdapter{
	
	
	
	@Override
	public int getCount() {
		return caseNameList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		
		if(convertView==null){
			holder=new Holder();
			convertView=LayoutInflater.from(dataActivity.this).inflate(R.layout.data_list_item, null);
			holder.caseName=(TextView) convertView.findViewById(R.id.netlistitem);
			holder.isCheck=(CheckBox) convertView.findViewById(R.id.checkbox);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.caseName.setText(caseNameList.get(position));
		
		if(!cbVisible){
			holder.isCheck.setVisibility(View.INVISIBLE);
		}else{
			holder.isCheck.setVisibility(View.VISIBLE);
		}
		
		if(!caseCheck.get(position)){
			holder.isCheck.setChecked(false);
		}else{
			holder.isCheck.setChecked(true);
		}
		
		return convertView;
	}
	
	public  class Holder{
		TextView caseName;
		CheckBox isCheck;
	}
}


public void openOptionsMenu(View view) {
	PopupMenu popmenu=new PopupMenu(this, view);
	popmenu.getMenuInflater().inflate(R.menu.data_activity, popmenu.getMenu());
	popmenu.show();
	popmenu.setOnMenuItemClickListener(menuItemListen);
}

private  OnMenuItemClickListener menuItemListen = new OnMenuItemClickListener() {
	
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		String itemName=  menuItem.getTitle().toString();
		
		switch(itemName){
		case "全选":
			MenuFirstItem();
			break;
			
		case "批量执行":
			try {
				if(!caseCheck.contains(true)){
					Toast.makeText(context, "未勾选案例，请勾选后再执行", Toast.LENGTH_SHORT).show();
				}else{
				  MenuSecondItem();
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			break;
			
		default:		
		break;
		}
		
		return true;
	}

	
};

//全选监听
public void MenuFirstItem() {
	
		cbVisible = true;
		boolean checkAll = false;
		
		for (int i = 0; i < caseCheck.size(); i++) {
			if(caseCheck.get(i) == false){
				checkAll = true;
				break;
			}
		}
		
		if(checkAll){
			for (int i = 0; i < caseCheck.size(); i++) {
				caseCheck.set(i, true);
			}
			adapter.notifyDataSetChanged();
		}else{
			for (int i = 0; i < caseCheck.size(); i++) {
				caseCheck.set(i, false);
			}
			adapter.notifyDataSetChanged();
		}	
	
}

//跑批监听
public void MenuSecondItem() throws Exception {
	FindXML find = new FindXML();
	
	for(int i=0;i<caseCheck.size();i++){
		if(caseCheck.get(i) == true){
			TestCase testcase=find.findCase(projectName +"/"+caseNameList.get(i)+".xml");
			Toast.makeText(context, "勾选案例:"+testcase.getCaseName(),Toast.LENGTH_SHORT).show();
			testCaseList.add(testcase);
		}
	}
	
	try {
		CreateFiles cf = new CreateFiles();
		cf.CreateText("test.xml");
		cf.print(testCaseList.get(0).getContent(), "test.xml");
		Helper.runTest("com.cpic.cmp",context);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
	
}


@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode==KeyEvent.KEYCODE_BACK){
		
		if(!cbVisible){
			finish();
		}else{
			cbVisible = false;
			for (int i = 0; i < caseCheck.size(); i++) {
				caseCheck.set(i, false);
			}
			
			
			adapter.notifyDataSetChanged();
		}
		
		
		return false;
	}else{
		return super.onKeyDown(keyCode, event);
	}
}


//广播接收
class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		TestCase runTest = new TestCase();
		if ("true".equals(intent.getExtras().getString("msg"))) {

			File file = new File(path + "/test/", "test_result.xml");
			
			try {
				 if(file.exists()){
				
					 InputStream in = new FileInputStream(file);
					 runTest.setResult(find.getString(in));
					 String id = FindXML.findID(runTest.getResult());
					 
					 for (TestCase test :testCaseList ) {
							if (test.getCaseID().equals(id)) {
								testCaseList.remove(test);
								break;
							}
						}
					 //刷新界面				
				   }
				 
				 if(testCaseList.size()>0){//再次执行
				 ArrayList<TestCase> test_caseList=new ArrayList<TestCase>();
				 test_caseList.clear();
				 test_caseList.addAll(testCaseList);
				 FindXML find = new FindXML();
				
					TestCase testcase = find.findCase(projectName +"/"+test_caseList.get(0).getCaseName()+".xml");
					CreateFiles cf = new CreateFiles();
					cf.CreateText("test.xml");
					
					cf.print(testcase.getContent(), "test.xml");	
					Helper.runTest("com.cpic.cmp",context);
				 }else{
					 testCaseList.clear();
					 runTest=null;
					 Toast.makeText(context, "执行全部完成", Toast.LENGTH_SHORT).show();
				 }
				 
			}catch(Exception e){
				Log.i("123", e.getMessage());
			}
		
	}
	
  }
}

}
