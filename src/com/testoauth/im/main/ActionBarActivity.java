package com.testoauth.im.main;

import java.util.ArrayList;
import com.example.testoauth.R;
import com.testoauth.activity.LocalFragment;
import com.testoauth.activity.NetFragment;
import com.testoauth.adapter.FragmentAdapter;
import com.testoauth.object.ViewTool;
import com.testoauth.viewMethod.actionMethod;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



 public class ActionBarActivity extends FragmentActivity{
	
		private ViewPager mPager;  
	    private ArrayList<Fragment> fragmentList;  
	    private ImageView image;  
	    private TextView view1, view2; 
	    private LinearLayout line1,line2;
	    private int currIndex;//当前页卡编号  
	    private int screenWidth;
	      
	    @SuppressLint("NewApi") @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.actionbar_main);  
	        InitTextView();  
	        InitViewPager(); 
	        initTabLineWidth();
	    }  
	      
	      
	    /* 
	     * 初始化标签名 
	     */  
	    public void InitTextView(){  
	        view1 = (TextView)findViewById(R.id.tv_guid1);  
	        view2 = (TextView)findViewById(R.id.tv_guid2); 
	        line1=(LinearLayout) findViewById(R.id.liner1);
	        line2=(LinearLayout) findViewById(R.id.liner2);
	        image = (ImageView)findViewById(R.id.cursor); 
	        mPager = (ViewPager)findViewById(R.id.viewpager); 
	        actionMethod aM=new actionMethod();
		    ViewTool vt = aM.actionTool(ActionBarActivity.this);
		    
		    vt.getIv().setBackgroundResource(R.drawable.button_selector_back);
		    vt.getTv().setText("任务");
		    vt.getTv().setTextSize(18);
		    vt.getRl().setBackgroundColor(Color.RED);
		    vt.getIv().setOnClickListener(listen);
		    
	        fragmentList = new ArrayList<Fragment>(); 
	        line1.setOnClickListener(new txListener(0));  
	        line2.setOnClickListener(new txListener(1));
	    }  
	    /** 
	     * 设置滑动条的宽度为屏幕的1/2(根据Tab的个数而定) 
	     */  
	    private void initTabLineWidth() {  
	        DisplayMetrics dpMetrics = new DisplayMetrics(); 
	        getWindow().getWindowManager().getDefaultDisplay()  
	                .getMetrics(dpMetrics);  
	        screenWidth = dpMetrics.widthPixels; 

	        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image  
	                .getLayoutParams();

	        lp.width = screenWidth / 2;
	        image.setLayoutParams(lp);
	        
	    }  
	   
	    //返回监听
	    private OnClickListener listen=new  OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	finish();
	        }
	    };   
	    
	    //textView监听
	    public class txListener implements OnClickListener{  
	        private int index=0;  
	          
	        public txListener(int i) {  
	            index =i;  
	        }  
	      @Override
			public void onClick(View v) {
				colorMeod();
				switch(index){
				case 0:
					view1.setTextColor(Color.GREEN);
					break;
				case 1:
					view2.setTextColor(Color.GREEN);
					break;
				}
				 mPager.setCurrentItem(index); 
				 currIndex=index;	
			}  
	    }  
	   
	    /* 
	     * 初始化ViewPager 
	     */  
	  public void InitViewPager(){  
	     
	        LocalFragment tab1Fg= new LocalFragment();  
	        NetFragment tab2Fg =new NetFragment(); 
	        
	        fragmentList.add(tab1Fg);  
	        fragmentList.add(tab2Fg);    
	          
	        //给ViewPager设置适配器  
	        mPager.setAdapter(new FragmentAdapter(this.getSupportFragmentManager(), fragmentList));  
	        mPager.setCurrentItem(0);//设置当前显示标签页为第一页  
	        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  
	    }  
	  
	    
	  public class MyOnPageChangeListener implements OnPageChangeListener{   
	        @Override  
	        public void onPageScrolled(int position, float offset, int offsetPixels) {  
	        	LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)  image 
                        .getLayoutParams();  
	        	 if (currIndex == 0 && position == 0)// 0->1  
	                {  
	                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currIndex  
	                            * (screenWidth / 2)); 
	                 
	  
	                } else if (currIndex == 1 && position == 0) // 1->0 
	                { lp.leftMargin = (int) (-(1 - offset)  
                            * (screenWidth * 1.0 / 2) + currIndex  
                            * (screenWidth / 2));  
	                }
	                image.setLayoutParams(lp);  
	        }  
	          
	        @Override  
	        public void onPageScrollStateChanged(int arg0) {  
	           
	              
	        }  
	          
	        
	        public void onPageSelected(int position) {
	        	colorMeod();
	        	switch(position){
	        	case 0:
	        		view1.setTextColor(Color.GREEN);
	        		break;
	        	case 1:
	        		view2.setTextColor(Color.GREEN);
	        		break;
	        	}
	        	currIndex=position;
			
	    }

			
	      
	    }   
	  
	  public void colorMeod() {	
		  view1.setTextColor(Color.WHITE);
		  view2.setTextColor(Color.WHITE);
		}  
	 
}
