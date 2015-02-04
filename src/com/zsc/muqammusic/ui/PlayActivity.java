package com.zsc.muqammusic.ui;


import java.util.ArrayList;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.data.Const;
import com.zsc.muqammusic.data.Constant;
import com.zsc.muqammusic.model.Music;
import com.zsc.muqammusic.service.MusicPlayerService;
import com.zsc.muqammusic.util.StrTime;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 通关界面
 * @author Lucien
 *
 */
public class PlayActivity extends Activity implements OnClickListener{
	
	private boolean prepared = false;
	// 返回 按键事件
	private ImageButton mBtnBack;
	
	private SeekBar seekBar;
	private ImageView btplay;
	private ImageView btprevious;
	private ImageView btnext;
	private ImageView musicAlbum;
	private TextView tvsongname, tvsinger, tvcurrent, tvdurction;
	private MusicinfoRec MusicinfoRec;
	
	public static MediaPlayer mediaPlayer = new MediaPlayer();
	
	int progress = 0;
	public Context context;
	int position, nowplaymode;// 当前播放歌曲下标 播放模式
	public static int current = 0;
	int totalms = 1;
	Music music;
	
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_play);
		
		startService(new Intent(this, MusicPlayerService.class));
		context = this;
		
		Intent intent = this.getIntent();
		Bundle bd = intent.getExtras();
		current = bd.getInt("index");
		System.out.println(current);

		mBtnBack = (ImageButton)findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PlayActivity.this.finish();
			}
		});
		
		
		btplay = (ImageView) findViewById(R.id.iv_player_play_pause);
		btprevious = (ImageView) findViewById(R.id.iv_player_prev);
		btnext = (ImageView) findViewById(R.id.iv_player_next);
		musicAlbum = (ImageView) findViewById(R.id.im_player_album);
		
		seekBar = (SeekBar) findViewById(R.id.sb_player_progress);
		tvcurrent = (TextView) findViewById(R.id.tv_player_played_time);
		tvdurction = (TextView) findViewById(R.id.tv_player_total_time);
		tvsongname = (TextView) findViewById(R.id.tv_songname);
		tvsinger = (TextView) findViewById(R.id.tv_singer_name);
		
		tvsongname.setText(Const.MUSIC_INFO[current][1]);
		tvsinger.setText(Const.MUSIC_INFO[current][3]);
		tvdurction.setText(Const.MUSIC_INFO[current][4]);
		musicAlbum.setImageResource(Const.MUSIC_IMG[current]);
		
		MusicinfoRec = new MusicinfoRec();
		broadcastIntent = new Intent();
		/* 初始化Views 的视图监听 */
		initViewsListener();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		// 启动的时候更新 页面当前播放信息
		//sendBroadcast(new Intent(Constant.ACTION_UPDATE_ALL));
		//btplay.setImageResource(R.drawable.bg_button_player_pause);
		//broadcastIntent.setAction(Constant.ACTION_PLAY);
		//isplaying = true;
		//sendBroadcast(broadcastIntent);
		nowplaymode = 2;//单曲循环		
	}

	Thread myThread;

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_UPDATE);
		registerReceiver(MusicinfoRec, filter);

		isrunable = true;
		// 启动更新进度条的线程
		myThread = new ProgeressThread();
		myThread.start();
		//btplay.performClick();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(MusicinfoRec);
		if(mediaPlayer != null){
			if(prepared){
				mediaPlayer.stop();
			}
		}
	}

	@Override
	protected void onStop() {
		isrunable = false;
		super.onStop();
	}
	
	
	private void initViewsListener() {
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progres,
					boolean fromUser) {
				if (fromUser == true && Math.abs(progres - progress) >= 5) {
					progress = progres;
					broadcastIntent = new Intent(Constant.ACTION_SEEK);
					broadcastIntent.putExtra("seekcurr", progress);// 讲拖动的进度传进Service
					sendBroadcast(broadcastIntent);
					seekBar.setProgress(progress);
				}
			}
		});
		
		btplay.setOnClickListener(this);
		btprevious.setOnClickListener(this);
		btnext.setOnClickListener(this);
	}
	
	// ---------------------------click
	// 事件----------------------------------------------------
    boolean isplaying = false;
	Intent broadcastIntent;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_player_play_pause:
			prepared = true;
			if (!isplaying) {// 如果不是播放
				btplay.setImageResource(R.drawable.bg_button_player_pause);
				broadcastIntent.setAction(Constant.ACTION_PLAY);
				isplaying = true;
			} else {
				broadcastIntent.setAction(Constant.ACTION_PAUSE);
				isplaying = false;
				btplay.setImageResource(R.drawable.bg_button_player_play);
			}
			sendBroadcast(broadcastIntent);
			break;
		case R.id.iv_player_prev:
			btplay.setImageResource(R.drawable.bg_button_player_pause);
			isplaying = true;
			broadcastIntent.setAction(Constant.ACTION_PREVIOUS);
			sendBroadcast(broadcastIntent);
			break;
		case R.id.iv_player_next:
			btplay.setImageResource(R.drawable.bg_button_player_pause);
			isplaying = true;
			broadcastIntent.setAction(Constant.ACTION_NEXT);
			sendBroadcast(broadcastIntent);
			break;
		}
	}

	Handler nameshandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 20:
				try {
					int progress = curms * 100 / totalms;
					// 设置当前进度
					seekBar.setProgress(progress);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tvcurrent.setText(StrTime.gettim(curms));
				//Log.i("SMZ", "SSSSS" + progress);
				break;
			}

		}
	};
	
	
	boolean isrunable = true;
	int curms;
	//更新进度条的线程
	class ProgeressThread extends Thread {
		@Override
		public void run() {
			while (isrunable) {
				if (mediaPlayer != null
						&& mediaPlayer.isPlaying()) {
					curms = mediaPlayer.getCurrentPosition();
					nameshandler.sendEmptyMessage(20);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			super.run();
		}
	}
	
	
	
	
	
	private class MusicinfoRec extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
	
			if (intent.getAction().equals(Constant.ACTION_UPDATE)) {
				position = intent.getIntExtra("position", 0);
				music = (Music) intent.getSerializableExtra("music");
				totalms = intent.getIntExtra("totalms", 288888);// 总时长
				Log.i("SMZ", totalms + "");
				try {
					tvsongname.setText(music.getMusicName());
					tvsinger.setText(music.getSinger());
					//tvdurction.setText(StrTime.getTime(music.getTime()));
					tvdurction.setText(music.getTime());
					musicAlbum.setImageResource(music.getAlbumPath());
					
					if (mediaPlayer.isPlaying()) {
						btplay.setImageResource(R.drawable.bg_button_player_pause);
						isplaying = true;
					} else {
						isplaying = false;
						btplay.setImageResource(R.drawable.bg_button_player_play);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<Music> getMusics() {
		ArrayList<Music> musics = new ArrayList<Music>();
		Music music;
		
		for(int i=0; i<Const.MUSIC_INFO.length; i++)
		{
			music = new Music();
			music.setId(i);
			music.setMusicName(Const.MUSIC_INFO[i][1]);
			music.setSavePath(Const.MUSIC_INFO[i][0]);
			music.setSinger(Const.MUSIC_INFO[i][3]);
			music.setTime(Const.MUSIC_INFO[i][4]);
			music.setAlbumPath(Const.MUSIC_IMG[i]);
			musics.add(music);
			//System.out.println(music.getSavePath()+" "+music.getMusicName());
		}
		//for(int i=0;i<musics.size();i++){
			//System.out.println(musics.get(i).getSavePath()+" "+musics.get(i).getMusicName());
		//}
		//System.out.println(musics.size());
		return musics;
	}

}
