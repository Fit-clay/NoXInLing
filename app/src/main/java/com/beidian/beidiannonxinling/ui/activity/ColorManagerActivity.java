package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ToastUtils;

public class ColorManagerActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_title;
    LinearLayout ll_back, ll_pci, ll_rsrp, ll_sinr, ll_app_dl, ll_app_ul;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main2);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("图例管理");
        ll_pci = (LinearLayout) findViewById(R.id.ll_pci);
        ll_rsrp = (LinearLayout) findViewById(R.id.ll_rsrp);
        ll_sinr = (LinearLayout) findViewById(R.id.ll_sinr);
        ll_app_dl = (LinearLayout) findViewById(R.id.ll_app_dl);
        ll_app_ul = (LinearLayout) findViewById(R.id.ll_app_ul);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        ll_pci.setOnClickListener(this);
        ll_rsrp.setOnClickListener(this);
        ll_sinr.setOnClickListener(this);
        ll_app_dl.setOnClickListener(this);
        ll_app_ul.setOnClickListener(this);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int type = 0;
        switch (v.getId()) {
            case R.id.ll_pci:
                ToastUtils.makeText(mContext, "pci");
                Intent intent = new Intent(ColorManagerActivity.this, ColorManagerDetails.class);
                type = Const.ColorManager.COVERAGE_PCI;
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.ll_rsrp:
                ToastUtils.makeText(mContext, "rsrp");
                Intent intent2 = new Intent(ColorManagerActivity.this, ColorManagerDetails.class);
                type = Const.ColorManager.COVERAGE_RSRP;
                intent2.putExtra("type", type);
                startActivity(intent2);
                break;
            case R.id.ll_sinr:
                ToastUtils.makeText(mContext, "tv_sinr");
                Intent intent4 = new Intent(ColorManagerActivity.this, ColorManagerDetails.class);
                type = Const.ColorManager.COVERAGE_SINR;
                intent4.putExtra("type", type);
                startActivity(intent4);
                break;
            case R.id.ll_app_dl:
                ToastUtils.makeText(mContext, "tv_app_dl");
                Intent intent5 = new Intent(ColorManagerActivity.this, ColorManagerDetails.class);
                type = Const.ColorManager.COVERAGE_DL;
                intent5.putExtra("type", type);
                startActivity(intent5);
                break;
            case R.id.ll_app_ul:
                ToastUtils.makeText(mContext, "tv_app_ul");
                Intent intent6 = new Intent(ColorManagerActivity.this, ColorManagerDetails.class);
                type = Const.ColorManager.COVERAGE_UL;
                intent6.putExtra("type", type);
                startActivity(intent6);
                break;

        }
    }
}
