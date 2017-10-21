package com.beidian.beidiannonxinling.ui.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;

public class CommonDialog extends Dialog {
	private Context         context;
	private OnClickListener clickListener;
	private String          msg;
	private int             type;

	public interface OnClickListener {

		public void doConfirm();

		public void doCancel();
	}

	public CommonDialog(Context context, String msg, int... types) {
		super(context, R.style.dialogCommon);
		this.context = context;
		this.msg = msg;
		if (types.length > 0) {
			this.type = types[0];
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		// LayoutInflater inflate = LayoutInflater.from(context);
		// View view = inflate.inflate(R.custom_tool_bar.dialog_yes_no, null);
		// setContentView(view);
		// TextView tvTitle = (TextView) view.findViewById(R.id.title);
		// TextView tvConfirm = (TextView) view.findViewById(R.id.confirm);
		// TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
		// ImageView ivMsg = (ImageView) view.findViewById(R.id.iv_msg);
		// LinearLayout layoutButton = (LinearLayout) view
		// .findViewById(R.id.layout_button);

		setContentView(R.layout.dialog_yes_no);

		TextView tvTitle = (TextView) findViewById(R.id.title);
		TextView tvConfirm = (TextView) findViewById(R.id.confirm);
		TextView tvCancel = (TextView) findViewById(R.id.cancel);
		ImageView ivMsg = (ImageView) findViewById(R.id.iv_msg);
		LinearLayout layoutButton = (LinearLayout) findViewById(
				R.id.layout_button);

		tvTitle.setMovementMethod(ScrollingMovementMethod.getInstance());
		tvTitle.setText(msg);
		tvConfirm.setOnClickListener(new ClickListener());
		tvCancel.setOnClickListener(new ClickListener());
		if (type == Const.TYPE_DIALOG_OK) {
			tvCancel.setVisibility(View.GONE);
			tvConfirm.setText(R.string.confirm);
			// CommonDialog.this.setCanceledOnTouchOutside(true);
		} else if (type == Const.TYPE_DIALOG_STOP) {
			tvCancel.setText(R.string.stop_search_star);
			tvConfirm.setVisibility(View.GONE);
			CommonDialog.this.setCanceledOnTouchOutside(false);
		} else if (type == Const.TYPE_DIALOG_IMAGE) {
			layoutButton.setVisibility(View.GONE);
			tvTitle.setVisibility(View.GONE);
			ivMsg.setVisibility(View.VISIBLE);
			CommonDialog.this.setCanceledOnTouchOutside(true);
		} else if (type == Const.TYPE_DIALOG_KONW) {
			tvCancel.setVisibility(View.GONE);
			tvConfirm.setText(R.string.kenwen);
			CommonDialog.this.setCanceledOnTouchOutside(true);
		} else if (type == Const.TYPE_DIALOG_EXIT) {
			tvCancel.setText(R.string.cancel);
			// tvCancel.setBackgroundResource(R.color.back_gray);
			tvCancel.setBackgroundResource(R.drawable.shape_circle_cancel);
			tvConfirm.setText(R.string.confirm);
			tvConfirm.setBackgroundResource(R.drawable.selector_blue_button);
			CommonDialog.this.setCanceledOnTouchOutside(false);
		} else {
			CommonDialog.this.setCanceledOnTouchOutside(true);
		}
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		// lp.type = android.view.WindowManager.LayoutParams.TYPE_PHONE;
		dialogWindow.setAttributes(lp);
	}

	public void setClicklistener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	private class ClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.confirm:
				CommonDialog.this.dismiss();
				if (null != clickListener) {
					clickListener.doConfirm();
				}
				break;
			case R.id.cancel:
				clickListener.doCancel();
				CommonDialog.this.dismiss();
				break;
			}
		}

	};

}
