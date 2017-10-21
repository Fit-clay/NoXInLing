package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.TestModelBean;

import java.util.List;

/**
 * Created by Eric on 2017/9/19.
 */

public class TestModelAdapter extends TBaseAdapter<TestModelBean.TemplateBean> {

    public TestModelAdapter(Context context, List<TestModelBean.TemplateBean> listData) {
        super(context, R.layout.view_test_model ,listData);
    }

    @Override
    public void doHandler(ViewHolder holder, int position, View convertView, ViewGroup parent) {
        TextView textView=  holder.getView(R.id.tv_test_model);
        textView.setText(getItem(position).getTemplatename());
    }
}
