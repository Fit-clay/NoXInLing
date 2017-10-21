package com.beidian.beidiannonxinling.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ModelAdapter;
import com.beidian.beidiannonxinling.adapter.ModelDetailAdapter;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.TestModelActivity;
import com.beidian.beidiannonxinling.ui.activity.TestModelDetailActivity;
import com.beidian.beidiannonxinling.util.TestModelUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ASUS on 2017/9/26.
 */

public class ModelAddDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private LinearLayout ll_confirm,ll_cancle;
    private EditText ed_modelname;
    private TestModelBean.TemplateBean modelBean=new TestModelBean.TemplateBean();
    private String ModelName=null;
    private  List<ModelCofingBean> mlist=null;

    public ModelAddDialog(Context context){
        super(context,R.style.dialogCommon);
        this.mContext = context;
        init(context);

    }
    private void init(Context context){
        mContext=context;
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_modeladd,null);
        setContentView(view);
        ll_confirm= (LinearLayout) view.findViewById(R.id.ll_confirm);
        ll_cancle= (LinearLayout) view.findViewById(R.id.ll_canlce);
        ed_modelname= (EditText) view.findViewById(R.id.ed_modelname);
        ll_confirm.setOnClickListener(this);
        ll_cancle.setOnClickListener(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.7); // 高度设置为屏幕的0.6
        lp.height=(int)(d.heightPixels*0.2);
        dialogWindow.setAttributes(lp);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_confirm:
                TestModelActivity parent= (TestModelActivity) mContext;
                if(ed_modelname.getText().toString().trim()!=null&&!ed_modelname.getText().toString().trim().equals("")){
                    UserInfoBean userbean= BaseApplication.getUserInfo();
                    ModelAdapter Adapter=parent.getmAdapter();
                    ed_modelname.getText().toString().trim();
                    modelBean.setId(Adapter.getItemCount()-1);
                    modelBean.setTemplatename(ed_modelname.getText().toString().trim());
                    modelBean.setTaskList(init(Adapter.getItemCount()-1));
                    modelBean.setAccount(userbean.getUsername());
                    modelBean.setTaskid(Adapter.getItemCount()-1);
                    Adapter.add(modelBean);



                    dismiss();

                }else {
                    ToastUtils.makeText(mContext,"名称不能为空");
                }

                break;
            case R.id.ll_canlce:
                dismiss();
                break;
        }
    }
    public List<TestTask> init(int id){

        List<TestTask> tasks=new ArrayList<>();



        List<ModelCofingBean> defaults = new ArrayList<>();//删除

        /**
         * mAttachItems
         */
        TestTask mAttachItems=new TestTask();
        mAttachItems.setId(0);
        mAttachItems.setTaskname("ATTACH(DETACH)");//名称
        mAttachItems.setTesttype(Const.TestManage.TEST_TYPE_ATTACH);//测试类型
        mAttachItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);//测试模式
        mAttachItems.setTestcount(String.valueOf(10));//测试次数
        mAttachItems.setTestnumber(String.valueOf(20));//限时计次时长
        mAttachItems.setTesttime(String.valueOf(5));//测试时长
        mAttachItems.setTestinterval(String.valueOf(5));//循环间隔
        mAttachItems.setRetentiontime(String.valueOf(2));//保持时间
        mAttachItems.setComeFromService(true);
        tasks.add(mAttachItems);
        /**
         * mFtpDownItem
         */
        TestTask mFtpDownItems=new TestTask();
        mFtpDownItems.setTaskname("FTP(UP)");
        mFtpDownItems.setId(1);
        mFtpDownItems.setTesttype(Const.TestManage.TEST_TYPE_FTP_UP);
        mFtpDownItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpDownItems.setTestcount(String.valueOf(6));
        mFtpDownItems.setTesttime(String.valueOf(5));//测试时长
        mFtpDownItems.setTestinterval(String.valueOf(5));//循环间隔
        mFtpDownItems.setRetentiontime(String.valueOf(2));//保持时间
        mFtpDownItems.setThreadcount(String.valueOf(3));
        mFtpDownItems.setTimeout(String.valueOf(60));
        mFtpDownItems.setExectimeout(String.valueOf(10));
        TestTask.TargetUrlBean tb=new TestTask.TargetUrlBean();
        mFtpDownItems.setServerpath("android/com/beidian/xinling");
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mFtpDownItems.setTargeturl(tb);
        mFtpDownItems.setServeraddress("101.231.82.82");
        mFtpDownItems.setUsername("ceping");
        mFtpDownItems.setPassword("Ceping&2015");
        mFtpDownItems.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_ACTIVE));
        mFtpDownItems.setPort(String.valueOf(21));
        mFtpDownItems.setComeFromService(true);
        tasks.add(mFtpDownItems);

        /**
         * mFtpUpItem
         */

        TestTask mFtpUpItems=new TestTask();
        mFtpUpItems.setTaskname("FTP(DOWN)");
        mFtpUpItems.setId(2);
        mFtpUpItems.setTesttype(Const.TestManage.TEST_TYPE_FTP_DOWNLOAD);
        mFtpUpItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpUpItems.setTestcount(String.valueOf(6));//测试次数
        mFtpUpItems.setTesttime(String.valueOf(5));//测试时长
        mFtpUpItems.setTestinterval(String.valueOf(5));//循环间隔
        mFtpUpItems.setRetentiontime(String.valueOf(2));//保持时间
        mFtpUpItems.setThreadcount(String.valueOf(3));
        mFtpUpItems.setTimeout(String.valueOf(60));
        mFtpUpItems.setExectimeout(String.valueOf(10));
        tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mFtpUpItems.setServerpath("android/com/beidian/xinling");
        mFtpUpItems.setTargeturl(tb);
        mFtpUpItems.setServeraddress("101.231.82.82");
        mFtpUpItems.setUsername("ceping");
        mFtpUpItems.setPassword("Ceping&2015");
        mFtpUpItems.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_PASSIVE));
        mFtpUpItems.setPort(String.valueOf(21));
        mFtpUpItems.setComeFromService(true);
        tasks.add(mFtpUpItems);
        /**
         * HTTP
         */
        TestTask mHttpItem = new TestTask();
        mHttpItem.setId(3);
        mHttpItem.setTestinterval(String.valueOf(4));
        mHttpItem.setTaskname("HTTP");
        mHttpItem.setTesttype(Const.TestManage.TEST_TYPE_HTTP);
        mHttpItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mHttpItem.setTesttime(String.valueOf(new Integer(10)));
        mHttpItem.setTestcount(String.valueOf(6));
        mHttpItem.setTestnumber(String.valueOf(8));// 8s
        tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mHttpItem.setTargeturl(tb);
        mHttpItem.setComeFromService(true);
        tasks.add(mHttpItem);


        /**
         * Csfbcall
         */
        TestTask mVoiceCallItem = new TestTask();
        mVoiceCallItem.setId(4);
        mVoiceCallItem.setTaskname("VOICE_CALL(csfb主叫)");
        mVoiceCallItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBZ);
        mVoiceCallItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVoiceCallItem.setTesttime(String.valueOf(20));
        mVoiceCallItem.setTestcount(String.valueOf(10));
        mVoiceCallItem.setTestnumber(String.valueOf(6));// 8s
        mVoiceCallItem.setTestinterval(String.valueOf(4));
        mVoiceCallItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVoiceCallItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVoiceCallItem.setCallphone("10086");
        mVoiceCallItem.setRetentiontime(String.valueOf(45));// 10s
        mVoiceCallItem.setBlockingtime(String.valueOf(20));// 60s
        mVoiceCallItem.setComeFromService(true);
        tasks.add(mVoiceCallItem);
        /**
         * Csfbpa
         */
        TestTask mCfsbCallPassiveItem = new TestTask();
        mCfsbCallPassiveItem.setId(5);
        mCfsbCallPassiveItem.setTaskname("VOICE_CALL(csfb被叫)");
        mCfsbCallPassiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBB);
        mCfsbCallPassiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mCfsbCallPassiveItem.setTesttime(String.valueOf(20));
        mCfsbCallPassiveItem.setTestcount(String.valueOf(10));
        mCfsbCallPassiveItem.setTestnumber(String.valueOf(6));// 8s
        mCfsbCallPassiveItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mCfsbCallPassiveItem.setTestinterval(String.valueOf(4));
        mCfsbCallPassiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mCfsbCallPassiveItem.setCallphone("10086");
        mCfsbCallPassiveItem.setRetentiontime(String.valueOf(45));// 10s
        mCfsbCallPassiveItem.setBlockingtime(String.valueOf(20));// 6s
        mCfsbCallPassiveItem.setComeFromService(true);
        tasks.add(mCfsbCallPassiveItem);
        /**
         * voteCall
         */
        TestTask mVolteCallActiveItem = new TestTask();
        mVolteCallActiveItem.setId(6);
        mVolteCallActiveItem.setTestinterval(String.valueOf(4));
        mVolteCallActiveItem.setTaskname("VOICE_CALL(volte主叫)");
        mVolteCallActiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_VOLTEZ);
        mVolteCallActiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVolteCallActiveItem.setTesttime(String.valueOf(20));
        mVolteCallActiveItem.setTestcount(String.valueOf(10));
        mVolteCallActiveItem.setTestnumber(String.valueOf(6));// 6s
        mVolteCallActiveItem
                .setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVolteCallActiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVolteCallActiveItem.setCallphone("10086");
        mVolteCallActiveItem.setRetentiontime(String.valueOf(45));// 10s
        mVolteCallActiveItem.setBlockingtime(String.valueOf(20));// 6s
        mVolteCallActiveItem.setComeFromService(true);
        tasks.add(mVolteCallActiveItem);
        /**
         * votePa
         */
        TestTask mVolteCallPassiveItem = new TestTask();
        mVolteCallPassiveItem.setId(7);
        mVolteCallPassiveItem.setTestinterval(String.valueOf(4));
        mVolteCallPassiveItem.setTaskname("VOICE_CALL(volte被叫)");
        mVolteCallPassiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_VOLTEB);
        mVolteCallPassiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVolteCallPassiveItem.setTesttime(String.valueOf(20));
        mVolteCallPassiveItem.setTestcount(String.valueOf(10));
        mVolteCallPassiveItem.setTestnumber(String.valueOf(6));// 6s
        mVolteCallPassiveItem
                .setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVolteCallPassiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVolteCallPassiveItem.setCallphone("10086");
        mVolteCallPassiveItem.setRetentiontime(String.valueOf(45));// 10s
        mVolteCallPassiveItem.setBlockingtime(String.valueOf(20));// 6s
        mVolteCallPassiveItem.setComeFromService(true);
        tasks.add(mVolteCallPassiveItem);
        /**
         * Wait
         */

        TestTask mWaitItem = new TestTask();
        mWaitItem.setId(8);
        mWaitItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        mWaitItem.setTaskname("WAIT");
        mWaitItem.setTesttime(String.valueOf(10));// 10s
        mWaitItem.setTesttype(String.valueOf(Const.TestManage.TEST_TYPE_WAIT));
        mWaitItem.setComeFromService(true);
        tasks.add(mWaitItem);

        /**
         * PING
         */
        TestTask mPingItem = new TestTask();
        mPingItem.setId(9);
        mPingItem.setTaskname("PING");
        mPingItem.setTesttype(Const.TestManage.TEST_TYPE_PING);
        mPingItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mPingItem.setTesttime(String.valueOf(10));
        mPingItem.setTestcount(String.valueOf(6));
        mPingItem.setTestnumber(String.valueOf(3));// 8s

        mPingItem.setTestinterval(String.valueOf(4));
        TestTask.TargetUrlBean mp=new TestTask.TargetUrlBean();
        mp.setUrl("www.baidu.com");
        mp.setName("百度");
        mPingItem.setTargeturl(mp);
        mPingItem.setComeFromService(true);
        tasks.add(mPingItem);
        /**
         *idle
         */
        TestTask midleItem = new TestTask();
        midleItem.setTaskname("IDLE");
        midleItem.setId(10);
        midleItem.setTesttype(Const.TestManage.TEST_TYPE_IDLE);
        midleItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        midleItem.setTesttime(String.valueOf(9));
        midleItem.setComeFromService(true);
        tasks.add(midleItem);


        return tasks;
    }

}
