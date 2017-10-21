package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.TestModelUtil;

import java.util.List;

/**
 * Created by ASUS on 2017/9/26.
 */

public class ModelDetailAdapter extends RecyclerView.Adapter {
    private Context mContext=null;
    private ModelCofingBean modelCofingBean=null;
    private List<TestModelBean.TemplateBean>  models;

    private int taskid;


    public ModelDetailAdapter() {

    }

    public ModelDetailAdapter(List<TestModelBean.TemplateBean>  datas,Context context, int id) {
        models=datas;
        this.mContext=context;
        this.taskid=id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ModelDetailHolder holder = new ModelDetailHolder(LayoutInflater
                .from(parent.getContext())
                //inflate的第三种放置方法
                .inflate(R.layout.item_model_detail, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ModelDetailHolder detailHolder = (ModelDetailHolder) holder;
        String id = String.valueOf(position + 1);
        detailHolder.tv_id.setText(id);
        models.get(taskid).getTaskList().get(position).setId(position);

        models.get(taskid).getTaskList().get(position).getTaskid();
        detailHolder.tv_name.setText(  models.get(taskid).getTaskList().get(position).getTaskname());
if(models.get(taskid).getTaskList().get(position).getTestmode()!=null) {
    if (models.get(taskid).getTaskList().get(position).getTestmode().equals(String.valueOf(Const.TestManage.TEST_MODE_COUNT))) {
        detailHolder.tv_count.setText(String.valueOf(models.get(taskid).getTaskList().get(position).getTestcount()));

    } else if (models.get(taskid).getTaskList().get(position).getTestmode().equals(String.valueOf(Const.TestManage.TEST_MODE_TIME))) {
        detailHolder.tv_count.setText(String.valueOf(models.get(taskid).getTaskList().get(position).getTesttime()));
    } else if (models.get(taskid).getTaskList().get(position).getTestmode().equals(String.valueOf(Const.TestManage.TEST_MODE_TIME_COUNT))) {
        detailHolder.tv_count.setText(String.valueOf(models.get(taskid).getTaskList().get(position).getTestnumber()));
    }
}else {

        detailHolder.tv_count.setText(String.valueOf(models.get(taskid).getTaskList().get(position).getWaittime()));

}

        detailHolder.im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                models.get(taskid).getTaskList().remove(position);
                notifyDataSetChanged();
                TestModelUtil.ModifiTemplate(models);
            }
        });
        detailHolder.ll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String model=   models.get(taskid).getTaskList().get(position).getTestmode();
                if (model != null) {


                    DialogUtil.modifyModeDialog(mContext,models,taskid,position);

                }
            }
        });
    }
    public void ModifyModelBean(int id,TestTask modelCofingBean){
        models.get(taskid).getTaskList().remove(id);

        models.get(taskid).getTaskList().add(id,modelCofingBean);
        notifyDataSetChanged();

       TestModelUtil.ModifiTemplate(models);


    }

    @Override
    public int getItemCount() {
        if(  models.get(taskid).getTaskList()==null) {
            return 0;
        }else {
            return   models.get(taskid).getTaskList().size();
        }

    }

    public void add(ModelCofingBean bean) {
        if (bean != null) {
          //  mList.add(bean);
            notifyDataSetChanged();
        } else {
            System.out.print("你添加了空值");
        }

    }

    class ModelDetailHolder extends RecyclerView.ViewHolder {
        //测试编号， 测试名称，测试次数
        TextView tv_id, tv_name, tv_count;
        ImageView im_delete;
        LinearLayout ll_menu;

        public ModelDetailHolder(View itemView) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_model_detail_number);
            tv_name = (TextView) itemView.findViewById(R.id.tv_model_detail_testproject);
            tv_count = (TextView) itemView.findViewById(R.id.tv_model_detail_testnumber);
            im_delete = (ImageView) itemView.findViewById(R.id.im_model_detail_delete);
            ll_menu = (LinearLayout) itemView.findViewById(R.id.ll_model_detail_item);
        }
    }
    public void addAttchdech(){
        TestTask mAttachItem=new TestTask();
        mAttachItem.setTaskid(taskid);
        mAttachItem.setId(  models.get(taskid).getTaskList().size()-1);
        mAttachItem.setTaskname("ATTACH(DETACH)");
        mAttachItem.setTesttype(Const.TestManage.TEST_TYPE_ATTACH);
        mAttachItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mAttachItem.setTesttime(String.valueOf(10));
        mAttachItem.setTestcount(String.valueOf(5));
        mAttachItem.setTestnumber(String.valueOf(5));// 8s
        mAttachItem.setTestinterval(String.valueOf(2));// 2s

        mAttachItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mAttachItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addHttp(){
        TestTask mHttpItem = new TestTask();
        mHttpItem.setTaskname("HTTP");
        mHttpItem.setTaskid(taskid);
        mHttpItem.setId(  models.get(taskid).getTaskList().size()-1);
        mHttpItem.setTesttype(Const.TestManage.TEST_TYPE_HTTP);
        mHttpItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mHttpItem.setTesttime(String.valueOf(new Integer(10)));
        mHttpItem.setTestcount(String.valueOf(6));
        mHttpItem.setTestnumber(String.valueOf(8));// 8s
        TestTask.TargetUrlBean tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mHttpItem.setTargeturl(tb);


        mHttpItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mHttpItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addFtpup(){

        TestTask mFtpUpItem=new TestTask();
        mFtpUpItem.setTaskname("FTP(UP)");
        mFtpUpItem.setTaskid(taskid);
        mFtpUpItem.setId(  models.get(taskid).getTaskList().size()-1);
        mFtpUpItem.setTesttype(Const.TestManage.TEST_TYPE_FTP_UP);
        mFtpUpItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpUpItem.setTesttime(String.valueOf(10));
        mFtpUpItem.setTestcount(String.valueOf(5));
        mFtpUpItem.setTestnumber(String.valueOf(5));// 8s
        // mFtpUpItem.setDurationTime(2);
        mFtpUpItem.setThreadcount(String.valueOf(3));
        mFtpUpItem.setTimeout(String.valueOf(60));//超时时间
        mFtpUpItem.setExectimeout(String.valueOf(10));//停转超时
        mFtpUpItem.setServerpath("android/com/beidian/xinling");
        mFtpUpItem.setUploadsize(String.valueOf(100));// 100M
        mFtpUpItem.setServeraddress("101.231.82.82");

        mFtpUpItem.setUsername("ceping");
        mFtpUpItem.setPassword("Ceping&2015");
        mFtpUpItem.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_ACTIVE));
        mFtpUpItem.setPort(String.valueOf(21));
        mFtpUpItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mFtpUpItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addFtpdown(){
        TestTask mFtpDownItem=new TestTask();
        mFtpDownItem.setTaskname("FTP(DOWN)");
        mFtpDownItem.setTaskid(taskid);
        mFtpDownItem.setId(  models.get(taskid).getTaskList().size()-1);
        mFtpDownItem.setTesttype(Const.TestManage.TEST_TYPE_FTP_DOWNLOAD);
        mFtpDownItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpDownItem.setTesttime(String.valueOf(5));//测试时长
        mFtpDownItem.setTestcount(String.valueOf(6));
        mFtpDownItem.setTestinterval(String.valueOf(5));//循环间隔
        mFtpDownItem.setRetentiontime(String.valueOf(2));//保持时间
        mFtpDownItem.setThreadcount(String.valueOf(3));
        mFtpDownItem.setTimeout(String.valueOf(60));//超时时间
        mFtpDownItem.setExectimeout(String.valueOf(10));//停转超时
        TestTask.TargetUrlBean tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mFtpDownItem.setServerpath("android/com/beidian/xinling");
        mFtpDownItem.setTargeturl(tb);
        mFtpDownItem.setServeraddress("101.231.82.82");
        mFtpDownItem.setUsername("ceping");
        mFtpDownItem.setPassword("Ceping&2015");
        mFtpDownItem.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_ACTIVE));
        mFtpDownItem.setPort(String.valueOf(21));
        mFtpDownItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mFtpDownItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addPing(){
        TestTask mPingItem = new TestTask();
        mPingItem.setId(models.get(taskid).getTaskList().size()-1);
        mPingItem.setTaskid(taskid);
        mPingItem.setTaskname("PING");
        mPingItem.setTesttype(Const.TestManage.TEST_TYPE_PING);
        mPingItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mPingItem.setTesttime(String.valueOf(10));
        mPingItem.setTestcount(String.valueOf(6));
        mPingItem.setTestnumber(String.valueOf(3));// 8s
        models.get(taskid).getTaskList().add(mPingItem);
        notifyDataSetChanged();
        TestModelUtil.ModifiTemplate(models);
    }
    public void addVoiceCallcsfb(){

        TestTask mVoiceCallItem = new TestTask();
        mVoiceCallItem.setTaskname("VOICE_CALL(csfb主叫)");
        mVoiceCallItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBZ);
        mVoiceCallItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVoiceCallItem.setTesttime(String.valueOf(20));
        mVoiceCallItem.setTestcount(String.valueOf(10));
        mVoiceCallItem.setTestnumber(String.valueOf(6));// 8s
        mVoiceCallItem.setTaskid(taskid);
        mVoiceCallItem.setId(  models.get(taskid).getTaskList().size()-1);
        mVoiceCallItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVoiceCallItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVoiceCallItem.setCallphone("10086");
        mVoiceCallItem.setRetentiontime(String.valueOf(45));// 10s
        mVoiceCallItem.setBlockingtime(String.valueOf(20));// 60s
        mVoiceCallItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mVoiceCallItem);
        notifyDataSetChanged();
        TestModelUtil.ModifiTemplate(models);


    }
    public void addVoicePasscsfb(){
        TestTask mCfsbCallPassiveItem = new TestTask();
        mCfsbCallPassiveItem.setTaskname("VOICE_CALL(csfb被叫)");
        mCfsbCallPassiveItem.setTaskid(taskid);
        mCfsbCallPassiveItem.setId( models.get(taskid).getTaskList().size()-1);
        mCfsbCallPassiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBB);
        mCfsbCallPassiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mCfsbCallPassiveItem.setTesttime(String.valueOf(20));
        mCfsbCallPassiveItem.setTestcount(String.valueOf(10));
        mCfsbCallPassiveItem.setTestnumber(String.valueOf(6));// 8s
        mCfsbCallPassiveItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));

        mCfsbCallPassiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mCfsbCallPassiveItem.setCallphone("10086");
        mCfsbCallPassiveItem.setRetentiontime(String.valueOf(45));// 10s
        mCfsbCallPassiveItem.setBlockingtime(String.valueOf(20));// 6s
        mCfsbCallPassiveItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mCfsbCallPassiveItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();

    }
    public void addVoiceCallVo(){
        TestTask mVolteCallActiveItem = new TestTask();
        mVolteCallActiveItem.setTaskid(taskid);
        mVolteCallActiveItem.setId(  models.get(taskid).getTaskList().size()-1);
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
        mVolteCallActiveItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mVolteCallActiveItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addVoicePassVo(){
        TestTask mVolteCallPassiveItem = new TestTask();
        mVolteCallPassiveItem.setTaskid(taskid);
        mVolteCallPassiveItem.setId(  models.get(taskid).getTaskList().size()-1);
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
        mVolteCallPassiveItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mVolteCallPassiveItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addWait(){
        TestTask mWaitItem = new TestTask();
        mWaitItem.setTaskid(taskid);
        mWaitItem.setId(  models.get(taskid).getTaskList().size()-1);
        mWaitItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        mWaitItem.setTaskname("WAIT");
        mWaitItem.setTesttime(String.valueOf(10));// 10s
        mWaitItem.setTesttype(String.valueOf(Const.TestManage.TEST_TYPE_WAIT));
        mWaitItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(mWaitItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }
    public void addIDLE(){
        TestTask midleItem = new TestTask();
        midleItem.setTaskname("IDLE");

        midleItem.setId(  models.get(taskid).getTaskList().size()-1);
        midleItem.setTesttype(Const.TestManage.TEST_TYPE_IDLE);
        midleItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        midleItem.setTesttime(String.valueOf(9));
        midleItem.setComeFromService(false);
        models.get(taskid).getTaskList().add(midleItem);
        TestModelUtil.ModifiTemplate(models);
        notifyDataSetChanged();
    }



}
