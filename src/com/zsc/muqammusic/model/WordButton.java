package com.zsc.muqammusic.model;

import android.widget.Button;

/**
 * ���ְ�ť
 * @author Lucien
 *
 */
public class WordButton {

	public int mIndex;
	public boolean mIsVisiable;//��ʾ������
	public String mWordString;//��ǰ����
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisiable = true;
		mWordString = "";
	}
}
