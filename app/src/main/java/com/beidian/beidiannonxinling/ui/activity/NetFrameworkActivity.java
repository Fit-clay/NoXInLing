package com.beidian.beidiannonxinling.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.NetFramework;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.google.gson.Gson;

import java.io.File;


public class NetFrameworkActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout ll_back;
    TextView tv_title;
    Button button;
    String[] strSpin;
    Spinner spinner_near,spinner_height,spinner_overlap,spinner_far;
    String filePath;//文件夹地址
    private String baseInfoPath;
    String jsonFile;//json文件地址
    String stationName="网状结构";//文件名称
    NetFramework netFramework;
    private String times;
    private int indexType;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_framework);
        ll_back = findView(R.id.ll_back);
        tv_title = findView(R.id.tv_title);
        button = findView(R.id.btn_net);
        spinner_near=findView(R.id.spinner_near);
        spinner_height=findView(R.id.spinner_height);
        spinner_overlap=findView(R.id.spinner_overlap);
        spinner_far=findView(R.id.spinner_far);

    }

    @Override
    protected void initData() {
        tv_title.setText("网状结构检查");
        strSpin=getResources().getStringArray(R.array.net_framework_state);
        initSpinner(spinner_near,strSpin);
        initSpinner(spinner_height,strSpin);
        initSpinner(spinner_overlap,strSpin);
        initSpinner(spinner_far,strSpin);
        netFramework=new NetFramework();
        filePath=getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH);
        baseInfoPath = getIntent().getStringExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH);

        times= TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
        indexType = Integer.valueOf(getIntent().getExtras().get(Const.IntentTransfer.TYPE).toString());

            if(indexType==Const.RESET){
                if(FileUtils.fileIsExists(baseInfoPath)){
                    String strs = FileUtils.readSDFile(baseInfoPath);
                    Gson gsons = new Gson();
                    netFramework = gsons.fromJson(strs, NetFramework.class);
                }
            }else {
                jsonFile= FileUtils.getFileDir(Const.SaveFile.getDir(getIntent().getStringExtra(Const.IntentTransfer.WORKDERNO),filePath, stationName+times));
            }
        setSpinnerData();
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.spinner_styles, strArra);
        spinners.setAdapter(arrA);
    }
    public void  setSpinnerData(){
        if (!TextUtils.isEmpty(netFramework.getNear())) {
            spinner_near.setSelection(netFramework.getNear().equals("通过") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(netFramework.getNear())) {
            spinner_height.setSelection(netFramework.getHeight().equals("通过") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(netFramework.getNear())) {
            spinner_overlap.setSelection(netFramework.getOverlap().equals("通过") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(netFramework.getNear())) {
            spinner_far.setSelection(netFramework.getFar().equals("通过") ? 0 : 1, true);
        }
    }

    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_net:

                String nearValue=spinner_near.getSelectedItem().toString();
                String heightValue=spinner_height.getSelectedItem().toString();
                String overlapValue=spinner_overlap.getSelectedItem().toString();
                String far=spinner_far.getSelectedItem().toString();
                jsonFile = Const.SaveFile.getJsonAbsoluteFilePaths(jsonFile, times, stationName);

                netFramework.setNear(nearValue);
                netFramework.setHeight(heightValue);
                netFramework.setOverlap(overlapValue);
                netFramework.setFar(far);
               netFramework.setItemName(stationName+times);

                JSON json= (JSON) JSON.toJSON(netFramework);
                if(indexType==Const.RESET){
                    jsonFile=baseInfoPath;
                }
                FileUtils.saveFile(NetFrameworkActivity.this, json.toString(), jsonFile);
                finish();
                break;
            default:
                break;
        }
    }
}
