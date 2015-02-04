package com.zsc.muqammusic.ui;

import com.zsc.muqammusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * ��������
 * @author Lucien
 *
 */
public class StartPageActivity extends Activity{
	
	private final int SPLASH_DISPLAY_LENGHT = 3000; // �ӳ�3��
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.start_page);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
			
				Intent intent = new Intent();
				intent.setClass(StartPageActivity.this, StartActivity.class);
				StartPageActivity.this.startActivity(intent);
				StartPageActivity.this.finish();

			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
