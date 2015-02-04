package com.zsc.muqammusic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.data.InstruConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InstruGirdActivity extends Activity implements OnItemClickListener{

	private GridView gridView;
	private ImageButton mBtnBack;
	private TextView mTvName;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_instrugird);
		
		gridView=(GridView) findViewById(R.id.gridView);
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, getData(), R.layout.gird_item, new String[]{"pic","name"}, new int[]{R.id.pic,R.id.name});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        
        mTvName = (TextView)findViewById(R.id.top_bar_text);
        mTvName.setText("ÀÖÆ÷");
		mBtnBack = (ImageButton) findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private List<Map<String, Object>> getData() {
		
		for (int i = 0; i < InstruConst.DRAWABLES.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", InstruConst.DRAWABLES[i]);
			map.put("name", InstruConst.ICONNAMES[i]);
            dataList.add(map);
		}
		Log.i("Main", "size="+dataList.size());
		return dataList;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(this," "+ arg2 , Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.putExtra("index", arg2);
		intent.setClass(InstruGirdActivity.this, InstruActivity.class);
		startActivity(intent);
	}

}
