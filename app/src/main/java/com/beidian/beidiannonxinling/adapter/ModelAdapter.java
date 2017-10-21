package com.beidian.beidiannonxinling.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.ui.activity.TestModelDetailActivity;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.TestModelUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ASUS on 2017/9/26.
 */

public class ModelAdapter extends RecyclerView.Adapter {
    List<ModelBean> mList;
    List<TestModelBean.TemplateBean> mbean;

    public void refence() {
        mbean=TestModelUtil.getModelNewTask();
    }

    public ModelAdapter( List<TestModelBean.TemplateBean> datas){
        mbean=datas;

    }
    public void Modify(List<ModelBean> datas){
        mList=datas;
        notifyDataSetChanged();

    }

    public void add(TestModelBean.TemplateBean datas){
        mbean.add(datas);


        TestModelUtil.ModifiTemplate(mbean);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ModelHolder holder = new ModelHolder(LayoutInflater
                .from(parent.getContext())
                //inflate的第三种放置方法
                .inflate(R.layout.item_model, parent, false));
        return holder;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
       final ModelHolder modelHolder= (ModelHolder) holder;

        modelHolder.tv_modelname.setText(mbean.get(position).getTemplatename());

        mbean.get(position).setId(position);

        modelHolder.item_model_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbean.remove(position);
                LogUtils.i("TAG"+position);
                notifyDataSetChanged();
                TestModelUtil.ModifiTemplate(mbean);

            }
        });
        modelHolder.ll_model_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(), TestModelDetailActivity.class);


                intent.putExtra("model_data", (Serializable) mbean);
                intent.putExtra("position",position);
                v.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        if(mbean!=null){
            return mbean.size();
        }
        return -1;
    }


    class ModelHolder extends RecyclerView.ViewHolder {
        TextView tv_modelname;
        LinearLayout ll_model_item;
        ImageView item_model_delete;

        public ModelHolder(View itemView) {
            super(itemView);
            tv_modelname= (TextView) itemView.findViewById(R.id.tv_model);
            ll_model_item= (LinearLayout) itemView.findViewById(R.id.ll_model_item);
            item_model_delete= (ImageView) itemView.findViewById(R.id.item_model_delete);
        }
    }

}
