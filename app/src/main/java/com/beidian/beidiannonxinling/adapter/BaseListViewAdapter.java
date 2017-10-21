package com.beidian.beidiannonxinling.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by shanpu on 2017/8/16.
 * <p>
 * ListView数据适配器基类
 */

public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    public List<T> mList;

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getRootView(position, convertView, parent);
    }

    /**
     * 获取view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View getRootView(int position, View convertView, ViewGroup parent);

}
