package com.zsc.muqammusic.util;

import com.zsc.muqammusic.R;
import com.zsc.muqammusic.model.IAlertDialogButtonListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Util {
	
	private static AlertDialog mAlertDialog;

	public static View getView(Context context, int layoutId) {
		LayoutInflater inflater = (LayoutInflater)context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View layout = inflater.inflate(layoutId, null);
		
		return layout;
	}
	
	/**
	 * ������ת
	 * @param context
	 * @param desti
	 */
	public static void startActivity(Context context, Class desti){
		Intent intent = new Intent();
		intent.setClass(context, desti);
		context.startActivity(intent);
		
		//�رյ�ǰ��Activity
		((Activity)context).finish();
	}
	
	/**
	 * ��ʾ�Զ���Ի���
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void showDialog(final Context context, String message, final IAlertDialogButtonListener listener){
		View dialogView = null;
		//AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Theme_Transparent);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		dialogView = getView(context, R.layout.dialog_view);
		
		ImageButton btnOkView = (ImageButton)dialogView.findViewById(R.id.btn_dialog_ok);
		ImageButton btnCancelView = (ImageButton)dialogView.findViewById(R.id.btn_dialog_cancel);
		TextView txtMessageView = (TextView)dialogView.findViewById(R.id.text_dialog_message);
		txtMessageView.setText(message);
		btnOkView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�رնԻ���
				if(mAlertDialog != null){
					mAlertDialog.cancel();
				}
				//�¼��ص�
				if(listener != null){
					listener.onClick();
				}
				//������Ч
				MyPlayer.playTone(context, MyPlayer.INDEX_STONE_ENTER);
			}
		});
		btnCancelView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�رնԻ���
				if(mAlertDialog != null){
					mAlertDialog.cancel();
				}
				//������Ч
				MyPlayer.playTone(context, MyPlayer.INDEX_STONE_CANCEL);
			}
		});
		
		//Ϊdialog����view
		builder.setView(dialogView);
		mAlertDialog = builder.create();
		//��ʾ�Ի���
		mAlertDialog.show();
	}
}
