package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.DistrictPopuWindowAdapter;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.DistrictBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.fragment.ConstructionFragment;
import com.beidian.beidiannonxinling.ui.fragment.ShiFenBaseInfoFragment;
import com.beidian.beidiannonxinling.ui.widget.DistrictPopuWindown;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.FragmentActivityHelper;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/21.
 */

public class ShiFenBaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private FragmentActivityHelper faHelper;
    private FrameLayout frame;
    private TextView tvTitle,tvSiteTitle,tv_net,tv_operator;
    private RadioButton rg_base,rg_construction;
    private LinearLayout ll_back;
    public  BaseInfoTestBean baseInfoTestBean;
    private ImageView ivMenu;
    private LinearLayout llMenu;
    public static BaseInfoTestBean.SiteInfoBean siteInfo;
    private List<CellinfoListBean> cellInfoList;

    private Fragment flowFragment;

    private DistrictPopuWindown popuWindown;
    List<DistrictBean> distrisctList;

    private String workorder;
    private String baseInfoPath;  //模板文件/查看时的文件位置
    private int indexType ;       //查看  新增  重测
    private String stationName ;  //文件名称
    private int index=0;
    private String fileDir;
    private  String  times;
    private Button btnSave;
    LoadingDialog loadingDialog;
    private String defultFilePath;  //服务器下发基站模板信息

    @Override
    protected void initView() {
    setContentView(R.layout.activity_shifen_base_info);
        frame=findView(R.id.frame);
        ll_back=findView(R.id.ll_back);
        tvTitle=findView(R.id.tv_title);
        tvSiteTitle=findView(R.id.tv_site_title);
        tv_net=findView(R.id.tv_net);
        tv_operator=findView(R.id.tv_operator);
        rg_base=findView(R.id.rg_base);
        rg_construction=findView(R.id.rg_construction);
        ivMenu=findView(R.id.iv_right_button);
        llMenu=findView(R.id.ll_right_button);
        btnSave=findView(R.id.btn_save);
            loadingDialog= DialogUtil.showLodingDialog(mContext,"加载中");

        faHelper = new FragmentActivityHelper(this, new Class[]{ShiFenBaseInfoFragment.class, ConstructionFragment.class}, R.id.frame);
        flowFragment= faHelper.onChanged(0);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        workorder = intent.getStringExtra(Const.IntentTransfer.WORKDERNO);
        baseInfoPath = intent.getStringExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH);
