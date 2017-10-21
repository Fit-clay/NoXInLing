package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.beidian.beidiannonxinling.R;

import java.util.ArrayList;


/**
 * Created by ren·Liu on 2017/6/16.
 */

public class ColorConfigSpinnerAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<String> list = new ArrayList<String>();

    public ColorConfigSpinnerAdapter(Context context){
        layoutInflater= LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position >= list.size() ? null: list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setStringArray(String[] item){
        list.clear();
        if(item !=null){
            for(int i =0; i < item.length;i++){
                list.add(item[i]);
            }
        }
    }

    public void setList(ArrayList<String> mList){
        list = mList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    R.layout.item_color_config_spinner, null);
            holder.tvDisplayName = (TextView) convertView
                    .findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if ( position < list.size()) {
            holder.tvDisplayName.setText(list.get(position));
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    R.layout.item_color_config_spinner, null);
            holder.tvDisplayName = (TextView) convertView
                    .findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if ( position < list.size()) {
            holder.tvDisplayName.setText(list.get(position));
        }
        return convertView;
    }
    static class ViewHolder {
        TextView tvDisplayName;
    }
}
