package com.testoauth.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.callbackdemo.CpicAPI;
import com.callbackdemo.GetMethod;
import com.example.testoauth.R;


import com.testoauth.im.main.ActionBarActivity;
import com.testoauth.object.TopicUser;
import com.testoauth.util.CanvasImageTask;
import com.testoauth.util.Helper;
import com.testoauth.viewMethod.XListView;
import com.testoauth.viewMethod.XListView.IXListViewListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class NetFragment extends Fragment implements IXListViewListener{
	
	private MyAdapter adapter = new MyAdapter();;
	private ActionBarActivity aBactivity;
	private ProgressDialog dialog;
	String json;
	private List<TopicUser> topicList;
	private TopicUser topicUser;
	private MyHandler myHandler;
	private int loadMoreNumber =1;
	private int refushNumber = 1;
	private XListView mListView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		aBactivity = (ActionBarActivity) getActivity();
		getTopicMessage();
		View view = inflater.inflate(R.layout.network_list, container, false);
			
		mListView = (XListView)  view.findViewById(R.id.netlist);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setXListViewListener(this);
		topicList = new ArrayList<TopicUser>();
		
		myHandler = new MyHandler();
		listen(view);
		return view;
		
		
		
	}

	// 获取论坛数据
	public void getTopicMessage() {
		GetMethod.topicMessage(cpicAPI, aBactivity,loadMoreNumber);
	}

	CpicAPI cpicAPI = new CpicAPI() {

		@Override
		public void onLoginSuccess(String arg0) {
			// TODO Auto-generated method stub
			json = arg0;
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						JSONArray jsonArray = new JSONArray(json);
						
						for (int i = 0; i < jsonArray.length(); i++) {
							topicUser = new TopicUser();
							JSONObject jsObject = jsonArray.getJSONObject(i);
							
							// topicID
							String  topicID = jsObject.getString("ID");
							if(loadMoreNumber != 1){
							for (int j = 0; j < topicList.size(); j++) {
								if(topicID.equals(topicList.get(i).getTopicId())){
									continue;
								}
							}
							}
							topicUser.setTopicId(jsObject.getString("ID"));
							
							JSONObject jsUser = jsObject.getJSONObject("User");
							
							// 用户名
							topicUser.setUserName(jsUser.getString("userName"));
							// 用户ID
							topicUser.setUserID(jsUser.getString("ID"));
							
							// 用户头像
							topicUser.setHeadImage(jsUser.getString("Avatar"));
							// nodeID
							topicUser.setNodeId(jsObject.getString("nodeID"));
							// 时间转换
							String timeDate = jsObject.getString("creatDate")
									.replace('T', ' ');
							String time = Helper.timeChangeMethod(timeDate);
							if (time != null) {
								// 发表时间
								String[] data = time.split("天");
								if (!data[0].equals("0")) {
									time = data[0] + "天";
								} else {
									data = data[1].split("小时");
									if (!data[0].equals("0")) {
										time = data[0] + "小时";
									} else {
										data = data[1].split("分");
										if (!data[0].equals("0")) {
											time = data[0] + "分钟";
										}else{
											time = "1分钟";
										}
									}
								}
							}
							topicUser.setCreatDate(time);

							// 标题
							topicUser.setTitle(jsObject.getString("title"));
							// 回复数
							if (jsObject.getString("replyCnt").equals("0")) {
								topicUser.setReplyCnt("");
							} else {
								topicUser.setReplyCnt(jsObject
										.getString("replyCnt"));
							}
							// 添加到list
							topicList.add(topicUser);
							
						}
						
						Message message = new Message();
						message.what = 0;
						myHandler.sendMessage(message);
						
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
					
					dialog.dismiss();
					
				}
			}).start();

		}

		@Override
		public void onLoginStart() {
			if(loadMoreNumber == 1 && refushNumber == 1){
				dialog = Helper.showProgress(aBactivity);
				dialog.show();
			}
		}

		@Override
		public void onLoginFailed() {

		}

		@Override
		public void OnLoginEnd() {

		}
	};

	public void listen(View view) {
		mListView.setOnItemClickListener(listener);

	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {

		}

	};

	public class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 0:
				
				if(loadMoreNumber == 1){
					mListView.setAdapter(adapter);
				}else{
					
					
					adapter.notifyDataSetChanged();
				}
				

				break;
			}
			super.handleMessage(msg);
		}
	}



	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return topicList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.network_list_item, null);
				holder.UserTv = (TextView) convertView
						.findViewById(R.id.network_user);
				holder.titleTv = (TextView) convertView
						.findViewById(R.id.network_title);
				holder.creatDateTv = (TextView) convertView
						.findViewById(R.id.network_data);
				holder.replyCntTv = (TextView) convertView
						.findViewById(R.id.network_reply);
				holder.headIv = (ImageView) convertView
						.findViewById(R.id.network_image);
				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.UserTv.setText(topicList.get(position).getUserName());
			holder.titleTv.setText(topicList.get(position).getTitle());

			holder.creatDateTv.setText(topicList.get(position).getCreatDate()
					+ "前 发布");
			holder.replyCntTv.setText(topicList.get(position).getReplyCnt());

			holder.headIv.setTag("http://139.196.177.74/Content/userAvatar/"
					+ topicList.get(position).getHeadImage());
			new CanvasImageTask().execute(holder.headIv);
			return convertView;
		}

		class Holder {
			// 作者
			TextView UserTv;
			// 发帖时间
			TextView creatDateTv;
			// 回复数
			TextView replyCntTv;
			// 标题
			TextView titleTv;
			// 头像
			ImageView headIv;
		}
	}

	/** 停止刷新， */
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		refushNumber ++;
		getTopicMessage();
		onLoad();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		loadMoreNumber++;
		getTopicMessage();
		onLoad();
	}

}
