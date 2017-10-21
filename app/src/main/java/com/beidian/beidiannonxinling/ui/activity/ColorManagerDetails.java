package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ColorManagerAdapter;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.util.ColorConfigUtils;
import com.beidian.beidiannonxinling.util.DialogUtil;

import java.util.List;

public class ColorManagerDetails extends BaseActivity {
    private RecyclerView recyclerView;
    private ColorManagerAdapter mAdapter;
    TextView tv_title;
    ImageView iv_add;
    LinearLayout ll_back, ll_right_button, bt_add;
    private int type = 0;

    public ColorManagerAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(ColorManagerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main3);
        iv_add = (ImageView) findViewById(R.id.iv_right_button);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("图例管理");
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_right_button = (LinearLayout) findViewById(R.id.ll_right_button);
        recyclerView = (RecyclerView) findViewById(R.id.coverage_detail_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        String result;
        List<ColorConfigItem> colors;
        colors = ColorConfigUtils.getColorItemBean(type);
        if (colors != null) {
            System.out.print("读入数据值" + colors.size());
            mAdapter = new ColorManagerAdapter(colors);
        }
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler;

                DialogUtil.showColorDialogwithhanlder(mContext, type);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.print("读入数据值");
    }
}
