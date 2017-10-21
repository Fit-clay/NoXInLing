package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 使用它替代传统的HolderView让过程更简单
 * @author Eric
 */
public class ViewHolder {
	private final SparseArray<View> views;
	private View convertView;
	
	private ViewHolder(View convertView){
		this.views = new SparseArray<View>();
		this.convertView = convertView;
		convertView.setTag(this);
	}
	
	public static ViewHolder get(Context context, int resource, View convertView){
		ViewHolder viewHolder;
		if(null == convertView){
			convertView = LayoutInflater.from(context).inflate(resource, null,false);
			viewHolder =  new ViewHolder(convertView);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return viewHolder;
	}
	
	public View getConvertView(){
		return convertView;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view = views.get(viewId);
		if(view == null){
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}
}
