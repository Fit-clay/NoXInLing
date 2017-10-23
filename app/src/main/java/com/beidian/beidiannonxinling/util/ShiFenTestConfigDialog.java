package com.beidian.beidiannonxinling.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.ChangeTestModelBean;
import com.beidian.beidiannonxinling.bean.ShiFenBBU_RRUBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.ui.widget.MultiSpinner;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

import static com.beidian.beidiannonxinling.R.id.sp_test_model;

/**
 * Created by Eric on 2017/9/25.
 */

public class ShiFenTestConfigDialog extends Dialog {
    private Spinner spChangeDistrict,spUnitInfo,spFloor,spTestWay,spTestModel,spTestItem,sp_rru;
    private  TextView tv_cancel,tv_confirm;
    private Context mContext;
    private EditText edtLat,edtLng,edtBuildAddress,edtReferenceHight,edtRru,edtRemark;
    private MultiSpinner spChange;
    private TestModelBean   testModelBean;

    public interface ChangeTestModel{
        void getChangeModel(ChangeTestModelBean bean);
    }
    ChangeTestModel testModel;
    public ShiFenTestConfigDialog(@NonNull Context context,BaseInfoTestBean baseInfoTestBean, ChangeTestModelBean changeTestModelBean ) {
        super(context);
        Window window = getWindow();
         window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        initView(context);
        setData(baseInfoTestBean,changeTestModelBean);
        setLinstener(baseInfoTestBean,changeTestModelBean);

    }

    private void initView(Context context) {
        mContext=context;
        setContentView(R.layout.view_popuwindown_test_config);

        spChangeDistrict= (Spinner) findViewById(R.id.sp_change_distract);
        spUnitInfo= (Spinner) findViewById(R.id.sp_unit_info);
        spFloor= (Spinner) findViewById(R.id.sp_floor_info);
        spTestWay= (Spinner) findViewById(R.id.sp_test_way);
        spTestModel= (Spinner) findViewById(sp_test_model);
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_confirm= (TextView) findViewById(R.id.tv_confirm);
        edtLat= (EditText) findViewById(R.id.edt_lat);
        edtLng= (EditText) findViewById(R.id.edt_lng);
        edtBuildAddress= (EditText) findViewById(R.id.edt_build_address);
        edtReferenceHight= (EditText) findViewById(R.id.edt_reference_height);
        edtRru= (EditText) findViewById(R.id.edt_rru);
        edtRemark= (EditText) findViewById(R.id.edt_remark);
        spChange = (MultiSpinner)findViewById(R.id.sp_test_change);
        sp_rru = (Spinner) findViewById(R.id.sp_rru);

    }


    private void setData(BaseInfoTestBean baseInfoTestBean, ChangeTestModelBean changeTestModelBean) {

        String[]  is_rru_array = mContext.getResources().getStringArray(R.array.is_rru);
        String[]  unit = mContext.getResources().getStringArray(R.array.unit);
        String[]  testModels = mContext.getResources().getStringArray(R.array.test_model);
        initSpinner(spUnitInfo,unit);
        String [] floors=new String[62];
        floors[0]="B2";
        floors[1]="B1";
        for(int i=1;i<61;i++){
            floors[i+1]=i+"F";
        }
        initSpinner(spFloor,floors);
        initSpinner(spTestWay, testModels);
        BaseInfoTestBean.SiteInfoBean siteInfoBean=baseInfoTestBean.getSiteInfo();
        String [] distract=new String[siteInfoBean.getCellinfoList().size()];
        for(int i=0;i<siteInfoBean.getCellinfoList().size();i++){
            distract[i]=siteInfoBean.getCellinfoList().get(i).getCellname();
        }
        initSpinner(spChangeDistrict,distract);
        edtLat.setText(baseInfoTestBean.getSiteInfo().getLat());
        edtLng.setText(baseInfoTestBean.getSiteInfo().getLng());
        edtBuildAddress.setText(baseInfoTestBean.getSiteInfo().getAddress());
        edtRru.setText(baseInfoTestBean.getSiteInfo().getRrunum());

        loadData(baseInfoTestBean.getSiteInfo().getWorkorderno());

        if(changeTestModelBean!=null){
            spChangeDistrict.setSelection(Integer.valueOf(changeTestModelBean.getDistractChange()));
            edtLat.setText(changeTestModelBean.getLat());
            edtLng.setText(changeTestModelBean.getLng());
            edtBuildAddress.setText(changeTestModelBean.getAddress());
            for(int i=0;i<floors.length;i++){
                if(!TextUtils.isEmpty(changeTestModelBean.getFloorInfo())&&changeTestModelBean.getFloorInfo().equals(floors[i])){
                    spFloor.setSelection(i);
                }
            }
            for(int i=0;i<testModels.length;i++){
                if(!TextUtils.isEmpty(changeTestModelBean.getTestWay())&&changeTestModelBean.getTestWay().equals(testModels[i])){
                    spFloor.setSelection(i);
                }
            }for(int i=0;i<unit.length;i++){
                if(!TextUtils.isEmpty(changeTestModelBean.getUnit())&&changeTestModelBean.getUnit().equals(unit[i])){
                    spUnitInfo.setSelection(i);
                }
            }
        }


    }

