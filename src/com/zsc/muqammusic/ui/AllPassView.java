package com.zsc.muqammusic.ui;

import com.zsc.muqammusic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ͨ�ؽ���
 * @author Lucien
 *
 */
public class AllPassView extends Activity{
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view);
		
		//�������ϽǵĽ�Ұ�ť
		FrameLayout view = (FrameLayout)findViewById(R.id.layout_bar_coin);
		view.setVisibility(View.INVISIBLE);
	}

}
