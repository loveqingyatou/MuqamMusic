package com.zsc.muqammusic.ui;

import java.util.ArrayList;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.model.WordButton;
import com.zsc.muqammusic.myui.MyGridView;
import com.zsc.muqammusic.util.Util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

	// ��Ƭ��ض���
	private Animation mPanAnim;
	private LinearInterpolator mPanLin;
	// ������ض���
	private Animation mBarInAnim;
	private LinearInterpolator mBarInLin;
	// ������ض���
	private Animation mBarOutAnim;
	private LinearInterpolator mBarOutLin;
	
	// ��Ƭ�ؼ�
	private ImageView mViewPan;
	// ���˿ؼ�
	private ImageView mViewPanBar;	
	// Play �����¼�
	private ImageButton mBtnPlayStart;
	
	// ��ǰ�����Ƿ���������
	private boolean mIsRunning = false;
	
	// ���ֿ�����
	private ArrayList<WordButton> mAllWords;
	
	private ArrayList<WordButton> mBtnSelectWords;
	
	private MyGridView mMyGridView;
	
	// ��ѡ�����ֿ�UI����
	private LinearLayout mViewWordsContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ��ʼ���ؼ�
		mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
		mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handlePlayButton();
			}
		});
		
		// ��ʼ���ؼ�
		mViewPan = (ImageView)findViewById(R.id.imageView1);
		mViewPanBar = (ImageView)findViewById(R.id.imageView2);
        mMyGridView = (MyGridView)findViewById(R.id.gridview);	
		mViewWordsContainer = (LinearLayout)findViewById(R.id.word_select_container);
		
		// ��ʼ������
		mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		mPanLin = new LinearInterpolator();
		mPanAnim.setInterpolator(mPanLin);
		mPanAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	// ���������˳�����
            	mViewPanBar.startAnimation(mBarOutAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
		
		mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
		mBarInLin = new LinearInterpolator();
		mBarInAnim.setFillAfter(true);//����������֮�󱣳���� ״̬��λ��
		mBarInAnim.setInterpolator(mBarInLin);
		mBarInAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	// ��ʼ��Ƭ����         	
            	mViewPan.startAnimation(mPanAnim);         	
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
		
		mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
		mBarOutLin = new LinearInterpolator();
		mBarOutAnim.setFillAfter(true);
		mBarOutAnim.setInterpolator(mBarOutLin);
		mBarOutAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	// ���׶����������
            	//Log.i("tese", "222222222");
            	mIsRunning = false;
            	mBtnPlayStart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            	
            }
        });
		
		
		// ��ʼ����Ϸ����
		initCurrentStageData();
	}

	
	/**
     * ����Բ���м�Ĳ��Ű�ť�����ǿ�ʼ��������
     */
	private void handlePlayButton() {
		if (mViewPanBar != null) {
			if (!mIsRunning) {
				mIsRunning = true;			
				// ��ʼ���˽��붯��
				mViewPanBar.startAnimation(mBarInAnim);
				mBtnPlayStart.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
    public void onPause() {
        mViewPan.clearAnimation();
        
        super.onPause();
    }
	
	
	private void initCurrentStageData() {
		// ��ʼ����ѡ���
		mBtnSelectWords = initWordSelect();
		
		LayoutParams params = new LayoutParams(80, 80);
		
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(
					mBtnSelectWords.get(i).mViewButton,
					params);
		}
		
		// �������
		mAllWords = initAllWord();
		// ��������- MyGridView
		mMyGridView.updateData(mAllWords);
	}
	
	/**
	 * ��ʼ����ѡ���ֿ�
	 */
	private ArrayList<WordButton> initAllWord() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		// ������д�ѡ����
	    // .........
		
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			WordButton button = new WordButton();
			
			button.mWordString = "��";
			
			data.add(button);
		}
		
		return data;
	}
	
	/**
	 * ��ʼ����ѡ�����ֿ�
	 * 
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		for (int i = 0; i < 4; i++) {
			View view = Util.getView(MainActivity.this, R.layout.self_ui_gridview_item);
			
			WordButton holder = new WordButton();
			
			holder.mViewButton = (Button)view.findViewById(R.id.item_btn);
			holder.mViewButton.setTextColor(Color.WHITE);
			holder.mViewButton.setText("");
			holder.mIsVisiable = false;
			
			holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
			
			data.add(holder);
		}
		
		return data;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
