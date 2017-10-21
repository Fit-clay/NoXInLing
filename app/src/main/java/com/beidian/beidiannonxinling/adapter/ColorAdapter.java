package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;


/**
 * Created by ren·Liu on 2017/6/16.
 */

public class ColorAdapter extends BaseAdapter {
    private int[] color;
    private Context context;
    private Handler handler;
    public ColorAdapter(Context context, int[] color, Handler handler){
        this.color=color;
        this.context=context;
        this.handler=handler;
    }
    @Override
    public int getCount() {
        return color.length;
    }

    @Override
    public Object getItem(int position) {
        return color[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(
                    R.layout.gv_item_color, null);

            holder.color = (LinearLayout) convertView
                    .findViewById(R.id.linearlayout_item_color);
            holder.tv = (View) convertView.findViewById(R.id.tv_item_color);
            holder.tv.setBackgroundColor(color[position]);

            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color=((Integer) view.getTag()).intValue();
                    Message m= Message.obtain();
                    m.what= Const.ColorManager.COVERAGE_REFRESH_SELECTED_COLOR;
                    m.arg1=color;
                    handler.sendMessage(m);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setTag(color[position]);

        return convertView;
    }
    class ViewHolder {
        LinearLayout color;
        View tv;
    }
}
