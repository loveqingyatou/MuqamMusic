package com.zsc.muqammusic.ui;

import com.zsc.muqammusic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通关界面
 * @author Lucien
 *
 */
public class AllPassView extends Activity{
	
	// 返回 按键事件
	private ImageButton mBtnBack;
	private TextView mTvName;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view2);
		
		LinearLayout view = (LinearLayout)findViewById(R.id.passllt);
		view.getBackground().setAlpha(150);
		
		mTvName = (TextView)findViewById(R.id.top_bar_text);
		mTvName.setText("通关");
		
		mBtnBack = (ImageButton)findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AllPassView.this.finish();
			}
		});
	}

}
