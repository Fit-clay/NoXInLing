package com.beidian.beidiannonxinling.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.CqtBaseTestBean;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.ShiFenBBU_RRUBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.bean.UpLoadCqtBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.ui.widget.MultiSpinner;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.TestModelUtil;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.ViewUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Eric on 2017/9/4.
 */

public class BaseCqtTestActivity extends BaseActivity implements View.OnClickListener {
    private Spinner spSector, spResult, sp_test_model, sp_model_info, sp_floor,sp_rru;
    private ScrollView sv_test_result;
    private MultiSpinner spChange;
    private EditText edtLng, edtLat, edtAddress, edtRemarks, edtRSRP, edtSINR, edtPING, edtHTTP, edtFtpDown, edtFtpUp, edtVolte, edtCsfb, edtPhone, edtTextResult, edtModel, edit_height, edt_floor_address, edt_rru;
    private TextView title, oneKeyTest, tvComplete, tv_address;
    private LinearLayout llBack, ly_shifen, ly_rru;
    private ImageView loacation;
    private String contentPath, stationName;
    private String jsonFilePath;//txy文件路径
    private String jsonDir;
    private List<CellinfoListBean> cellInfoList;
    private List<CellinfoListBean.LineinfoListBean> lineInfoList;
    private CqtBaseTestBean cqtBean;
    private BaseInfoTestBean.SiteInfoBean siteInfo;
    private BaseInfoTestBean testBean;
    String[] sectorArra;
    LocationClient mLocClient;
    TestModelBean cqtTestModelBean;
    String saveFileName;
    int indexType;
    private ProgressDialog dialog;
    private String workorderNo;
    private boolean modelFlag = true;
    private LinearLayout lv_on_key_test;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                dialog.dismiss();
//              setData();
                fillingTestValue();
            }
        }
    };
    private int changeDistrict = 0;
    String baseInfoAddress;
    String times;
    private boolean isShifen;//是否是室分测试
    private boolean oneKeyFlag;
    List<ShiFenBBU_RRUBean> rruBeanList;
    private SensorManager sensorManager = null;
    Sensor mPressure;
    SensorEventListener pressureListener;
    float currentPressure;
    private float Calibrate = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_result);
        spSector = findView(R.id.sp_sector);
        spResult = findView(R.id.sp_result);
        edtModel = findView(R.id.edt_test_model);
        spChange = findView(R.id.sp_test_change);
        edtLng = findView(R.id.edt_test_result_lng);
        edtLat = findView(R.id.edt_test_result_lat);
        edtRemarks = findView(R.id.edt_remarks);
        edtRSRP = findView(R.id.edt_rsrp);
        edtSINR = findView(R.id.edt_sinr);
        edtPING = findView(R.id.edt_ping);
        edtHTTP = findView(R.id.edt_http);
        edtFtpDown = findView(R.id.edt_ftp_down);
        edtFtpUp = findView(R.id.edt_ftp_up);
        edtVolte = findView(R.id.edt_volte);
        edtCsfb = findView(R.id.edt_csfb);
        edtPhone = findView(R.id.edt_call);
        edtTextResult = findView(R.id.edt_test_result);
        title = findView(R.id.tv_title);
        edtAddress = findView(R.id.edt_test_result_address);
        llBack = findView(R.id.ll_back);
        oneKeyTest = findView(R.id.tv_one_key_test);
        tvComplete = findView(R.id.tv_complete);
        loacation = findView(R.id.iv_location);
        lv_on_key_test = findView(R.id.lv_on_key_test);
        sv_test_result = findView(R.id.sv_test_result);
        sp_test_model = findView(R.id.sp_test_model);
        ly_shifen = findView(R.id.ly_shifen);
        ly_rru = findView(R.id.ly_rru);
        sp_model_info = findView(R.id.sp_model_info);
        sp_floor = findView(R.id.sp_floor);
        tv_address = findView(R.id.tv_address);
        edit_height = findView(R.id.edit_height);
        edt_floor_address = findView(R.id.edt_floor_address);
        edt_rru = findView(R.id.edt_rru);
        sp_rru = findView(R.id.sp_rru);
        initDialog();
    }

    @Override
    protected void initData() {
        cqtBean = new CqtBaseTestBean();

        title.setText(R.string.cqt_test);

        Intent intent = getIntent();
        contentPath = intent.getStringExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH);
        workorderNo = intent.getStringExtra(Const.IntentTransfer.WORKDERNO);
        isShifen = intent.getExtras().getBoolean(Const.IntentTransfer.ORDER_TYPE);
        indexType = Integer.valueOf(intent.getExtras().get(Const.IntentTransfer.TYPE).toString());
        baseInfoAddress = intent.getStringExtra(Const.IntentTransfer.DEFULT_INFO_PATH);
        testBean = (BaseInfoTestBean) intent.getSerializableExtra(Const.IntentTransfer.DEFULT_INFO_BEAN);

        if (isShifen) {
            ly_shifen.setVisibility(View.VISIBLE);
            ly_rru.setVisibility(View.VISIBLE);
            tv_address.setText("楼宇地址");
        } else {
            tv_address.setText("地址");
            ly_shifen.setVisibility(View.GONE);
            ly_rru.setVisibility(View.GONE);
        }
        if (indexType == Const.LOOK) {
            lv_on_key_test.setVisibility(View.GONE);
            tvComplete.setVisibility(View.GONE);
            ViewUtils.disableSubControls(sv_test_result);
            String str = FileUtils.readSDFile(contentPath);
            cqtBean = new Gson().fromJson(str, CqtBaseTestBean.class);
            setData();
        } else {
            loadData();
        }
        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(mPressure == null){
            ToastUtils.makeText(mContext,"您的手机不支持自动测量高度");
            return;
        }
        pressureListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    df.getRoundingMode();
                    currentPressure = Float.parseFloat(df.format(event.values[0]-Calibrate));
                    // 计算海拔
                    double height = 44330000*(1-(Math.pow((Double.parseDouble(df.format(currentPressure))/1013.25),
                            (float)1.0/5255.0)));

                    height=(1018.92-Double.parseDouble(df.format(currentPressure)))*9;
                    edit_height.setText(df.format(height)+" m");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }
        };



    }

    private void loadData() {
        if (!TextUtils.isEmpty(baseInfoAddress)) {
            if (FileUtils.fileIsExists(baseInfoAddress)) {
                String str = FileUtils.readSDFile(baseInfoAddress);
                testBean = new Gson().fromJson(str, BaseInfoTestBean.class);
            }
        }
        if (testBean != null) {
            siteInfo = testBean.getSiteInfo();
            cellInfoList = siteInfo.getCellinfoList();
            if (indexType == Const.RESET) {
                if (FileUtils.fileIsExists(contentPath)) {
                    String strAddress = FileUtils.readSDFile(contentPath);
                    cqtBean = new Gson().fromJson(strAddress, CqtBaseTestBean.class);
                    setData();
                }
                return;
            } else {
                cqtBean.setAddress(siteInfo.getAddress());
                cqtBean.setLat(String.valueOf(siteInfo.getLat()));
                cqtBean.setLng(String.valueOf(siteInfo.getLng()));
                stationName = siteInfo.getSitename();
                setData();
            }

        }
    }

    private void setData() {
        if (siteInfo != null) {
            List<String> stringList = new ArrayList<>();
            for (CellinfoListBean bean : siteInfo.getCellinfoList()) {
                if (!bean.isUserAdd()) {
                    stringList.add(bean.getCellname());
                }
            }
            sectorArra = new String[stringList.size()];
            for (int i = 0; i < sectorArra.length; i++) {
                sectorArra[i] = stringList.get(i);
            }



        } else {
            String sector = TextUtils.isEmpty(cqtBean.getSectorSelect()) ? "      " : cqtBean.getSectorSelect();
            sectorArra = new String[]{sector};
        }
        initSpinner(spSector, sectorArra);


        if (!TextUtils.isEmpty(cqtBean.getSectorSelect())) {
            for (int i = 0; i < sectorArra.length; i++) {
                if (cqtBean.getSectorSelect().equals(sectorArra[i])) {
                    spSector.setSelection(i);
                }
            }
        }


        // TODO: 2017/9/21 单元信息 楼层信息 网络获取？
        initSpinner(sp_model_info, new String[]{"1单元", "2单元", "3单元", "4单元"});
        initSpinner(sp_floor, new String[]{"1F", "2F", "3F", "4F"});


        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.view_spinner_style_result, new String[]{"通过", "未通过"});
        spResult.setAdapter(arrA);
        spResult.setSelection("通过".equals(cqtBean.getItemState()) ? 0 : 1);

        edtLat.setText(cqtBean.getLat());
        edtLng.setText(cqtBean.getLng());
        edtAddress.setText(cqtBean.getAddress());
        edtRemarks.setText(cqtBean.getRemarks());

        edit_height.setText(cqtBean.getEdtHeight());
        edt_floor_address.setText(cqtBean.getFloorAddress());
        edt_rru.setText(cqtBean.getRru());

