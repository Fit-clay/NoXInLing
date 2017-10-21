package com.beidian.beidiannonxinling.ui.fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shanpu on 2017/8/14.
 * <p>
 */
public abstract class BaseLazyLoadFragment extends Fragment {
    public final String TAG = getClass().getSimpleName(); /*TAG标记*/
    private View mContentView;
    protected boolean isInit = false; /*视图是否已经初初始化*/
    protected boolean isLoad = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentView = initView(inflater, container, savedInstanceState);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        initListener();
        return mContentView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }
    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }
    /**
     * 视图销毁的时候将Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    /**
     * 初始化Fragment内容页
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();
    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {
    }

    protected <T extends View> T findView(View view ,@IdRes int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 初始化监听器
     */
    protected abstract void initListener();
}