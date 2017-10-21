package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.beidian.beidiannonxinling.adapter.interceptor.AdapterDataInterceptor;

import java.util.ArrayList;
import java.util.List;

public abstract class TBaseAdapter<T> extends BaseAdapter {

	private Context mContext;
	private int resource;
	protected List<T> mListData;
	AdapterDataInterceptor<T> interceptor;
	protected LayoutInflater mInflater;
	
	public TBaseAdapter(Context context,int layoutId,List<T> listData){
		if(null == listData){ listData = new ArrayList<T>();}
		mContext  = context;
		resource  = layoutId;
		mListData = listData;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override public int getCount() {
		return mListData.size();
	}

	@Override public T getItem(int position) {
		return mListData.get(position);
	}

	@Override public long getItemId(int position) {
		return position;
	}

	@Override public View getView(int position, View convertView, ViewGroup parent) {
		final int index = position;
		ViewHolder holder = ViewHolder.get(mContext, resource, convertView);
		doHandler(holder, index, convertView, parent);//TODO 考虑添加异步任务
		return holder.getConvertView();
	}
	
	/**
	 * 对外操作接口
	 * @param holder [holder对象 通过 getView(id) 方法获取view对象]
	 * @param position [根据位置信息,获取数据-->因为考虑到headerView等情况,不直接转为T]
	 * @param convertView
	 * @param parent
	 */
	public abstract void doHandler(ViewHolder holder, int position, View convertView, ViewGroup parent);
	
	/**
	 * 更新数据
	 * @param listData
	 */
	public void updateData(List<T> listData){
		mListData.clear();
		mListData.addAll(listData);
		notifyDataSetChanged();
	}
	
	public void addEnd(List<T> listData){
		mListData.addAll(listData);
		notifyDataSetChanged();
	}
	
	public void insertHead(List<T> listData){
		List<T> lt = new ArrayList<T>();
		lt.addAll(listData);
		lt.addAll(mListData);
		mListData.clear();
		mListData.addAll(lt);
		notifyDataSetChanged();
	}
	
	public void add(T t){
		if(mListData.add(t)){ notifyDataSetChanged(); }
	}
	
	public void remove(T t){
		if(mListData.remove(t)){ notifyDataSetChanged(); }
	}
	
	public T remove(int postion){
		T t = mListData.remove(postion);
		if(null != t){ notifyDataSetChanged(); }
		return t;
	}
	
	public Context getContext(){
		return mContext;
	}

	public void notifyDataSetChanged(){
		if(null != interceptor){ interceptor.doHandler(mListData); }
		super.notifyDataSetChanged();
	}

	public void setInterceptor(AdapterDataInterceptor<T> interceptor){
		this.interceptor = interceptor;
	}
}
