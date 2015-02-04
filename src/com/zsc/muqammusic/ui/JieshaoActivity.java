package com.zsc.muqammusic.ui;

import com.zsc.muqammusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * ͨ�ؽ���
 * @author Lucien
 *
 */
public class JieshaoActivity extends Activity{
	
	// ���� �����¼�
	private ImageButton mBtnBack;
	private TextView mTvDes;
	private TextView mTvName;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_jieshao);
		
		mTvName = (TextView)findViewById(R.id.top_bar_text);
		mTvName.setText("����");
		mTvDes = (TextView)findViewById(R.id.tv_des);
		mTvDes.getBackground().setAlpha(150);
		mBtnBack = (ImageButton)findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JieshaoActivity.this.finish();
			}
		});
	}

}
