package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ModelDetailAdapter;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.util.DialogUtil;

import java.util.List;

public class TestModelDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private RecyclerView recyclerView;
    private LinearLayout ll_add,ll_back;
    private ModelDetailAdapter mModelAdapter;
    private List<TestModelBean.TemplateBean> model;
    private int position;
    public ModelDetailAdapter getmAdapter() {
        return mModelAdapter;
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_model_detail);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("模板详情");
        model  = (List<TestModelBean.TemplateBean>) getIntent().getSerializableExtra("model_data");
        position= (int) getIntent().getSerializableExtra("position");
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        ll_add = (LinearLayout) findViewById(R.id.ll_right_button);
        recyclerView = (RecyclerView) findViewById(R.id.model_detail_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {
        if(model.get(position).getTaskList()!=null){
            mModelAdapter = new ModelDetailAdapter(model,mContext,position);
        }else {
            mModelAdapter = new ModelDetailAdapter();
        }
        recyclerView.setAdapter(mModelAdapter);

    }



    @Override
    protected void initListener() {
        ll_add.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_right_button:
                DialogUtil.addModeDialog(mContext);
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
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onPostResume: ");
        super.onResume();
    }
}
