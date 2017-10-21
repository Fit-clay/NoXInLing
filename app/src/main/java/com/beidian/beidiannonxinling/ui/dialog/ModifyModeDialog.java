package com.beidian.beidiannonxinling.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ModelDetailAdapter;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.TestModelDetailActivity;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/10/10.
 */

public class ModifyModeDialog extends Dialog implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private Context mContext;
    EditText et_model_wait_time,et_model_name,et_model_times_s,et_model_time,et_model_circleTime,et_model_testTime,et_model_detail_callNumber,et_model_durationTime,et_model_blockTime,et_model_nthreadNum,
            et_model_noDataTime,ed_corver_target_http,ed_corver_target_url_http,ed_corver_target_url,ed_corver_target,et_idle_waitTime, et_model_outTime,et_model_connectPath,et_model_connectIp,et_model_userName,et_model_password,et_model_port;

    LinearLayout ll_base,ll_http,ll_wait,ll_ftp,ll_back,ll_voice,ll_ping,ll_tv_model_circleTime,ll_right_text,ll_model_times,ll_model_detail_testTime,ll_testmodel,ll_model_durationTime,ll_test_detail_testTime_s_s;
    Spinner sp_test_model,call_model,sp_ftp_dcoum,sp_ftp_model;
    ArrayAdapter<CharSequence> callAdapter;
    ArrayAdapter<CharSequence> callModelAdapter;

    TextView tv_title,tv_number,tv_test_time,tv_right_text,tv_model_testTime;

    private  TestTask bean;

    public ModifyModeDialog(Context context, List<TestModelBean.TemplateBean> date, int task,int id){
        super(context, R.style.dialogCommon);
        this.mContext=context;
        this.bean=date.get(task).getTaskList().get(id);
        initView(context);
    }
    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_model_detail, null);
        setContentView(view);
        tv_test_time= (TextView) findViewById(R.id.tv_test_time);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_right_text= (TextView) findViewById(R.id.tv_right_text);
        ll_right_text= (LinearLayout) findViewById(R.id.ll_right_text);
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        ll_ftp= (LinearLayout) findViewById(R.id.ll_ftp);
        ll_voice= (LinearLayout) findViewById(R.id.ll_voice_call);
        ll_http= (LinearLayout) findViewById(R.id.ll_http);
        ll_ping= (LinearLayout) findViewById(R.id.ll_ping);
        ll_testmodel= (LinearLayout) findViewById(R.id.ll_testmodel);
        ll_tv_model_circleTime= (LinearLayout) findViewById(R.id.ll_tv_model_circleTime);
        ll_model_detail_testTime= (LinearLayout) findViewById(R.id.ll_model_detail_testTime);
        ll_model_times= (LinearLayout) findViewById(R.id.ll_model_times);
        ll_model_durationTime= (LinearLayout) findViewById(R.id.ll_model_durationTime);

        tv_model_testTime= (TextView) findViewById(R.id.tv_model_testTime);
         /*

        基础面板区
         */
        et_model_name= (EditText) findViewById(R.id.tv_model_name);
        et_model_time= (EditText) findViewById(R.id.tv_model_times);//测试次数
        et_model_testTime= (EditText) findViewById(R.id.et_model_testTime);//测试时长
        et_model_circleTime= (EditText) findViewById(R.id.et_model_circleTime);//间隔
        sp_test_model= (Spinner) findViewById(R.id.sp_test_model);//测试模式
        call_model= (Spinner) findViewById(R.id.call_model);
        tv_right_text.setText("保存");

        /**
         * voice call功能区
         */
        call_model= (Spinner) findViewById(R.id.call_model);
        et_model_detail_callNumber= (EditText) findViewById(R.id.et_model_detail_callNumber);//呼叫/接听电话号码
        et_model_durationTime= (EditText) findViewById(R.id.et_model_durationTime);//保持时间
        et_model_blockTime= (EditText) findViewById(R.id.et_model_blockTime);//阻塞时间
        /**
         * ftp功能面板区
         */
        et_model_nthreadNum= (EditText) findViewById(R.id.et_model_nthreadNum);//线程数
        et_model_noDataTime= (EditText) findViewById(R.id.et_model_noDataTime);//停转超时
        et_model_outTime= (EditText) findViewById(R.id.et_model_outTime);//超时时间
        et_model_connectPath= (EditText) findViewById(R.id.et_model_connectPath);//服务器路径
        et_model_connectIp= (EditText) findViewById(R.id.et_model_connectIp);
        et_model_userName= (EditText) findViewById(R.id.et_model_userName);//用户名
        et_model_password= (EditText) findViewById(R.id.tv_model_password );//密码
        et_model_port= (EditText) findViewById(R.id.et_model_port);//端口
        /**
         * HTTP
         */
        ed_corver_target_http= (EditText) findViewById(R.id.ed_corver_target_http);
        ed_corver_target_url_http= (EditText) findViewById(R.id.ed_corver_target_http_url);


        ll_model_detail_testTime.setVisibility(View.GONE);

        callAdapter  = ArrayAdapter.createFromResource(mContext,
                R.array.model_test_model, android.R.layout.simple_spinner_item);
        callModelAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.model_call_model, android.R.layout.simple_spinner_item);
        callAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        callModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /**
         *PING
         */
        ed_corver_target= (EditText) findViewById(R.id.ed_corver_target);
        ed_corver_target_url= (EditText) findViewById(R.id.ed_corver_target_url);
        initData();
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
        sp_test_model.setOnItemSelectedListener(this);
        ll_right_text.setOnClickListener(this);
    }

    protected void initData() {
        ll_ping.setVisibility(View.GONE);
        tv_title.setText(bean.getTaskname());
        et_model_name.setText(bean.getTaskname());
        sp_test_model.setAdapter(callAdapter);
        call_model.setAdapter(callModelAdapter);
        LogUtils.e(bean.getTesttype()+"ffffffffff");
        ll_http.setVisibility(View.GONE);
        ll_ping.setVisibility(View.GONE);
        switch (bean.getTesttype().toString()) {

            case Const.TestManage.TEST_TYPE_IDLE:
                ll_voice.setVisibility(View.GONE);
                ll_ftp.setVisibility(View.GONE);
                ll_testmodel.setVisibility(View.GONE);

                /**
                 * 基础信息初始化
                 */
                ll_tv_model_circleTime.setVisibility(View.GONE);


                ll_model_durationTime.setVisibility(View.GONE);

                ll_model_times.setVisibility(View.GONE);


                ll_model_detail_testTime.setVisibility(View.VISIBLE);
                et_model_testTime.setText(bean.getTesttime().toString());

                break;
            case  Const.TestManage.TEST_TYPE_FTP_DOWNLOAD:
                ll_ftp.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }

                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * ping测试初始化
                 */

                if (bean.getThreadcount() != null) {
                    et_model_nthreadNum.setText(bean.getThreadcount().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_outTime.setText(bean.getTimeout().toString());
                }
                if (bean.getExectimeout() != null) {
                    et_model_noDataTime.setText(bean.getExectimeout().toString());
                }
                if (bean.getServerpath() != null) {
                    et_model_connectPath.setText(bean.getServerpath().toString());
                }

                // mFtpUpItem.setUploadsize((long) 100);// 100M
                if (bean.getServeraddress() != null) {
                    et_model_connectIp.setText(bean.getServeraddress().toString());
                }
                if (bean.getUsername() != null) {
                    et_model_userName.setText(bean.getUsername().toString());
                }
                if (bean.getPassword() != null) {
                    et_model_password.setText(bean.getPassword().toString());
                }

                //mFtpUpItem.setPassiveModel(Const.FTP_PASSIVE_MODEL_PASSIVE);
                if (bean.getPort() != null) {
                    et_model_port.setText(bean.getPort().toString());
                }
                break;
            case  Const.TestManage.TEST_TYPE_FTP_UP:
                ll_ftp.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * ping测试初始化
                 */
                if (bean.getThreadcount() != null) {
                    et_model_nthreadNum.setText(bean.getThreadcount().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_outTime.setText(bean.getTimeout().toString());
                }
                if (bean.getExectimeout() != null) {
                    et_model_noDataTime.setText(bean.getExectimeout().toString());
                }
                if (bean.getServerpath() != null) {
                    et_model_connectPath.setText(bean.getServerpath().toString());
                }

                // mFtpUpItem.setUploadsize((long) 100);// 100M
                if (bean.getServeraddress() != null) {
                    et_model_connectIp.setText(bean.getServeraddress().toString());
                }
                if (bean.getUsername() != null) {
                    et_model_userName.setText(bean.getUsername().toString());
                }
                if (bean.getPassword() != null) {
                    et_model_password.setText(bean.getPassword().toString());
                }

                //mFtpUpItem.setPassiveModel(Const.FTP_PASSIVE_MODEL_PASSIVE);
                if (bean.getPort() != null) {
                    et_model_port.setText(bean.getPort().toString());
                }
                break;

            case  Const.TestManage.TEST_TYPE_CALL_CSFBB:
                ll_ftp.setVisibility(View.GONE);
                ll_voice.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * 语音模式初始化
                 */
                if (bean.getCallphone() != null) {
                    et_model_detail_callNumber.setText(bean.getCallphone().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_durationTime.setText(bean.getTimeout().toString());
                }
                if (bean.getBlockingtime() != null) {
                    et_model_blockTime.setText(bean.getBlockingtime().toString());
                }

                break;
            case  Const.TestManage.TEST_TYPE_CALL_CSFBZ:
                ll_ftp.setVisibility(View.GONE);
                ll_voice.setVisibility(View.VISIBLE);
                tv_number.setText("呼叫号码");
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * 语音模式初始化
                 */
                if (bean.getCallphone() != null) {
                    et_model_detail_callNumber.setText(bean.getCallphone().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_durationTime.setText(bean.getTimeout().toString());
                }
                if (bean.getBlockingtime() != null) {
                    et_model_blockTime.setText(bean.getBlockingtime().toString());
                }

                break;
            case  Const.TestManage.TEST_TYPE_CALL_VOLTEZ:
                ll_ftp.setVisibility(View.GONE);
                ll_voice.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                tv_number.setText("呼叫号码");
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * 语音模式初始化
                 */
                if (bean.getCallphone() != null) {
                    et_model_detail_callNumber.setText(bean.getCallphone().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_durationTime.setText(bean.getTimeout().toString());
                }
                if (bean.getBlockingtime() != null) {
                    et_model_blockTime.setText(bean.getBlockingtime().toString());
                }
                break;
            case  Const.TestManage.TEST_TYPE_CALL_VOLTEB:
                ll_ftp.setVisibility(View.GONE);
                ll_voice.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                /**
                 * 语音模式初始化
                 */
                if (bean.getCallphone() != null) {
                    et_model_detail_callNumber.setText(bean.getCallphone().toString());
                }
                if (bean.getTimeout() != null) {
                    et_model_durationTime.setText(bean.getTimeout().toString());
                }
                if (bean.getBlockingtime() != null) {
                    et_model_blockTime.setText(bean.getBlockingtime().toString());
                }
                break;
            case  Const.TestManage.TEST_TYPE_ATTACH:
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                /**
                 * 基础信息初始化
                 */
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }

                break;
            case Const.TestManage.TEST_TYPE_WAIT:
                tv_model_testTime.setText("等待时间(S)");
                ToastUtils.makeText(mContext,"wait");

                ll_voice.setVisibility(View.GONE);
                ll_ftp.setVisibility(View.GONE);
                ll_testmodel.setVisibility(View.GONE);

                /**
                 * 基础信息初始化
                 */
                ll_tv_model_circleTime.setVisibility(View.GONE);


                ll_model_durationTime.setVisibility(View.GONE);

                ll_model_times.setVisibility(View.GONE);


                ll_model_detail_testTime.setVisibility(View.VISIBLE);
                et_model_testTime.setText(bean.getTesttime().toString());
                break;
            case Const.TestManage.TEST_TYPE_PING:
                ll_ping.setVisibility(View.VISIBLE);
                /**
                 * 基础信息初始化
                 */
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }

                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                if(bean.getTargeturl().getName().toString()!=null){
                    ed_corver_target.setText(bean.getTargeturl().getName().toString());
                }
                if(bean.getTargeturl().getUrl().toString()!=null){
                    ed_corver_target_url.setText(bean.getTargeturl().getUrl().toString());
                }


                break;
            case Const.TestManage.TEST_TYPE_HTTP:
                ll_http.setVisibility(View.VISIBLE);
                if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME){
                    sp_test_model.setSelection(1);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_COUNT){
                    sp_test_model.setSelection(0);
                }else if(bean.getTestmode()==Const.TestManage.TEST_MODE_TIME_COUNT){
                    sp_test_model.setSelection(2);
                }
                if (bean.getTestinterval() != null) {
                    et_model_circleTime.setText(bean.getTestinterval().toString());//循环间隔时长
                } else {
                    ll_tv_model_circleTime.setVisibility(View.GONE);
                }
                if (bean.getRetentiontime() != null) {
                    et_model_durationTime.setText(bean.getRetentiontime().toString());
                }
                if (bean.getTestcount() != null) {
                    et_model_time.setText(bean.getTestcount().toString());//测试次数
                }
                if (bean.getTesttime().toString() != null) {
                    et_model_testTime.setText(bean.getTesttime().toString());//测试时长
                }
                if(bean.getTargeturl().getName().toString()!=null){
                    ed_corver_target_http.setText(bean.getTargeturl().getName().toString());
                }
                if(bean.getTargeturl().getUrl().toString()!=null){
                    ed_corver_target_url_http.setText(bean.getTargeturl().getUrl().toString());
                }
                break;



        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(callAdapter.getItem(position).equals("计次")){
            ToastUtils.makeText(mContext,"计次");
            ll_model_detail_testTime.setVisibility(View.GONE);
            ll_model_times.setVisibility(View.VISIBLE);
            bean.setTestmode(String.valueOf(Const.TestManage.TEST_MODE_COUNT));


        }else if(callAdapter.getItem(position).equals("计时")){
            ToastUtils.makeText(mContext,"计时");
            ll_model_detail_testTime.setVisibility(View.VISIBLE);
            ll_model_times.setVisibility(View.GONE);
            bean.setTestmode(Const.TestManage.TEST_MODE_TIME);

        }else if(callAdapter.getItem(position).equals("限时计次")){
            ToastUtils.makeText(mContext,"限时计次");
            ll_model_detail_testTime.setVisibility(View.VISIBLE);
            ll_model_times.setVisibility(View.VISIBLE);
            bean.setTestmode(Const.TestManage.TEST_MODE_TIME_COUNT);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        TestModelDetailActivity parent= (TestModelDetailActivity) mContext;
        ModelDetailAdapter detailAdapter;
        switch (v.getId()){
            case R.id.ll_back:
                break;
            case R.id.ll_right_text:

                switch (bean.getTesttype().toString()){
                    case Const.TestManage.TEST_TYPE_PING:
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        TestTask.TargetUrlBean targetUrlBean=new TestTask.TargetUrlBean();
                        targetUrlBean.setUrl(ed_corver_target_url.getText().toString());
                        targetUrlBean.setName(ed_corver_target.getText().toString());
                        bean.setTargeturl(targetUrlBean);
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_FTP_UP:
                        ToastUtils.makeText(mContext,"TEST_FTP_UPLOAD");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /***
                         * FTP
                         */
                        bean.setThreadcount(et_model_nthreadNum.getText().toString());
                        bean.setExectimeout(et_model_noDataTime.getText().toString());
                        bean.setTimeout(et_model_outTime.getText().toString());
                        bean.setServerpath(et_model_connectPath.getText().toString());
                        bean.setServeraddress(et_model_connectIp.getText().toString());
                        bean.setUsername(et_model_userName.getText().toString());
                        bean.setPassword(et_model_password.getText().toString());
                        bean.setPort(et_model_port.getText().toString());
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_FTP_DOWNLOAD:
                        ToastUtils.makeText(mContext,"TEST_FTP_DOWNLOAD");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /***
                         * FTP
                         */
                        bean.setThreadcount(et_model_nthreadNum.getText().toString());
                        bean.setExectimeout(et_model_noDataTime.getText().toString());
                        bean.setTimeout(et_model_outTime.getText().toString());
                        bean.setServerpath(et_model_connectPath.getText().toString());
                        bean.setServeraddress(et_model_connectIp.getText().toString());
                        bean.setUsername(et_model_userName.getText().toString());
                        bean.setPassword(et_model_password.getText().toString());
                        bean.setPort(et_model_port.getText().toString());
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_HTTP:
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        targetUrlBean=new TestTask.TargetUrlBean();
                        targetUrlBean.setUrl(ed_corver_target_url_http.getText().toString());
                        targetUrlBean.setName(ed_corver_target_http.getText().toString());
                        bean.setTargeturl(targetUrlBean);
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;

                    case Const.TestManage.TEST_TYPE_ATTACH:
                        ToastUtils.makeText(mContext,"TEST_TACH");

                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_WAIT:
                        ToastUtils.makeText(mContext,"TEST_VOICE_CSFB_ACTIVE");
                        bean.setTaskname(String.valueOf(et_model_name.getText().toString()));

                        bean.setTesttime(et_model_testTime.getText().toString());
                        /**
                         * 保存
                         */
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_IDLE:
                        bean.setTaskname(String.valueOf(et_model_name.getText().toString()));
                        bean.setTesttime(et_model_testTime.getText().toString());
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_CALL_CSFBZ:
                        ToastUtils.makeText(mContext,"TEST_VOICE_CSFB_ACTIVE");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /**
                         * 语音
                         */
                        bean.setCallphone(et_model_detail_callNumber.getText().toString());
                        bean.setRetentiontime(et_model_durationTime.getText().toString());
                        bean.setBlockingtime(et_model_blockTime.getText().toString());
                        /**
                         * 保存
                         */
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_CALL_CSFBB:
                        ToastUtils.makeText(mContext,"TEST_VOICE_CFSB_PASSIVE");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /**
                         * 语音
                         */
                        bean.setCallphone(et_model_detail_callNumber.getText().toString());
                        bean.setRetentiontime(et_model_durationTime.getText().toString());
                        bean.setBlockingtime(et_model_blockTime.getText().toString());
                        /**
                         * 保存
                         */
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_CALL_VOLTEB:
                        ToastUtils.makeText(mContext,"TEST_VOICE_VOLTE_PASSIVE");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /**
                         * 语音
                         */
                        bean.setCallphone(et_model_detail_callNumber.getText().toString());
                        bean.setRetentiontime(et_model_durationTime.getText().toString());
                        bean.setBlockingtime(et_model_blockTime.getText().toString());
                        /**
                         * 保存
                         */
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                    case Const.TestManage.TEST_TYPE_CALL_VOLTEZ:
                        ToastUtils.makeText(mContext,"TEST_VOICE_VOLTE_ACTIVE");
                        bean.setTestinterval(et_model_circleTime.getText().toString());
                        bean.setTaskname( String.valueOf(et_model_name.getText()));
                        bean.setTestcount(et_model_time.getText().toString());
                        bean.setTesttime(et_model_testTime.getText().toString());
                        bean.setTestmode(bean.getTestmode());
                        /**
                         * 语音
                         */
                        bean.setCallphone(et_model_detail_callNumber.getText().toString());
                        bean.setRetentiontime(et_model_durationTime.getText().toString());
                        bean.setBlockingtime(et_model_blockTime.getText().toString());
                        /**
                         * 保存
                         */
                        parent= (TestModelDetailActivity) mContext;
                        detailAdapter=parent.getmAdapter();
                        ToastUtils.makeText(mContext,"你的修改已提交~");
                        detailAdapter.ModifyModelBean(bean.getId(),bean);
                        dismiss();
                        break;
                }
                break;
        }
    }
}
