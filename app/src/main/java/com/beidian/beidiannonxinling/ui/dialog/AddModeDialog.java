package com.beidian.beidiannonxinling.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.ui.activity.TestModelDetailActivity;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/10/9.
 */

public class AddModeDialog extends Dialog implements View.OnClickListener {
    private TextView tv_title;
    private ImageView im_attchdech,im_ftp_down,im_ftp_up,im_http,im_idle,im_ping,im_voicecall,im_voicepass,im_csfbcall,im_csfbpass,im_wait;
    private List<ModelCofingBean> Modes=new ArrayList<>();
    private ModelCofingBean model=new ModelCofingBean();
    private Context mContext;
    private LinearLayout ll_back;

    public AddModeDialog(Context context) {
        super(context, R.style.dialogCommon);
        this.mContext = context;
        initView(context);
    }
    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newtask, null);
        setContentView(view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("添加测试项");
        ll_back= (LinearLayout) view.findViewById(R.id.ll_back);
        im_attchdech= (ImageView) view.findViewById(R.id.im_attchdech);
        im_ftp_down= (ImageView) view.findViewById(R.id.im_ftp_down);
        im_ftp_up= (ImageView) view.findViewById(R.id.im_ftp_up);
        im_idle= (ImageView) view.findViewById(R.id.im_idle);
        im_http= (ImageView) view.findViewById(R.id.im_http);
        im_ping= (ImageView) view.findViewById(R.id.im_ping);

        im_voicecall= (ImageView) view.findViewById(R.id.im_voicecall_csfb_call);
        im_voicepass= (ImageView) view.findViewById(R.id.im_voicecall_csfb_answer);
        im_csfbcall= (ImageView) view.findViewById(R.id.im_voicecall_votie_call);
        im_csfbpass= (ImageView) view.findViewById(R.id.im_voicecall_votie_answer);
        im_wait= (ImageView) view.findViewById(R.id.im_wait);


        im_attchdech.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        im_ftp_down.setOnClickListener(this);
        im_ftp_up.setOnClickListener(this);
        im_idle.setOnClickListener(this);
        im_http.setOnClickListener(this);
        im_ping.setOnClickListener(this);

        im_voicecall.setOnClickListener(this);
        im_voicepass.setOnClickListener(this);
        im_csfbcall.setOnClickListener(this);
        im_csfbpass.setOnClickListener(this);
        im_wait.setOnClickListener(this);


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.95); // 高度设置为屏幕的0.6
        lp.height=(int)(d.heightPixels*0.6);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        TestModelDetailActivity detailActivity= (TestModelDetailActivity) mContext;
        switch (v.getId()){
            case R.id.ll_back:
                dismiss();
                break;
            case R.id.im_attchdech:
                ToastUtils.makeText(mContext,"im_attchdech");
                detailActivity.getmAdapter().addAttchdech();
                break;
            case R.id.im_ftp_down:
                ToastUtils.makeText(mContext,"im_ftp_down");
                detailActivity.getmAdapter().addFtpdown();
                break;
            case R.id.im_ftp_up:
                ToastUtils.makeText(mContext,"im_ftp_up");
                detailActivity.getmAdapter().addFtpup();
                break;
            case R.id.im_http:
                ToastUtils.makeText(mContext,"im_http");
                detailActivity.getmAdapter().addHttp();
                break;
            case R.id.im_idle:
               ToastUtils.makeText(mContext,"im_idle");
                detailActivity.getmAdapter().addIDLE();
                break;
            case R.id.im_ping:
                ToastUtils.makeText(mContext,"im_ping");
                detailActivity.getmAdapter().addPing();
                break;
            case R.id.im_voicecall_csfb_call:
                ToastUtils.makeText(mContext,"im_voicecall_csfb_call");
                detailActivity.getmAdapter().addVoiceCallcsfb();
                break;
            case R.id.im_voicecall_csfb_answer:
                ToastUtils.makeText(mContext,"im_voicecall_csfb_answer");
                detailActivity.getmAdapter().addVoicePasscsfb();
                break;
            case R.id.im_voicecall_votie_call:
                ToastUtils.makeText(mContext,"im_voicecall_votie_call");
                detailActivity.getmAdapter().addVoiceCallVo();
                break;
            case R.id.im_voicecall_votie_answer:
                ToastUtils.makeText(mContext,"im_voicecall_votie_answer");
                detailActivity.getmAdapter().addVoicePassVo();
                break;
            case R.id.im_wait:
                ToastUtils.makeText(mContext,"im_wait");
                detailActivity.getmAdapter().addWait();
                break;

        }
    }
}
