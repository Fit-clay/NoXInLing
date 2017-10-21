package com.beidian.beidiannonxinling.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.TaskRecycleAdapter;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.bean.Workorder;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.util.JsonUtils;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends BaseFragment {
    public MyLocationListenner myListener = new MyLocationListenner();
    protected String account;
    protected String telphone;
    //定位相关
    LocationClient mLocClient;
    double X = 0.0;
    double Y = 0.0;
    private RecyclerView myRecyclerview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TaskRecycleAdapter mAdapter;
    private int page = 1;
    private int statenow;
    private int modelnow;

    /**
     * RecyclerView基本配置
     * 1.布局管理 mRecyclerView.setLayoutManager(custom_tool_bar);
     * 2.设置Adapter mRecyclerView.setAdapter(adapter):
     * 3.设置子项的增长或删除动画 mRecyclerView.setItemAnimator(new DefaultItemAnimator());
     * 4.分割线（根据个人）mRecyclerView.addItemDecoration(new DividerItemDecoration(
     * getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
     */
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + " onCreateView...");
        View view = inflater.inflate(R.layout.fragment_task_list, null);
        UserInfoBean bean = BaseApplication.getUserInfo();
        myRecyclerview = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        mLocClient.setLocOption(MapUtil.setLocationOption());
        mLocClient.start();
        account = bean.getUsername();
        telphone = bean.getTelphone();

        return view;
    }


    @Override
    protected void loadData() {

        //String account =preferences.getString("name","");
        HttpParams params = new HttpParams();
        //params.put("account", account);

        params.put("account", account);
        page = 1;
        params.put("current", page);
        OkGo.post(Const.Url.URL_GET_WORK_ORDER).tag(TaskListFragment.this).params(params).execute(new ResultCallback(getActivity(), true) {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                //设置布局管理器
                myRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                //颜色适配
                //mSwipeRefreshLayout.setColorSchemeColors(R);
                List<Workorder> Datas = JsonUtils.getWorknow(s, Y, X);
                if (Datas != null && Datas.size() != 0) {
                    Log.d(TAG, "onSuccess: " + Datas.size());
                    mAdapter = new TaskRecycleAdapter(Datas);

                }
                //设置适配器
                myRecyclerview.setAdapter(mAdapter);
                groupBy(statenow, modelnow);
                //初次载入显示进度条
                mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            }

            @Override
            public void onFailure(Call call, Response response, Exception e) {
                ToastUtils.makeText(getActivity(), "无数据，请检查网络或联系管理员");
            }

        });

    }


    @Override
    protected void initListener() {
        initPullRefresh();
        initLoadMoreListener();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + " onCreate...");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getClass().getSimpleName() + " onResume...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getClass().getSimpleName() + " onDestroy...");
    }

    @Override
    public void onStop() {
        super.onStop();
        OkGo.getInstance().cancelTag(TaskListFragment.this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            LogUtils.i(TAG, "onHiddenChanged  " + TAG + "隐藏");
        } else {
            LogUtils.i(TAG, "onHiddenChanged  " + TAG + "可见");
        }
    }

    public void groupBy(int state, int mode) {
        if (state != 0 && mode != 0) {
            statenow = state;
            modelnow = mode;
        }
        if (statenow != 0 && modelnow != 0) {
            if (CheckGroupBy()) {
                mAdapter.groupBy(statenow, modelnow);
            }
        } else {

        }
    }

    public boolean CheckGroupBy() {
        Boolean isCheck = true;
        List<Workorder> check = mAdapter.getRange();
        if (check.get(0).getRange().equals("距离初始化中请刷新")) {
            isCheck = false;
            ToastUtils.makeText(getContext(), "你不能在距离未加载完成时使用排序方法");
        }
        return isCheck;
    }



    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serchTop();
            }
        });
    }

    private void initLoadMoreListener() {

        myRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "onScrollStateChanged: " + "pull");
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    //设置正在加载更多
                    mAdapter.changeMoreStatus(TaskRecycleAdapter.LOADING_MORE);

                    HttpParams params = new HttpParams();
                    params.put("account", account);

                    params.put("current", page + 1);
                    Log.i(TAG, "onScrollStateChanged: " + account + page);
                    OkGo.post(Const.Url.URL_GET_WORK_ORDER).tag(TaskListFragment.this).params(params).execute(new ResultCallback(getActivity(), false) {
                        @Override
                        public void onFailure(Call call, Response response, Exception e) {
                        }
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            List<Workorder> footerDatas = JsonUtils.getWorknow(s, Y, X);
                            mAdapter.addFooterItem(footerDatas);

                            //设置回到上拉加载更多
                            mAdapter.changeMoreStatus(TaskRecycleAdapter.PULLUP_LOAD_MORE);
                            if (footerDatas.size() > 0) {
                                groupBy(statenow, modelnow);
                                page++;
                            }

                        }
                    });
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }
        });


    }
    public void serchTop(){

        HttpParams params = new HttpParams();
        params.put("account", account);
        page = 1;
        params.put("current", page);
        OkGo.post(Const.Url.URL_GET_WORK_ORDER).tag(TaskListFragment.this).params(params).execute(new ResultCallback(getActivity(), false) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {
                LogUtils.i(TAG, "请求失败请检查网络：" + response);
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.i(TAG, "网络请求结果：" + s);
                //设置适配器
                List<Workorder> headDatas = JsonUtils.getWorknow(s, Y, X);

                if (headDatas != null && headDatas.size() != 0) {
                    if (mAdapter == null) {
                        //设置布局管理器
                        myRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mAdapter = new TaskRecycleAdapter(headDatas);
                        myRecyclerview.setAdapter(mAdapter);

                    } else {
                        mAdapter.addItem(headDatas);

                    }
                } else {
                    Toast.makeText(getActivity(), "出错了!", Toast.LENGTH_SHORT).show();
                }

                //刷新完成
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

    }
    public void serch(String text) {
        HttpParams params = new HttpParams();
        params.put("account", account);
        params.put("workorderNo", text);
        Log.d(TAG, "Serch: " + text);
        OkGo.post(Const.Url.URL_SEARCH_WORK_ORDER).tag(TaskListFragment.this).params(params).execute(new ResultCallback(getActivity(), true) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                List<Workorder> datas = JsonUtils.getTask(s,Y,X);
                if (datas.size() > 0) {
                    mAdapter.addItem(datas);
                    groupBy(statenow, modelnow);
                } else {
                    mAdapter.removeall();
                }

            }

            @Override
            public void onFailure(Call call, Response response, Exception e) {

            }
        });

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            Log.i(TAG, "LocationChange: ");
            Y = location.getLatitude();
            X = location.getLongitude();
            HttpParams params = new HttpParams();
            params.put("account", account);
            params.put("current", page);
            OkGo.post(Const.Url.URL_GET_WORK_ORDER).tag(TaskListFragment.this).params(params).execute(new ResultCallback(getActivity(), false) {
                @Override
                public void onFailure(Call call, Response response, Exception e) {
                    LogUtils.i(TAG, "请求失败请检查网络：" + response);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mLocClient.unRegisterLocationListener(myListener);
                }

                @Override
                public void onSuccess(String s, Call call, Response response) {
                    LogUtils.i(TAG, "网络请求结果：" + s);
                    //设置适配器
                    List<Workorder> headDatas = JsonUtils.getWorknow(s, Y, X);

                    if (headDatas != null && headDatas.size() != 0) {
                        if (mAdapter == null) {
                            //设置布局管理器
                            myRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mAdapter = new TaskRecycleAdapter(headDatas);
                            myRecyclerview.setAdapter(mAdapter);
                        } else {
                            mAdapter.onNotifyDataSetChanged(headDatas);
                            groupBy(statenow, modelnow);
                        }
                    } else {
                        Toast.makeText(getActivity(), "出错了!", Toast.LENGTH_SHORT).show();
                    }

                    //刷新完成
                    mSwipeRefreshLayout.setRefreshing(false);
                    mLocClient.unRegisterLocationListener(myListener);
                }
            });



            if (mLocClient.isStarted()) {
                mLocClient.stop();


            }


        }


    }


}
