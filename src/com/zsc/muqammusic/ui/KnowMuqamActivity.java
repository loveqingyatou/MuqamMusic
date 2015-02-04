package com.zsc.muqammusic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.data.Const;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 通关界面
 * @author Lucien
 *
 */
public class KnowMuqamActivity extends Activity{
	
	// 返回 按键事件
	private ImageButton mBtnBack;
	private ImageButton mBtnMuqam;
	private ImageButton mBtnMuqamJieshao;
	private ImageButton mBtnMuqamYueqi;
	private TextView mTvName;
	
	private List<Map<String, Object>> mData;
	private ListView listView;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_know_muqam);
		
		mData = getData();
		listView = (ListView) findViewById(R.id.musicListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.getBackground().setAlpha(150);
		MyAdapter adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
		
		mTvName = (TextView)findViewById(R.id.top_bar_text);
		mTvName.setText("木卡姆");
		mBtnBack = (ImageButton)findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KnowMuqamActivity.this.finish();
			}
		});
		
		mBtnMuqam = (ImageButton)findViewById(R.id.btn_muqam);
		mBtnMuqamJieshao = (ImageButton)findViewById(R.id.btn_muqam_jieshao);
		mBtnMuqamYueqi = (ImageButton)findViewById(R.id.btn_muqam_yueqi);	
		mBtnMuqamJieshao.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					mBtnMuqam.setBackgroundResource(R.drawable.muqam_sel);
					mBtnMuqamJieshao.setBackgroundResource(R.drawable.muqam_jieshao_sel);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					mBtnMuqam.setBackgroundResource(R.drawable.muqam);
					mBtnMuqamJieshao.setBackgroundResource(R.drawable.muqam_jieshao);
				}
				return false;
			}
		});
		mBtnMuqamJieshao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(KnowMuqamActivity.this, JieshaoActivity.class);
				KnowMuqamActivity.this.startActivity(intent);
			}
		});
		mBtnMuqamYueqi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(KnowMuqamActivity.this, InstruGirdActivity.class);
				KnowMuqamActivity.this.startActivity(intent);
			}
		});
		mBtnMuqamYueqi.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mBtnMuqam.setBackgroundResource(R.drawable.muqam_sel);
					mBtnMuqamYueqi.setBackgroundResource(R.drawable.muqam_yueqi_sel);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mBtnMuqam.setBackgroundResource(R.drawable.muqam);
					mBtnMuqamYueqi.setBackgroundResource(R.drawable.muqam_yueqi);
				}
				return false;
			}
		});
	}
	
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(int i=0;i<Const.MUSIC_INFO.length;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", Const.MUSIC_INFO[i][1]);
			map.put("info", Const.MUSIC_INFO[i][2] + " " + Const.MUSIC_INFO[i][3]);
			map.put("img", R.drawable.ic_launcher);
			list.add(map);
		}
		return list;
	}
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public Button viewBtn;
	}
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder.img = (ImageView)convertView.findViewById(R.id.img);
				holder.title = (TextView)convertView.findViewById(R.id.title);
				holder.info = (TextView)convertView.findViewById(R.id.info);
				holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
			holder.title.setText((String)mData.get(position).get("title"));
			holder.info.setText((String)mData.get(position).get("info"));
			final String msg = (String)mData.get(position).get("title");
			final int index = position;
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//showInfo(msg);
					Intent intent = new Intent();
					intent.setClass(KnowMuqamActivity.this, PlayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("index", index);
					intent.putExtras(bundle);
					KnowMuqamActivity.this.startActivity(intent);
				}
			});			
			return convertView;
		}
	}
	
	/**
	 * listview中点击按键弹出对话框
	 */
	public void showInfo(String msg){
		new AlertDialog.Builder(this)
		.setTitle("我的listview")
		.setMessage(msg)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();		
	}

}