//        fileDir = intent.getStringExtra(Const.IntentTransfer.FILE_PATH);
        indexType = Integer.valueOf(intent.getExtras().get(Const.IntentTransfer.TYPE).toString());
        defultFilePath=intent.getStringExtra(Const.IntentTransfer.DEFULT_INFO_PATH);

        ivMenu.setImageResource(R.mipmap.icon_menu);
        if (!TextUtils.isEmpty(baseInfoPath)) {
            if (FileUtils.fileIsExists(baseInfoPath)) {
                String str = FileUtils.readSDFile(baseInfoPath);
                Gson gson = new Gson();
                baseInfoTestBean = gson.fromJson(str, BaseInfoTestBean.class);//拿到要展示的bean
                siteInfo = baseInfoTestBean.getSiteInfo();
                cellInfoList=siteInfo.getCellinfoList();
                stationName = siteInfo.getSitename();
                setData();
            }
        }else {
            if (FileUtils.fileIsExists(defultFilePath)) {
                String str = FileUtils.readSDFile(defultFilePath);
                Gson gson = new Gson();
                baseInfoTestBean = gson.fromJson(str, BaseInfoTestBean.class);//拿到要展示的bean
                siteInfo = baseInfoTestBean.getSiteInfo();
                cellInfoList=siteInfo.getCellinfoList();
                stationName = siteInfo.getSitename();
                setData();
            }
        }
    }

    private void setData() {
        tvTitle.setText("室分基础信息");
        if(siteInfo!=null){
            if(indexType==Const.LOOK){
                btnSave.setVisibility(View.GONE);
            }
            if(indexType==Const.ADD){
                times = TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
                fileDir = FileUtils.getFileDir(Const.SaveFile.getDir(workorder, getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH), stationName+times));//当前页面要存的文件夹
                }else {
                fileDir=baseInfoPath.substring(0,baseInfoPath.lastIndexOf("/"));
                }

            String title= String.format(getResources().getString(R.string.site_title),siteInfo.getSitename());
            String netType= String.format(getResources().getString(R.string.nettype),siteInfo.getNetstandard());
            String operator= String.format(getResources().getString(R.string.operator_),siteInfo.getOperator());
            tvSiteTitle.setText(title);
            tv_net.setText(netType);
            tv_operator.setText(operator);
            loadingDialog.show();
            if(flowFragment instanceof ShiFenBaseInfoFragment){
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       ((ShiFenBaseInfoFragment)flowFragment).setData(siteInfo,index,fileDir,indexType);
                       loadingDialog.dismiss();
                   }
               },500);
            }
        }
        distrisctList = new ArrayList<>();

        for (int i = 0; i < siteInfo.getCellinfoList().size(); i++) {
            CellinfoListBean bean1=siteInfo.getCellinfoList().get(i);
            distrisctList.add(new DistrictBean("小区"+bean1.getId(),bean1.isUserAdd()));
        }
        if(indexType!=Const.LOOK){
            distrisctList.add(new DistrictBean("新增小区",false));
        }
        if (popuWindown == null) {
            popuWindown = new DistrictPopuWindown(this, distrisctList, new DistrictPopuWindowAdapter.DeleteListenes() {
                @Override
                public void onDeletePosition(int position) {
                    // TODO: 2017/9/13 删除
                    cellInfoList.remove(position);
                    index = position - 1;
                    setData();
                    popuWindown.setList(distrisctList);
                    popuWindown.dismiss();
                    if(flowFragment instanceof ShiFenBaseInfoFragment){
                        ((ShiFenBaseInfoFragment)flowFragment).setData(siteInfo,index,fileDir,indexType);
                    }
                }
            }, new DistrictPopuWindown.ItemClickListener() {
                @Override
                public void onItenClick(int position) {
                    if (position == distrisctList.size() - 1) {
                        if(indexType!=Const.LOOK){
                            CellinfoListBean cellinfoListBean = new CellinfoListBean();
                            cellinfoListBean.setUserAdd(true);
                            cellinfoListBean.setId(cellInfoList.get(cellInfoList.size()-1).getId()+1);
                            cellInfoList.add(cellinfoListBean);
                            index = distrisctList.size() - 1;
                            setData();
                            if(flowFragment instanceof ShiFenBaseInfoFragment){
                                ((ShiFenBaseInfoFragment)flowFragment).setData(siteInfo,index,fileDir,indexType);
                            }
                            popuWindown.setList(distrisctList);
                        }else {
                            index = position;
                            if(flowFragment instanceof ShiFenBaseInfoFragment){
                                ((ShiFenBaseInfoFragment)flowFragment).setData(siteInfo,index,fileDir,indexType);
                            }
                        }
                    } else {
                        index = position;
                        setData();
                    }
                    popuWindown.dismiss();
                }
            });
        }
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        rg_construction.setOnClickListener(this);
        rg_base.setOnClickListener(this);
        llMenu.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.rg_base:
                flowFragment= faHelper.onChanged(0);
                break;
            case R.id.rg_construction:
                if(flowFragment instanceof ShiFenBaseInfoFragment){
                    ((ShiFenBaseInfoFragment)flowFragment).saveInfo();
                }
                flowFragment=faHelper.onChanged(1);
                if(flowFragment instanceof ConstructionFragment){
                    loadingDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((ConstructionFragment)flowFragment).setData(siteInfo,indexType);
                            loadingDialog.dismiss();
                        }
                    },300);
                }
                break;
            case R.id.ll_right_button:
                popuWindown.showAsDropDown(llMenu, 0, 0);
                break;
            case R.id.btn_save:
                if(flowFragment instanceof ShiFenBaseInfoFragment){
                    ((ShiFenBaseInfoFragment)flowFragment).saveInfo();
                }else if(flowFragment instanceof ConstructionFragment){
                        ((ConstructionFragment)flowFragment).saveInfo();
                    }
              String jsonFilePath= Const.SaveFile.getJsonAbsoluteFilePaths(fileDir, times, stationName);
                if (indexType == Const.ADD) {
                    siteInfo.setItemname(stationName  + times);
                } else {
                    jsonFilePath = baseInfoPath;
                }
                JSON json = (JSON) JSON.toJSON(baseInfoTestBean);
                FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(flowFragment instanceof ShiFenBaseInfoFragment){
            ((ShiFenBaseInfoFragment)flowFragment).onFragmentResult();
        }
    }

    @Override
    public void onBackPressed() {
        if(faHelper.getCurrentIndex()==0){
            if(indexType==Const.ADD){
                if(FileUtils.fileIsExists(fileDir)){
                    new File(fileDir).delete();
                }
            }
            super.onBackPressed();

        }else {
            if(flowFragment instanceof ConstructionFragment){
                ((ConstructionFragment)flowFragment).saveInfo();
            }
            flowFragment= faHelper.onChanged(0);

        }

    }
}
