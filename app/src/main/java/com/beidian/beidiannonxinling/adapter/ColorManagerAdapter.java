package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.util.ColorConfigUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/9/21.
 */

public class ColorManagerAdapter extends RecyclerView.Adapter {
    private List<ColorConfigItem> mList;

    public void add(ColorConfigItem item,int type,Context context){
        if(item!=null){
            System.out.println("列表数据1:"+mList.size());
            mList.add(item);
            System.out.println("列表数据2:"+mList.size());
            ColorConfigUtils.modifeDataBytype(type,mList);
            notifyDataSetChanged();
       }

    }
    public void delete(int position,int type){
            System.out.println("列表数据1:"+mList.size());
            mList.remove(position);
            System.out.println("列表数据2:"+mList.size());
            ColorConfigUtils.modifeDataBytype(type,mList);
            notifyDataSetChanged();


    }
    public ColorManagerAdapter(List<ColorConfigItem> datas) {
        mList = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ColorHolder holder = new ColorHolder(LayoutInflater
                .from(parent.getContext())
                //inflate的第三种放置方法
                .inflate(R.layout.item_color, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ColorHolder colorholder = (ColorHolder) holder;
        final int number = position;
        String lefttype = null, rightType = null;
        colorholder.tv_color_display.setBackgroundColor(mList.get(position).getColor());
        if (mList.get(position).getLeftType() == 0) {
            lefttype = "(";
        } else {
            lefttype = "[";
        }
        if (mList.get(position).getRightType() == 0) {
            rightType = ")";
        } else {
            rightType = "]";
        }
        colorholder.ll_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        colorholder.tv_text_show.setText(lefttype +
                mList.get(position).getMaxValue() + "," + mList.get(position).getMinValue()
                + rightType
        );
        colorholder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("删除前"+mList.size());
                delete(position,mList.get(position).getType());


                System.out.print("删除后"+mList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    class ColorHolder extends RecyclerView.ViewHolder {
        TextView tv_color_display, tv_text_show;
        ImageView iv_delete;
        LinearLayout ll_color;

        public ColorHolder(View itemView) {
            super(itemView);
            tv_color_display = (TextView) itemView.findViewById(R.id.coverage_color_display_view);
            tv_text_show = (TextView) itemView.findViewById(R.id.tv_color_result);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_color_delete);
            ll_color = (LinearLayout) itemView.findViewById(R.id.rl_color_parent);

        }
    }

}
