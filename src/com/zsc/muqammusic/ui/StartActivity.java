package com.zsc.muqammusic.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 启动界面
 * @author Lucien
 *
 */
public class StartActivity extends Activity{
	
	// Play 按键事件
	private ImageButton mBtnPlayStart;
	
	// 了解木卡姆 按键事件
	private ImageButton mBtnKnowMquam;
	
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_start);
		
		// 初始化控件
		mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
		mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				StartActivity.this.startActivity(intent);
				//StartActivity.this.finish();
				//进入猜歌界面
				//Util.startActivity(StartActivity.this, MainActivity.class);
			}
		});
		
		// 初始化控件
		mBtnKnowMquam = (ImageButton)findViewById(R.id.know_muqam_btn);
		mBtnKnowMquam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//进入了解木卡姆界面
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, KnowMuqamActivity.class);
				StartActivity.this.startActivity(intent);
				//Util.startActivity(StartActivity.this, KnowMuqamActivity.class);
			}
		});
	}
	
	/** 
	 * 菜单、返回键响应 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	/** 
	 * 双击退出函数 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出  
	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}

}
