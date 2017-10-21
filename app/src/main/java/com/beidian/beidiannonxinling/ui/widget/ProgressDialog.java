package com.beidian.beidiannonxinling.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.util.ScreenUtils;

public class ProgressDialog extends Dialog {

	private View view;

	public ProgressDialog(Context context, View view) {
		super(context, R.style.inputDialog);
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(view);
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.9); // 设置宽度
		this.getWindow().setAttributes(lp);
	}

}
