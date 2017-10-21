package com.beidian.beidiannonxinling.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ModelAdapter;
import com.beidian.beidiannonxinling.adapter.ModelDetailAdapter;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.SPUtils;
import com.beidian.beidiannonxinling.util.TestModelUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class TestModelActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout ll_button,ll_back;
    private static List<TestModelBean.TemplateBean> testModelBean;

    private ModelAdapter modelAdapter;
    private RecyclerView recyclerView;
    public ModelAdapter getmAdapter() {
        return modelAdapter;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_model);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("模板详情");
        ll_button= (LinearLayout) findViewById(R.id.ll_right_button);
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        recyclerView= (RecyclerView) findViewById(R.id.rv_modle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testModelBean=TestModelUtil.getModelNewTask();


        modelAdapter=new ModelAdapter(testModelBean);

        recyclerView.setAdapter(modelAdapter);


    }



    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        ll_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_right_button:
                ToastUtils.makeText(mContext,"弹出新增");
                DialogUtil.modelAddDialog(mContext);
                break;
            case R.id.ll_back:
                finish();
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        modelAdapter.refence();


    }
}
