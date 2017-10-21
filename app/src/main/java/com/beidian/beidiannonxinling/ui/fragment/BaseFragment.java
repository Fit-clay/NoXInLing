package com.beidian.beidiannonxinling.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shanpu on 2017/8/14.
 * <p>
 */

public abstract class BaseFragment extends Fragment {
    public final String TAG = getClass().getSimpleName(); /*TAG标记*/
    private View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = initView(inflater, container, savedInstanceState);
        initListener();
        loadData();
        return mContentView;
    }

    /**
     * 初始化内容页
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();



}