package com.zsc.muqammusic.util;

import java.io.FileDescriptor;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

/**
 * ���ֲ�����
 * @author Lucien
 *
 */
public class MyPlayer {

	//����
	public final static int INDEX_STONE_ENTER = 0;
	public final static int INDEX_STONE_CANCEL = 1;
	public final static int INDEX_STONE_COIN = 2;
	
	//��Ч���ļ�����
	private final static String[] SONG_NAMES = {"enter.mp3","enter.mp3","coin.mp3"};
	//��Ч
	private static MediaPlayer[] mToneMediaPlayer = new MediaPlayer[SONG_NAMES.length];
	//��������
	private static MediaPlayer mMusicMediaPlayer;
	
	/**
	 * ��������Ч��
	 * @param context
	 * @param index
	 */
	public static void playTone(Context context, int index){
		//��������
		AssetManager assetManager = context.getAssets();
		
		if(mToneMediaPlayer[index] == null){
			mToneMediaPlayer[index] = new MediaPlayer();
			try {
				AssetFileDescriptor fileDescriptor = assetManager.openFd(SONG_NAMES[index]);
				mToneMediaPlayer[index].setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
				mToneMediaPlayer[index].prepare();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mToneMediaPlayer[index].start();
	}
	
	/**
	 * ���Ÿ���
	 * @param context
	 * @param fileName
	 */
	public static void playSong(Context context, String fileName){
		if(mMusicMediaPlayer == null){
			mMusicMediaPlayer = new MediaPlayer();
		}
		
		//ǿ������
		mMusicMediaPlayer.reset();
		//���������ļ�
		AssetManager assetManager = context.getAssets();
		try {
			AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
			mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			mMusicMediaPlayer.prepare();
			mMusicMediaPlayer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void stopTheSong(Context context){
		if(mMusicMediaPlayer != null){
			mMusicMediaPlayer.stop();
		}
	}
}