//        cqtBean.setBuildingInfo(sp_model_info.getSelectedItem().toString());
//        cqtBean.setFloorInfo(sp_floor.getSelectedItem().toString());
//        cqtBean.setEdtHeight(edit_height.getText().toString());
//        cqtBean.setFloorAddress(edt_floor_address.getText().toString());
//        cqtBean.setRru(edt_rru.getText().toString());


        fillingTestValue();
        cqtTestModelBean=new TestModelBean();
        if(TestModelUtil.getModelNewTask()!=null){
            cqtTestModelBean.getTemplate().addAll(TestModelUtil.getModelNewTask());
        }

        HttpParams params = new HttpParams();
        params.put("account", BaseApplication.getUserInfo().getUsername());
        params.put("workorderNo",workorderNo);
        OkGo.post(Const.Url.URL_GET_TEST_MODEL_BY_ACCOUNT).tag(mContext).params(params).execute(new ResultCallback(mContext, true) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {

            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                TestModelBean   bean = new Gson().fromJson(s, TestModelBean.class);
                cqtTestModelBean.getTemplate().addAll(bean.getTemplate());
                String[] tests = new String[cqtTestModelBean.getTemplate().size()];
                for (int p = 0; p < tests.length; p++) {
                    tests[p] = cqtTestModelBean.getTemplate().get(p).getTemplatename();
                }
                initSpinner(sp_test_model, tests);
            }
        });
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        oneKeyTest.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        loacation.setOnClickListener(this);

        spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你选择的是" + sectorArra[position], Toast.LENGTH_SHORT).show();
                cqtBean.setSectorSelect(sectorArra[position]);
                changeDistrict = position;
                times = TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");

                if (indexType == Const.ADD) {
                    if(isShifen){
                        jsonDir = FileUtils.getFileDir(Const.SaveFile.getDir(workorderNo, getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH), "rru_"+sectorArra[position]+ times));
                    }else {
                        jsonDir = FileUtils.getFileDir(Const.SaveFile.getDir(workorderNo, getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH), sectorArra[position] + times));
                    }
                } else {
                    jsonDir = contentPath.substring(0, contentPath.lastIndexOf("/"));
                }
                if(siteInfo!=null){
                    rruBeanList=  siteInfo.getCellinfoList().get(position).getRrulist();
                    String[] rruStr=new String[rruBeanList.size()];
                    for(int i=0;i<rruBeanList.size();i++){
                        rruStr[i]=rruBeanList.get(i).getTitle();
                    }
                    initSpinner(sp_rru, rruStr);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_test_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cqtTestModelBean != null) {
                    spChange.setTitle("测试项选择");
                    spChange.setDataList(cqtTestModelBean.getTemplate().get(position).getTaskList());
                    spChange.setSelectCount(cqtTestModelBean.getTemplate().get(position).getTaskList().size());
                    spChange.setCheckedSet(null);
                    if (indexType != Const.ADD) {
                        if (modelFlag) {
                            //查看或重测时填充默认数据 只走一次
                            for (int n = 0; n < cqtTestModelBean.getTemplate().size(); n++) {
                                if (cqtTestModelBean.getTemplate().get(n).getTemplatename().equals(cqtBean.getTestModel())) {
                                    sp_test_model.setSelection(n);
                                }
                            }
                            modelFlag = false;
                            if (cqtBean != null && cqtBean.getTestTaskList() != null && cqtBean.getTestTaskList().size() > 0) {
                                Set<Object> set = new HashSet<>();
                                for (int i = 0; i < cqtBean.getTestTaskList().size(); i++) {
                                    set.add(cqtBean.getTestTaskList().get(i).getId());
                                }
                                spChange.setCheckedSet(set);
                                StringBuffer sb = new StringBuffer();

                                for (TestTask testTask : cqtBean.getTestTaskList()) {
                                    sb.append(testTask.getTesttype()).append(",");
                                }
                                if (sb.length() > 0) {
                                    sb.setLength(sb.length() - 1);
                                }
                                spChange.setText(sb.toString());


                            }
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_one_key_test:
                ViewUtils.PreventDuplicateClicks(oneKeyTest, 3000);  //防止重复点击
                if (!spChange.getCheckedOptions().isEmpty() && spChange.getCheckedOptions().size() > 0) {         //是否选择测试项
                    cqtBean.setTestTaskList(spChange.getCheckedOptions());
                } else {
                    DialogUtil.showConfirmDialog(mContext, "请选择测试项");
                    return;
                }
                stationName = cqtBean.getSectorSelect();
                if (indexType == Const.ADD) {                                       //确定文件保存地址
                    if (TextUtils.isEmpty(saveFileName)) {
                        saveFileName = stationName + times;
                    }
                } else {
                    saveFileName = new File(contentPath).getName();
                }
                Intent testIntent = new Intent(mContext, CqtTestActivity.class);
                testIntent.putExtra(Const.IntentTransfer.ONE_KEY_TEST, new OneKeyTestBean(cqtBean.getSectorSelect(), workorderNo, cqtBean.getTestTaskList(), saveFileName, jsonDir, testBean, changeDistrict,null));
                startActivityForResult(testIntent, Const.IntentTransfer.resultCode_CqtTestActivityActivity);
                break;
            case R.id.iv_location:
                mLocClient = new LocationClient(this);
                mLocClient.registerLocationListener(new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        if (bdLocation == null) {
                            return;
                        }
                        edtLat.setText(String.valueOf(bdLocation.getLatitude()));
                        edtLng.setText(String.valueOf(bdLocation.getLongitude()));
                        double mCurrentLon = bdLocation.getLongitude();
                        Log.e(TAG, "onReceiveLocation: " + bdLocation.getLongitude());
                        Log.e(TAG, "onReceiveLocation: " + bdLocation.getLatitude());
                        edtAddress.setText(bdLocation.getAddress().address);
                        if (mLocClient.isStarted()) {
                            mLocClient.stop();
                        }
                    }
                });
                mLocClient.setLocOption(MapUtil.setLocationOption());
                mLocClient.start();
                break;
            case R.id.tv_complete:
                stationName = cqtBean.getSectorSelect();
                cqtBean.setItemName(stationName + "_" + times);
                cqtBean.setItemState(spResult.getSelectedItem().toString());
                cqtBean.setTestName(edtModel.getText().toString());
                cqtBean.setTestTaskList(spChange.getCheckedOptions());
                cqtBean.setRemarks(edtRemarks.getText().toString());

                cqtBean.setBuildingInfo(sp_model_info.getSelectedItem().toString());
                cqtBean.setFloorInfo(sp_floor.getSelectedItem().toString());
                cqtBean.setEdtHeight(edit_height.getText().toString());
                cqtBean.setFloorAddress(edt_floor_address.getText().toString());
                cqtBean.setRru(edt_rru.getText().toString());
                cqtBean.setBbuRruBean(siteInfo.getCellinfoList().get(changeDistrict).getRrulist().get(sp_rru.getSelectedItemPosition()));

                UpLoadCqtBean upLoadCqtBean=new UpLoadCqtBean();
                upLoadCqtBean.setRruNum(cqtBean.getBbuRruBean().getTitle());
                upLoadCqtBean.setRruAddress(cqtBean.getBbuRruBean().getInfactAdress());
                upLoadCqtBean.setLineCi(siteInfo.getCellinfoList().get(changeDistrict).getCelleci());
                upLoadCqtBean.setLinePci(siteInfo.getCellinfoList().get(changeDistrict).getPci());
                upLoadCqtBean.setLineEarfcn(siteInfo.getCellinfoList().get(changeDistrict).getEarfcn());
                upLoadCqtBean.setLineDown(cqtBean.getFtpDown());
                upLoadCqtBean.setLineUpload(cqtBean.getFtpUp());



                if (null != sp_test_model.getSelectedItem()) {
                    cqtBean.setTestModel(sp_test_model.getSelectedItem().toString());
                }
                JSON json = (JSON) JSON.toJSON(cqtBean);
                if (indexType == Const.ADD) {
                    if (TextUtils.isEmpty(saveFileName)) {
                        saveFileName = stationName;
                        if(isShifen){
                            jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(jsonDir, times, "rru_"+saveFileName);

                        }else {
                            jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(jsonDir, times, saveFileName);
                        }
                    } else {
                        if(isShifen){
                            jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(jsonDir, "", "rru_"+saveFileName);
                        }else {
                            jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(jsonDir, "", saveFileName);
                        }
                    }
                    FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
                } else {
                    FileUtils.saveFile(mContext, json.toString(), contentPath);

                   if(FileUtils.fileIsExists(jsonDir)){
                       new File(jsonDir).delete();
                   }
                }
                if(isShifen){
                    String upPath = Const.SaveFile.getJsonAbsoluteFilePaths(FileUtils.getFileDir(Const.SaveFile.getDir(workorderNo, "","" )),"", Const.SaveFile.CQTRRULIST);
                    if(indexType == Const.ADD){
                        upLoadCqtBean.setTestName(new File(jsonFilePath).getName());
                        JSON upJson = (JSON) JSON.toJSON(upLoadCqtBean);
                        FileUtils.saveFile(mContext, upJson.toString()+"\n",upPath,true);
                    }else {
                        List<String> mList = FileUtils.readFileOnLine(upPath);
                        for(int i=0;i<mList.size();i++){
                            try {
                                JSONObject jsonObject=new JSONObject(mList.get(i));
                                if(jsonObject.get("testName").equals(new File(contentPath).getName())){
                                    mList.remove(i);
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        upLoadCqtBean.setTestName(new File(contentPath).getName());
                        JSON upJson = (JSON) JSON.toJSON(upLoadCqtBean);
                        new File(upPath).delete();
                        FileUtils.saveFile(mContext, upJson.toString()+"\n",upPath,true);
                        for(String item:mList){
                            FileUtils.saveFile(mContext, item+"\n",upPath,true);
                        }

                    }


                }

                finish();
                break;
        }
    }

    private void initDialog() {
        dialog = new ProgressDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(Const.NetConst.NET_LOADING);

    }

    public String getCalculationAverage(String path, String type) {
        double count = 0;
        List<String> mList = FileUtils.readFileOnLine(path);
        if (mList == null) {
            return "";
        }
        for (String lineStr : mList) {
            try {
                JSONObject jsonObject = new JSONObject(lineStr);
                String str = jsonObject.get(type).toString();
                if (!TextUtils.isEmpty(str)) {
                    count += Double.valueOf(str);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(count / mList.size());
    }

    public String getLastValue(String path, String type) {
        String count = "";
        List<String> mList = FileUtils.readFileOnLine(path);
        if (mList == null) {
            return "";
        }
        try {
            JSONObject jsonObject = new JSONObject(mList.get(mList.size() - 1));
            count += jsonObject.get(type).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void fillingTestValue() {
        if (cqtBean != null) {
            edtRSRP.setText(TextUtils.isEmpty(cqtBean.getRsrp()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getRsrp())));
            edtSINR.setText(TextUtils.isEmpty(cqtBean.getSinr()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getSinr())));
            edtPING.setText(TextUtils.isEmpty(cqtBean.getPing()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getPing())) + "ms");
            edtHTTP.setText(TextUtils.isEmpty(cqtBean.getHttp()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getHttp())) + "kb/s");
            edtFtpDown.setText(TextUtils.isEmpty(cqtBean.getFtpDown()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getFtpDown())) + "kb/s");
            edtFtpUp.setText(TextUtils.isEmpty(cqtBean.getFtpUp()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getFtpUp())) + "kb/s");
            edtVolte.setText(TextUtils.isEmpty(cqtBean.getVolte()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getVolte())) + "%");
            edtCsfb.setText(TextUtils.isEmpty(cqtBean.getCsfb()) ? " " : String.format("%.2f", Double.valueOf(cqtBean.getCsfb())) + "%");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(FileUtils.fileIsExists(jsonDir)){
            new File(jsonDir).delete();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(pressureListener, mPressure,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(pressureListener!=null){
            sensorManager.unregisterListener(pressureListener);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Const.IntentTransfer.resultCode_CqtTestActivityActivity) {
                oneKeyFlag = true;
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        String average = getCalculationAverage(jsonDir + Const.FileName.SIGNALLING, "rsrp");
                        cqtBean.setRsrp(average);
                        cqtBean.setSinr(getCalculationAverage(jsonDir + Const.FileName.SIGNALLING, "sinr"));
                        cqtBean.setFtpUp(getCalculationAverage(jsonDir + Const.FileName.FTP_UP, "speed"));
                        cqtBean.setFtpDown(getCalculationAverage(jsonDir + Const.FileName.FTP_DOWN, "speed"));
                        cqtBean.setPing(getCalculationAverage(jsonDir + Const.FileName.PING, "delay"));
                        cqtBean.setHttp(getCalculationAverage(jsonDir + Const.FileName.HTTP, "avgSpeed"));
                        cqtBean.setVolte(getLastValue(jsonDir + Const.FileName.CALL_VOLTEZ, "successRate"));
                        cqtBean.setCsfb(getLastValue(jsonDir + Const.FileName.CALL_CSFBZ, "successRate"));
                        Message message = handler.obtainMessage();
                        message.arg1 = 1;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        }
    }
}
