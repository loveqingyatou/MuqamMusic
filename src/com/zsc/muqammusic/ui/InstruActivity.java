package com.zsc.muqammusic.ui;


import com.zsc.muqammusic.R;
import com.zsc.muqammusic.model.Instrument;
import com.zsc.muqammusic.util.InstrumentFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class InstruActivity extends Activity implements OnGestureListener {
	
	private ViewFlipper flipper;
	private GestureDetector detector;
	private ScrollView scrollView;
	private TextView mTvIns;
	private TextView mTvName;
	private ImageButton mBtnBack;
	private RelativeLayout reLayout; 
	private boolean isHidden;
	private Animation inAnimBtm;
	private Animation outAnimBtm;
	private Animation inAnimUp;
	private Animation outAnimUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_instrument);
		
        detector = new GestureDetector(this,this);
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);
		flipper.getBackground().setAlpha(150);
		scrollView = (ScrollView) findViewById(R.id.ins_scroll);
		scrollView.getBackground().setAlpha(150);
		mTvIns = (TextView)findViewById(R.id.ins_des);
		reLayout = (RelativeLayout) findViewById(R.id.top_bar);
		mTvName = (TextView)findViewById(R.id.top_bar_text);
		mBtnBack = (ImageButton) findViewById(R.id.top_bar_back);
		mBtnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);
		//System.out.println("index "+index);
		//System.out.println("size "+InstrumentFactory.getInstruments().size());
		Instrument ins = InstrumentFactory.getInstruments().get(index);
		
		int[] images = ins.getImages();
		for(int i=0; i<images.length; i++){
			flipper.addView(addTextView(images[i]));
		}
		mTvIns.setText(ins.getIntroduce());
		mTvName.setText(ins.getName());
		
		isHidden = false;
		//scrollView.setVisibility(View.INVISIBLE);
		
		inAnimBtm = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
		outAnimBtm = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
		inAnimUp = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
		outAnimUp = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
	}

    private View addTextView(int id) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		return iv;
	}
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	return this.detector.onTouchEvent(event);
    }
    
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
		if (e1.getX() - e2.getX() > 120) {
			hidden();
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
			this.flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			hidden();
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
			this.flipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		showAndHidden();
		return false;
	}
	
	private void hidden(){
		if(!isHidden){
			scrollView.startAnimation(outAnimBtm);
			scrollView.setVisibility(View.INVISIBLE);
			reLayout.startAnimation(outAnimUp);
			reLayout.setVisibility(View.INVISIBLE);
			isHidden = true;
		}
	}
	private void showAndHidden(){
		if(isHidden){
			scrollView.startAnimation(inAnimBtm);
			scrollView.setVisibility(View.VISIBLE);
			reLayout.startAnimation(inAnimUp);
			reLayout.setVisibility(View.VISIBLE);
			isHidden = false;
		}else{
			scrollView.startAnimation(outAnimBtm);
			scrollView.setVisibility(View.INVISIBLE);
			reLayout.startAnimation(outAnimUp);
			reLayout.setVisibility(View.INVISIBLE);
			isHidden = true;
		}
	}

}