    private void loadData(String workOrderno) {
        testModelBean=new TestModelBean();
        if(TestModelUtil.getModelNewTask()!=null){
            testModelBean.getTemplate().addAll(TestModelUtil.getModelNewTask());
        }
        HttpParams params = new HttpParams();
        params.put("account", BaseApplication.getUserInfo().getUsername());
        params.put("workorderNo",workOrderno);
        OkGo.post(Const.Url.URL_GET_TEST_MODEL_BY_ACCOUNT).tag(mContext).params(params).execute(new ResultCallback(mContext, true) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {

            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                TestModelBean    modelBean = new Gson().fromJson(s, TestModelBean.class);
//                testModelBean= new Gson().fromJson(s, TestModelBean.class);
                testModelBean.getTemplate().addAll(modelBean.getTemplate());
                String[] tests = new String[testModelBean.getTemplate().size()];
                for (int p = 0; p < tests.length; p++) {
                    tests[p] = testModelBean.getTemplate().get(p).getTemplatename();
                }
                initSpinner(spTestModel, tests);
            }
        });

    }
private int index;
    private void setLinstener(final BaseInfoTestBean baseInfoTestBean,final ChangeTestModelBean changeTestModelBean) {
        spChangeDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index=position;
                List<ShiFenBBU_RRUBean> rruBeanList;  rruBeanList=  baseInfoTestBean.getSiteInfo().getCellinfoList().get(position).getRrulist();
                String[] rruStr=new String[rruBeanList.size()];
                for(int i=0;i<rruBeanList.size();i++){
                    rruStr[i]=rruBeanList.get(i).getTitle();
                }
                initSpinner(sp_rru, rruStr);
                if(changeTestModelBean!=null){
                    for(int i=0;i<rruStr.length;i++){
                        if(changeTestModelBean.getRru().getTitle().equals(rruStr[i])){
                            sp_rru.setSelection(i);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ((Activity)mContext).finish();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTestModelBean modelBean=new ChangeTestModelBean();
                modelBean.setDistractChange(String.valueOf(index));
                modelBean.setLat(edtLat.getText().toString());
                modelBean.setLng(edtLng.getText().toString());
                modelBean.setAddress(edtBuildAddress.getText().toString());
                modelBean.setUnit(spUnitInfo.getSelectedItem().toString());
                modelBean.setFloorInfo(spFloor.getSelectedItem().toString());
                modelBean.setReferenceHeight(edtReferenceHight.getText().toString());
                modelBean.setTestWay(spTestWay.getSelectedItem().toString());
//                if(spTestModel.getSelectedItem()!=null){
                    modelBean.setTestModel(spTestModel.getSelectedItem().toString());
//                }
                modelBean.setTestChange(spChange.getCheckedOptions());
                modelBean.setRru(baseInfoTestBean.getSiteInfo().getCellinfoList().get(index).getRrulist().get(sp_rru.getSelectedItemPosition()));
                modelBean.setRemark(edtRemark.getText().toString());
                modelBean.setTestModelBean(testModelBean);
               if(testModel!=null){
                   testModel.getChangeModel(modelBean);
               }
                dismiss();
            }
        });
        spTestModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spChange.setTitle("测试项选择");
                    spChange.setDataList(testModelBean.getTemplate().get(position).getTaskList());
                    spChange.setSelectCount(testModelBean.getTemplate().get(position).getTaskList().size());

                     spChange.setCheckedSet(null);
                if(changeTestModelBean != null){
                    if (changeTestModelBean.getTestModel()!=null) {
//                    if (modelFlag) {
                        //查看或重测时填充默认数据 只走一次
                        for (int n = 0; n < testModelBean.getTemplate().size(); n++) {
                            if (testModelBean.getTemplate().get(n).getTemplatename().equals(changeTestModelBean.getTestModel())) {
                                spTestModel.setSelection(n);
                            }
                        }
                        if (changeTestModelBean.getTestChange() != null && changeTestModelBean.getTestChange().size() > 0) {
                            Set<Object> set = new HashSet<>();
                            for (int i = 0; i < changeTestModelBean.getTestChange().size(); i++) {
                                set.add(changeTestModelBean.getTestChange().get(i).getId());
                            }
                            spChange.setCheckedSet(set);
                            StringBuffer sb = new StringBuffer();

                            for (TestTask testTask : changeTestModelBean.getTestChange() ) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public  void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(mContext, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

    public void setTestModel(ChangeTestModel testModel) {
        this.testModel = testModel;
    }
}
