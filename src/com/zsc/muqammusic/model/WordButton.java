package com.zsc.muqammusic.model;

import android.widget.Button;

/**
 * 文字按钮
 * @author Lucien
 *
 */
public class WordButton {

	public int mIndex;
	public boolean mIsVisiable;//显示或隐藏
	public String mWordString;//当前文字
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisiable = true;
		mWordString = "";
	}
}
