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
 * ��������
 * @author Lucien
 *
 */
public class StartActivity extends Activity{
	
	// Play �����¼�
	private ImageButton mBtnPlayStart;
	
	// �˽�ľ��ķ �����¼�
	private ImageButton mBtnKnowMquam;
	
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_start);
		
		// ��ʼ���ؼ�
		mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
		mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				StartActivity.this.startActivity(intent);
				//StartActivity.this.finish();
				//����¸����
				//Util.startActivity(StartActivity.this, MainActivity.class);
			}
		});
		
		// ��ʼ���ؼ�
		mBtnKnowMquam = (ImageButton)findViewById(R.id.know_muqam_btn);
		mBtnKnowMquam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//�����˽�ľ��ķ����
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, KnowMuqamActivity.class);
				StartActivity.this.startActivity(intent);
				//Util.startActivity(StartActivity.this, KnowMuqamActivity.class);
			}
		});
	}
	
	/** 
	 * �˵������ؼ���Ӧ 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //����˫���˳�����  
	       }  
	    return false;  
	}  
	/** 
	 * ˫���˳����� 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // ׼���˳�  
	        Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // ȡ���˳�  
	            }  
	        }, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}

}
