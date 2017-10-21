package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.Workorder;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.BaseTestActivity;
import com.beidian.beidiannonxinling.ui.activity.HongzhanTestActivity;
import com.beidian.beidiannonxinling.ui.activity.LocatActivity;
import com.beidian.beidiannonxinling.ui.activity.ShiFenTestActivity;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ASUS on 2017/8/23.
 */

public class TaskRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 上拉加载更多
     */
    public static final int PULLUP_LOAD_MORE = 0;
    /**
     * 正在加载中
     */
    public static final int LOADING_MORE = 1;
    /**
     * 没有加载更多 隐藏
     */
    public static final int NO_LOAD_MORE = 2;
    /**
     *
     */
    public static final int GROUP_BY_TIME = 1;
    public static final int GROUP_BY_RANGE = 2;
    public static final int GROUP_BY_STATE = 3;
    public static final int M0DEL_MIN = 1;
    public static final int MODEL_MAX = 2;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public final String TAG = getClass().getSimpleName();
    private ToastUtils utils;
    private Context mContext;
    private List<Workorder> mList;
    private LayoutInflater mInflater;
    private int mGroupByModes = 0;
    /**
     * 上拉加载更多状态-默认为0
     */
    private int mLoadMoreStatus = 0;
    /*
        更新模式
     */
    private int RE_MODEL = 0;

    public TaskRecycleAdapter(List<Workorder> datas) {
        mList = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemViewHolder holder = new ItemViewHolder(LayoutInflater
                    .from(parent.getContext())
                    //inflate的第三种放置方法
                    .inflate(R.layout.item_list, parent, false));
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            FooterViewHolder holder = new FooterViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_load, parent, false));

            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemholder = (ItemViewHolder) holder;
            if (payloads.isEmpty()) {//payloads为空 即不是调用notifyItemChanged(position,payloads)方法执行的
                //在这里进行初始化item全部控件
                final int number = position;
                itemholder.id.setText(mList.get(position).getWorkorderno());
                itemholder.date.setText(mList.get(position).getCreatetime());

                itemholder.taskname.setText(mList.get(position).getSitename());

                itemholder.range.setText(mList.get(position).getRange() + "公里");
                /**
                 * 工单状态(0:待处理，1已完成，2已提交)
                 */
                if (mList.get(position).getWorkorderstatus().equals("0")) {

                    itemholder.typemod.setText("待处理");
                } else if (mList.get(position).getWorkorderstatus().equals("1")) {

                    itemholder.typemod.setText("已提交");
                } else if (mList.get(position).getWorkorderstatus().equals("2")) {

                    itemholder.typemod.setText("已完成");
                } else {

                    itemholder.typemod.setText("待处理");
                }
                Log.e(TAG, "onBindViewHolder: "+mList.get(position).getWorkordertype());
                if (mList.get(position).getWorkordertype().equals("1")) {
                    itemholder.typestate.setText("宏站");
                } else if (mList.get(position).getWorkordertype().equals("2")) {
                    itemholder.typestate.setText("室分");
                } else if((mList.get(position).getWorkordertype().equals("0"))){
                    itemholder.typestate.setText("基础功能");
                }


                itemholder.navigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), LocatActivity.class);
                        Workorder data = mList.get(number);
                        Log.i(TAG, "onClick: " + data.getLatitude());
                        Log.i(TAG, "onClick: " + data.getLongitude());
                        Log.i(TAG, "onClick: " + data.getStartX());
                        intent.putExtra("task", data);
                        v.getContext().startActivity(intent);
                    }
                });
                itemholder.rl_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Workorder data = mList.get(number);
                        // TODO: 2017/9/20  根据状态判断跳转到那个activity
                        Intent intent=null;
                        if(data.getWorkordertype().equals(Const.BASE)){
                            intent = new Intent(v.getContext(), BaseTestActivity.class);
                            intent.putExtra("title", "基础验证测试");
                            intent.putExtra(Const.IntentTransfer.FILE_PATH, Const.SaveFile.BASE_TEST);

                        }else if(data.getWorkordertype().equals(Const.HONGZHAN)){
                            intent  = new Intent(v.getContext(), HongzhanTestActivity.class);
                            intent.putExtra("title", "宏站单验");
                            intent.putExtra(Const.IntentTransfer.FILE_PATH, Const.SaveFile.HONGZHAN_TEST);

                        }else if(data.getWorkordertype().equals(Const.SHIFEN)){
                            intent  = new Intent(v.getContext(), ShiFenTestActivity.class);
                            intent.putExtra("title", "室分单验");
                            intent.putExtra(Const.IntentTransfer.FILE_PATH, Const.SaveFile.SHIFEN_TEST);

                        }
                        intent.putExtra("task", data);
                        v.getContext().startActivity(intent);
                    }
                });

            } else {//payloads不为空 即调用notifyItemChanged(position,payloads)方法后执行的

                //在这里可以获取payloads中的数据  进行局部刷新
                //假设是int类型
                int type = (int) payloads.get(0);// 刷新哪个部分 标志位
                switch (type) {
                    case 1:
                        itemholder.range.setText(mList.get(position).getRange());//只刷新userName
                        break;

                }
            }

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:

                    footerViewHolder.progressBar.setVisibility(View.INVISIBLE);
                    footerViewHolder.textView.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_MORE:
                    footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footerViewHolder.textView.setVisibility(View.VISIBLE);
                    footerViewHolder.textView.setText("加载中.....");
                    break;
                case NO_LOAD_MORE:
                    footerViewHolder.mloadLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }

    }


    /**
     * 加载上拉加载的ITEM
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size() + 1;
        }
        return 0;
    }

    public List<Workorder> getRange() {
        return mList;
    }

    public void onNotifyDataSetChanged(List<Workorder> date) {
        for (int i = 0; i < date.size(); i++) {
            mList.get(i).setRange(date.get(i).getRange());
            mList.get(i).setStartX(date.get(i).getStartX());
            Log.i(TAG, "onNotifyDataSetChanged: " + date.get(i).getStartX());
            Log.i(TAG, "onNotifyDataSetChanged: " + date.get(i).getStartY());
            mList.get(i).setStartY(date.get(i).getStartY());
            notifyItemChanged(i, 0);
            notifyDataSetChanged();
        }
    }

    public void removeall() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<Workorder> items) {
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }


    public void addFooterItem(List<Workorder> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }


    /**
     * 排序模式
     */
    public void groupBy(int state, int mode) {
        if (state != 0 && mode != 0) {
            if (state == GROUP_BY_TIME && mode == M0DEL_MIN) {
                Collections.reverse(mList);
                notifyDataSetChanged();
            } else if (state == GROUP_BY_TIME && mode == MODEL_MAX) {
                Collections.sort(mList);
                notifyDataSetChanged();
            } else if (state == GROUP_BY_RANGE && mode == M0DEL_MIN) {
                Collections.sort(mList, new RangeComparator());
                notifyDataSetChanged();
            } else if (state == GROUP_BY_RANGE && mode == MODEL_MAX) {
                Collections.sort(mList, new RangeComparatorM());
                notifyDataSetChanged();
            } else if (state == GROUP_BY_STATE && mode == M0DEL_MIN) {
                Collections.sort(mList, new StateComparator());
                notifyDataSetChanged();
            } else if (state == GROUP_BY_STATE && mode == MODEL_MAX) {
                Collections.sort(mList, new StateComparatorM());
                notifyDataSetChanged();
            }
        } else {
            Collections.sort(mList);
            notifyDataSetChanged();
        }
    }

    public void groupBYTime() {
        Log.e(TAG, "GroupBYTime: " + mList.size());
    }

    /**
     * 更新加载更多状态
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }


    /**
     * 自定义排序器（距离）
     * 小-大
     */
    static class RangeComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Workorder t1 = (Workorder) object1;
            Workorder t2 = (Workorder) object2;
            return new Double(t1.getRange()).compareTo(new Double(t2.getRange()));
        }
    }

    /**
     * 自定义排序器（类型）
     * 小-大
     */
    static class StateComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Workorder t1 = (Workorder) object1;
            Workorder t2 = (Workorder) object2;
            return new Double(t1.getWorkorderstatus()).compareTo(new Double(t2.getWorkorderstatus()));
        }
    }

    /**
     * 自定义排序器（距离）
     * 大-小
     */
    static class RangeComparatorM implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Workorder t1 = (Workorder) object1;
            Workorder t2 = (Workorder) object2;
            return new Double(t2.getRange()).compareTo(new Double(t1.getRange()));
        }
    }

    /**
     * 自定义排序器（类型）
     * 大-小
     */
    static class StateComparatorM implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Workorder t1 = (Workorder) object1;
            Workorder t2 = (Workorder) object2;

            return new Double(t2.getWorkorderstatus()).compareTo(new Double(t1.getWorkorderstatus()));

        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView id, taskname, date, range, typestate, typemod;
        ImageView navigation;
        RelativeLayout rl_parent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            taskname = (TextView) itemView.findViewById(R.id.task_name);
            date = (TextView) itemView.findViewById(R.id.date);
            range = (TextView) itemView.findViewById(R.id.range);
            typemod = (TextView) itemView.findViewById(R.id.type2);
            typestate = (TextView) itemView.findViewById(R.id.type_state);
            navigation = (ImageView) itemView.findViewById(R.id.iv_left);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView textView;
        LinearLayout mloadLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pbLoad);
            textView = (TextView) itemView.findViewById(R.id.tvLoadText);
            mloadLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
        }
    }
}
