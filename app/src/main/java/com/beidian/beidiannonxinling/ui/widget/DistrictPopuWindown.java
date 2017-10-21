package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.DistrictPopuWindowAdapter;
import com.beidian.beidiannonxinling.bean.DistrictBean;

import java.util.List;


/**
 * Created by Eric on 2017/9/13.
 */

public class DistrictPopuWindown extends PopupWindow {
    Context mContext;
    ListView listView;
    DistrictPopuWindowAdapter adapter;
    List<DistrictBean> list;
    public interface ItemClickListener{
        void onItenClick(int position);
    }
    ItemClickListener itemClick;
    public DistrictPopuWindown(Context context, List<DistrictBean> list, DistrictPopuWindowAdapter.DeleteListenes deleteListenes, ItemClickListener listener){
        mContext=context;
        this.list=list;
        this.itemClick=listener;
        View customView = LayoutInflater.from(mContext).inflate(R.layout.view_district_popu,null, false);
        listView=(ListView) customView.findViewById(R.id.lv_district_popu);
        adapter=new DistrictPopuWindowAdapter(mContext,list,deleteListenes);
        listView.setAdapter(adapter);
        doListener();
        setWidth(450);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setContentView(customView);
    }

    private void doListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClick.onItenClick(position);
            }
        });
    }

    public void setList( List<DistrictBean> mList){
        list.clear();
        list.addAll(mList);
        adapter.notifyDataSetChanged();
    }


}
