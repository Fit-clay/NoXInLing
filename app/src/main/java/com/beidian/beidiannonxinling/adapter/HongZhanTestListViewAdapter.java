package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.BaseTestBean;

import java.util.List;

/**
 * Created by shanpu on 2017/8/30.
 * <p>
 */

public class HongZhanTestListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaseTestBean.ParentListBean.ChildListBean> mData;

    public HongZhanTestListViewAdapter(Context context, List<BaseTestBean.ParentListBean.ChildListBean> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_listview_hongzhantest_result, null);
            viewHolder.tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BaseTestBean.ParentListBean.ChildListBean bean = mData.get(position);
        viewHolder.tv_itemName.setText(bean.getItemName());
        viewHolder.tv_state.setText(bean.getItemState());

        return convertView;
    }

    static class ViewHolder {
        private TextView tv_itemName;//条目名称
        private TextView tv_state;//状态(通过)
    }
}
