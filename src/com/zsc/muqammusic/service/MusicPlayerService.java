package com.zsc.muqammusic.service;

import java.util.ArrayList;

import com.zsc.muqammusic.data.Constant;
import com.zsc.muqammusic.model.Music;
import com.zsc.muqammusic.ui.PlayActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MusicPlayerService extends Service {

	public class PhoneStatRec extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			TelephonyManager mTelManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			boolean isringpause = false;
			switch (mTelManager.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// ����
				if (mPlayer != null && mPlayer.isPlaying()) {
					mPlayer.pause();
					isringpause = true;
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// ͨ��
				if (mPlayer != null && mPlayer.isPlaying()) {
					mPlayer.pause();
					isringpause = true;
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:// ͨ������
				if (mPlayer != null && isringpause == true) {
					mPlayer.start();
					isringpause = false;
				}
				break;
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private MyReciever mReceiver;
	private PhoneStatRec phoneStatRec;
	public static MediaPlayer mPlayer;
	private ArrayList<Music> musicList;
	public static String[] SongNamekeywords;
	private int current = -1;// ��ǰ���ŵĸ����±�
	private int nowcurr = 0;// ��ǰ���Ž���
	private int totalms = 0;// ��ǰ������ʱ��
	private int playmode = 2;// ����ģʽ 0 ˳�򲥷� 1 ������� 2 ����ѭ��
	private int mode_current = 0;
	public static int status = 1;// 1 δ���� 2 ��ͣ 3 ����
	Music nowplaymusic;
	//public static NotificationManager manager;
	Context context;
	  
	/* ����������ſ��ƵĹ㲥 */
	private class MyReciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (Constant.ACTION_PLAY.equals(intent.getAction())) {
				switch (status) {
				case 1:
					play();
					break;
				case 2:
					mPlayer.start();
					break;
				case 3:
					mPlayer.pause();
					status = 2;
					break;
				}
				status = 3;
			}
			// ��ͣ
			else if (Constant.ACTION_PAUSE.equals(intent.getAction())) {
				mPlayer.pause();
				status = 2;
			}
			// ֹͣ
			else if (Constant.ACTION_STOP.equals(intent.getAction())) {
				mPlayer.stop();
				mPlayer.release();
				stopSelf();
			}
			// ��һ��
			else if (Constant.ACTION_PREVIOUS.equals(intent.getAction())) {
				previous();
				status = 3;
			}
			// ��һ��
			else if (Constant.ACTION_NEXT.equals(intent.getAction())) {
				next();
				status = 3;
			}
			// JUMP
			else if (Constant.ACTION_JUMR.equals(intent.getAction())) {
				int position = intent.getIntExtra("position", 0);
				if (position >= 0) {
					jump(position);
				}
				// JUMP_OTHER
			} else if (Constant.ACTION_JUMR_OTHER.equals(intent.getAction())) {
				String name = intent.getStringExtra("name");
				Log.i("test", musicList.size() + "--position" + "---" + name);
				int position = getdataindex(name);
				if (position >= 0) {
					jump(position);
				}
			} else if (Constant.ACTION_FIND.equals(intent.getAction())) {
				String name = intent.getStringExtra("name");
				int position = getindex(name);
				if (position >= 0) {
					jump(position);
				}
			}
			// seek
			else if (Constant.ACTION_SEEK.equals(intent.getAction())) {
				try {
					nowcurr = (intent.getIntExtra("seekcurr", 0)) * totalms
							/ 100;
					mPlayer.seekTo(nowcurr);
					if (status == 2) {
						mPlayer.start();
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (Constant.ACTION_UPDATE_ALL.equals(intent.getAction())) {
				updataAllMusicInfo(false, nowplaymusic);

			} else // ���ò���ģʽ
			if (Constant.ACTION_SET_PLAYMODE.equals(intent.getAction())) {
				int n = intent.getIntExtra("play_mode", -1);
				playmode = n;
				if (n == 2) {
					mode_current = current;
				}
				// �����б����仯
			} else if (Constant.ACTION_LISTCHANGED.equals(intent.getAction())) {
				musicList.clear();
				//musicList.addAll(MyApplication.musics);
				initAllsongNames();
			} 
		}

	}

	/**
	 * ���µ�ǰ���Ÿ�������ϸ��Ϣ
	 * 
	 * @param isnet
	 * @param music
	 */
	private Intent updataintent;

	private void updataAllMusicInfo(boolean isnet, Music music) {
		if (updataintent == null) {
			updataintent = new Intent(Constant.ACTION_UPDATE);
		}
		if (isnet) {
			updataintent.putExtra("status", status);
			updataintent.putExtra("music", music);
			updataintent.putExtra("isnet", true);
		} else {
			updataintent.putExtra("status", status);
			updataintent.putExtra("music", nowplaymusic);
			updataintent.putExtra("position", current);
			updataintent.putExtra("totalms", totalms);
		}
		sendBroadcast(updataintent);
		//MyNotiofation.getNotif(MusicPlayerService.this, nowplaymusic, manager);
	}

	//SharedPreferences sp;

	@Override
	public void onCreate() {
		super.onCreate();
		//sp = getSharedPreferences("service", 0);
		//sp.edit().putBoolean("isStart", true).commit();
		// �㲥������
		mReceiver = new MyReciever();
		phoneStatRec = new PhoneStatRec();
		updataintent = new Intent(Constant.ACTION_UPDATE);
		context = this;
		mPlayer = PlayActivity.mediaPlayer;
		
		//manager = (NotificationManager) this
				//.getSystemService(Context.NOTIFICATION_SERVICE);
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			/**
			 * ���ֲ�����ɵĴ�����
			 */
			@Override
			public void onCompletion(MediaPlayer mp) {
				self();// ���ŵ�ǰ
			}
		});
		// ��ǰ���ŵ������б�
		musicList = PlayActivity.getMusics();
		// ��ǰ�������ֵ�����
		//current = MyApplication.musicPreference.getsaveposition(this);
		//current = PlayActivity.current;
	}

	private void initAllsongNames() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//SongNamekeywords = Musicdata.GetAll(musicList);
			}
		}).start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		//initAllsongNames();
		if (mPlayer == null) {
			mPlayer = PlayActivity.mediaPlayer;
		}
		//musicList = ((MyApplication) getApplication()).getMusics();
		musicList = PlayActivity.getMusics();
		// ��̬ע��㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_LISTCHANGED);
		filter.addAction(Constant.ACTION_PLAY);
		filter.addAction(Constant.ACTION_PAUSE);
		filter.addAction(Constant.ACTION_PREVIOUS);
		filter.addAction(Constant.ACTION_NEXT);
		filter.addAction(Constant.ACTION_SEEK);
		filter.addAction(Constant.ACTION_STOP);
		filter.addAction(Constant.ACTION_JUMR);
		filter.addAction(Constant.ACTION_JUMR_OTHER);
		filter.addAction(Constant.ACTION_UPDATE_ALL);
		filter.addAction(Constant.ACTION_FIND);
		filter.addAction(Constant.ACTION_NET_PLAY);
		filter.addAction(Constant.ACTION_SET_PLAYMODE);
		filter.addAction(Constant.ACTION_STAR_THREAD);
		registerReceiver(mReceiver, filter);

		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(phoneStatRec, mIntentFilter);
		//playmode = MyApplication.musicPreference.getPlayMode(this);
		//playmode = 2;
		
		//if(current == -1){
			//current = PlayActivity.current;
		//}else{
			current = PlayActivity.current;
			//current--;
		//}
		System.out.println("service current "+ current);

		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				
				//Random random = new Random();
				//current = random.nextInt(musicList.size() - 1);
				//Intent inte = new Intent("com.tarena.nextone");
				//inte.putExtra("position", current);
				//sendBroadcast(inte);
				self();
			}
		});
	
		
	}

	@Override
	public void onDestroy() {
		//Log.i("info", sp.getBoolean("isStart", false) + "");
		// ȡ���㲥ע��
		unregisterReceiver(mReceiver);
		unregisterReceiver(phoneStatRec);
		//MyApplication.musicPreference.savaPlayPosition(context, current);
		//manager.cancelAll();
		super.onDestroy();
	}

	/**
	 * ���ݸ������ƻ�ȡ��ǰ�����ڲ����б��е�λ��
	 * 
	 * @param name
	 * @return �±�λ��
	 */
	public int getindex(String name) {
		int index = 0;
		for (int i = 0; i < musicList.size(); i++) {
			if (musicList.get(i).getMusicName().equals(name)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * ���ݸ���·����ȡ��ǰ�����ڲ����б��е�λ��
	 * 
	 * @param savepath
	 * @return �±�λ��
	 */
	public int getdataindex(String savepath) {
		int index = 0;
		if (musicList.size() > 0) {
			for (int i = 0; i < musicList.size(); i++) {
				if (musicList.get(i).getSavePath() != null
						&& musicList.get(i).getSavePath().equals(savepath)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	
	/**
	 * ���ŵ�ǰ����
	 */
	private void play() {
		if (musicList != null && musicList.size() > 0) {
			Log.i("cpu", "" + playmode + isjump);
			/*
			if (playmode == 1) {// ���
				if (!isjump) {
					current = new Random().nextInt(musicList.size());
				}
			} else if (playmode == 2) {// ����
				current = mode_current;
			}
			*/
			System.out.println("play current "+ current);
			nowplaymusic = musicList.get(current);
			Log.i("music", current + "��ǰ���ŵĸ���");
			isjump = false;
			try {
				mPlayer.reset();
				//���������ļ�
				AssetManager assetManager = context.getAssets();
				AssetFileDescriptor fileDescriptor = assetManager.openFd(nowplaymusic.getSavePath());
				Log.i("musicNAME", nowplaymusic.getSavePath());
				mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
				//mPlayer.setDataSource(nowplaymusic.getSavePath());
				mPlayer.prepare();
				mPlayer.start();
				status = 3;
				totalms = mPlayer.getDuration();
				updataAllMusicInfo(false, null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ������һ������
	 */
	private void previous() {
		if (musicList != null && musicList.size() > 0) {
			if (current == 0) {
				current = musicList.size() - 1;
			} else {
				current--;
			}
			play();
		}
	}

	/**
	 * ������һ������
	 */
	private void next() {
		if (musicList != null && musicList.size() > 0) {
			if (current == musicList.size() - 1) {
				current = 0;
			} else {
				current++;
			}
			play();
		}
	}
	
	/**
	 * ����ѭ��
	 */
	private void self() {
		if (musicList != null && musicList.size() > 0) {
			if (current == musicList.size() - 1) {
				current = 0;
			} else {
				current++;
			}
			if (current == 0) {
				current = musicList.size() - 1;
			} else {
				current--;
			}
			play();
		}
	}

	/**
	 * ���ŵ����ĳһλ�ø���
	 * 
	 * @param position
	 */
	boolean isjump = false;

	private void jump(int position) {
		Log.i("test", musicList.size() + "--position" + position);
		if (musicList != null && musicList.size() > 0) {
			current = position;
			isjump = true;
			mode_current = current;
			play();
		}
	}

}
