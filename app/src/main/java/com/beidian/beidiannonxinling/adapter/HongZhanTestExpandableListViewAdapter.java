package com.beidian.beidiannonxinling.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.BaseTestBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.BaseCqtTestActivity;
import com.beidian.beidiannonxinling.ui.activity.BaseInfoCollectionActivity;
import com.beidian.beidiannonxinling.ui.activity.GisActivity;
import com.beidian.beidiannonxinling.ui.activity.LTEShiFenTestActivity;
import com.beidian.beidiannonxinling.ui.activity.NetFrameworkActivity;
import com.beidian.beidiannonxinling.ui.activity.ShiFenBaseInfoActivity;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.List;

/**
 * Created by shanpu on 2017/8/30.
 * <p>
 */
public class HongZhanTestExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Activity mContext;
    private List<BaseTestBean.ParentListBean> mData;
    private String jsonFileAbsolutePathName;//json文件保存路径
    private String workorderno;
    private String testType;
    public HongZhanTestExpandableListViewAdapter(Activity context, List<BaseTestBean.ParentListBean> list, String jsonFileAbsolutePathName, String workorderno,String testType) {
        this.mContext = context;
        this.mData = list;
        this.jsonFileAbsolutePathName = jsonFileAbsolutePathName;
        this.workorderno = workorderno;
        this.testType = testType;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = View.inflate(mContext, R.layout.item_expandablelistview_hongzhantest_group, null);
            groupViewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            groupViewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            groupViewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            groupViewHolder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.tv_id.setText("" + (groupPosition + 1));
        groupViewHolder.tv_type.setText(mData.get(groupPosition).getGroupName());

        groupViewHolder.tv_state.setText(mData.get(groupPosition).getState());

        //点击事件：新增
        groupViewHolder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makeText(mContext, "新增");
                Intent intent = null;
                String filePath="";
                switch (groupPosition) {
                    case 0:
                        if(getChildrenCount(groupPosition)>0){
                            DialogUtil.showConfirmDialog(mContext,"基础信息只能有一条");
                        }else {
                            switch (testType){
                                case Const.HONGZHAN:
                                     filePath=Const.SaveFile.HONGZHAN_INFO_COLLECTION;
                                    intent  = new Intent(mContext, BaseInfoCollectionActivity.class);
                                    break;
                                case Const.BASE:
                                    filePath=Const.SaveFile.BASE_INFO_COLLECTION;
                                    intent  = new Intent(mContext, BaseInfoCollectionActivity.class);
                                    break;
                                case Const.SHIFEN:
                                    filePath=Const.SaveFile.SHIFEN_INFO_COLLECTION;
                                    intent  = new Intent(mContext, ShiFenBaseInfoActivity.class);
                                    break;
                            }
                        intent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                        intent.putExtra(Const.IntentTransfer.FILE_PATH,filePath);
                        intent.putExtra(Const.IntentTransfer.ORDER_TYPE,testType);
                        intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);

                            mContext.startActivityForResult(intent, 0);
                        }
                        break;
                    case 1:
                        intent = new Intent(mContext, BaseCqtTestActivity.class);
                        switch (testType){
                            case Const.HONGZHAN:
                                filePath=Const.SaveFile.HONGZHAN_CQT_TEST_COLLECTION;
                                break;
                            case Const.BASE:
                                filePath=Const.SaveFile.BASE_CQT_TEST_COLLECTION;
                                break;
                            case Const.SHIFEN:
                                filePath=Const.SaveFile.SHIFEN_MASTER_DEVICE;
                                intent.putExtra(Const.IntentTransfer.ORDER_TYPE,true);
                                break;
                        }
                        intent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                        intent.putExtra(Const.IntentTransfer.FILE_PATH,filePath);

                        if(mData.get(0).getChildList().size()>0){
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, mData.get(0).getChildList().get(0).getFileAbsolutePath());
                        }else {
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);
                        }
                        mContext.startActivityForResult(intent, 0);
                        break;
                    case 2:
                        switch (testType) {
                            case Const.HONGZHAN:
                                intent = new Intent(mContext, GisActivity.class);
                                intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_DT_TEST);
                                intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);

                                break;
                            case Const.SHIFEN:
                                intent = new Intent(mContext, LTEShiFenTestActivity.class);
                                intent.putExtra("title","室内打点测试");
                                intent.putExtra(Const.IntentTransfer.WORKDERNO,workorderno);
                                intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.SHIFEN_DISTRIBUTED);
                                intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);

                                break;
                        }
                        if(mData.get(0).getChildList().size()>0){
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, mData.get(0).getChildList().get(0).getFileAbsolutePath());
                        }else {
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);
                        }

                        mContext.startActivityForResult(intent, 0);
                        break;
                    case 3:
                        switch (testType) {
                          case Const.HONGZHAN:
                              intent=new Intent(mContext, NetFrameworkActivity.class);
                              intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_NET);
                              intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                              intent.putExtra(Const.IntentTransfer.WORKDERNO,workorderno);
                              mContext.startActivity(intent);
                              break;
                        }

                        break;
                }

            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(mContext, R.layout.item_expandablelistview_hongzhantest_child, null);
            childViewHolder.tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            childViewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            childViewHolder.tv_see = (TextView) convertView.findViewById(R.id.tv_see);
            childViewHolder.tv_reTest = (TextView) convertView.findViewById(R.id.tv_reTest);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final BaseTestBean.ParentListBean.ChildListBean childBean = mData.get(groupPosition).getChildList().get(childPosition);
        childViewHolder.tv_itemName.setText(childBean.getItemName());
        if(childBean.getItemState().equals("pass")){
            childViewHolder.tv_state.setVisibility(View.GONE);
            childViewHolder.tv_see.setVisibility(View.GONE);
        }else {
            childViewHolder.tv_state.setVisibility(View.VISIBLE);
            childViewHolder.tv_see.setVisibility(View.VISIBLE);
        }
        childViewHolder.tv_state.setText(childBean.getItemState());
        //点击事件：查看
        childViewHolder.tv_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (groupPosition) {
                    case 0:
                        switch (testType){
                            case Const.HONGZHAN:
                            case Const.BASE:
                                intent  = new Intent(mContext, BaseInfoCollectionActivity.class);
                                break;
                            case Const.SHIFEN:
                                intent  = new Intent(mContext, ShiFenBaseInfoActivity.class);
                                break;
                        }
                        intent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                        intent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        intent.putExtra(Const.IntentTransfer.TYPE, Const.LOOK);
                        intent.putExtra(Const.IntentTransfer.ORDER_TYPE,testType);
                        mContext.startActivityForResult(intent,0);
                        break;
                    case 1:
                        Intent CQTintent = new Intent(mContext, BaseCqtTestActivity.class);
                        CQTintent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                        CQTintent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        CQTintent.putExtra(Const.IntentTransfer.TYPE, Const.LOOK);
                        if(testType.equals(Const.SHIFEN)){
                            CQTintent.putExtra(Const.IntentTransfer.ORDER_TYPE,true);
                        }
                        mContext.startActivity(CQTintent);
                        break;
                }


            }
        });
        childViewHolder.tv_reTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //重测
                Intent intent=null;
                switch (groupPosition) {
                    case 0:
                        switch (testType){
                            case Const.HONGZHAN:
                            case Const.BASE:
                                intent  = new Intent(mContext, BaseInfoCollectionActivity.class);
                                break;
                            case Const.SHIFEN:
                                intent  = new Intent(mContext, ShiFenBaseInfoActivity.class);
                                break;
                        }
                        intent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                        intent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        intent.putExtra(Const.IntentTransfer.TYPE, Const.RESET);
                        intent.putExtra(Const.IntentTransfer.ORDER_TYPE,testType);

                        if(mData.get(0).getChildList().size()>0){
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, mData.get(0).getChildList().get(0).getFileAbsolutePath());
                        }else {
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);
                        }
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent CQTintent = new Intent(mContext, BaseCqtTestActivity.class);
                        CQTintent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                        if(mData.get(0).getChildList().size()>0){
                            CQTintent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, mData.get(0).getChildList().get(0).getFileAbsolutePath());
                        }else {
                            CQTintent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);
                        }
                        CQTintent.putExtra(Const.IntentTransfer.WORKDERNO, workorderno);
                        CQTintent.putExtra(Const.IntentTransfer.TYPE, Const.RESET);
                        if(testType.equals(Const.SHIFEN)){
                            CQTintent.putExtra(Const.IntentTransfer.ORDER_TYPE,true);
                        }
                        mContext.startActivity(CQTintent);

                        break;
                    case 2:
                        switch (testType){
                            case Const.HONGZHAN:
                                intent=new Intent(mContext, GisActivity.class);
                                intent.putExtra("title","宏站打点测试");
                                break;
                            case Const.SHIFEN:
                                intent  = new Intent(mContext, LTEShiFenTestActivity.class);
                                intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.SHIFEN_DISTRIBUTED);
                                intent.putExtra("title","室内打点测试");
                                break;
                        }
                        intent.putExtra(Const.IntentTransfer.TYPE, Const.RESET);
                        if(mData.get(0).getChildList().size()>0){
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, mData.get(0).getChildList().get(0).getFileAbsolutePath());
                        }else {
                            intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, jsonFileAbsolutePathName);
                        }
                        intent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        switch (testType) {
                            case Const.HONGZHAN:
                                intent=new Intent(mContext, NetFrameworkActivity.class);
                                intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_NET);
                                intent.putExtra(Const.IntentTransfer.TYPE, Const.RESET);
                                intent.putExtra(Const.IntentTransfer.WORKDERNO,workorderno);
                                intent.putExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH, childBean.getFileAbsolutePath());
                                mContext.startActivity(intent);
                        }
                            break;

                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class GroupViewHolder {
        private TextView tv_id;//序号
        private TextView tv_type;//类别
        private TextView tv_state;//已测试、未测试
        private TextView tv_add;//新增按钮

    }

    static class ChildViewHolder {
        private TextView tv_itemName;//条目名称
        private TextView tv_state;//状态(通过)
        private TextView tv_see;//查看
        private TextView tv_reTest;//重测
    }
}
