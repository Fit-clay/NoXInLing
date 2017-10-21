package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.DistrictBean;
import java.util.List;

import static com.beidian.beidiannonxinling.R.id.ll_delete;

/**
 * Created by Eric on 2017/9/13.
 */

public class DistrictPopuWindowAdapter extends TBaseAdapter<DistrictBean> {
    private ImageView imageView;
    private LinearLayout linearLayout;
    public interface DeleteListenes{
        void onDeletePosition(int position);
    }
    DeleteListenes deleteListenes;
    public DistrictPopuWindowAdapter(Context context, List<DistrictBean> listData,  DeleteListenes deleteListenes) {
        super(context, R.layout.item_district_popu, listData);
        this.deleteListenes=deleteListenes;

    }
    @Override
    public void doHandler(ViewHolder holder, final int position, View convertView, ViewGroup parent) {
        imageView=holder.getView(R.id.iv_item_delete);
        TextView textView=holder.getView(R.id.tv_item_district_name);
        linearLayout =holder.getView(ll_delete);
        textView.setText(getItem(position).getName());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListenes.onDeletePosition(position);
            }
        });
        if(position==getCount()-1||!getItem(position).isUserAdd()){
            linearLayout.setVisibility(View.GONE);
        }else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


}
