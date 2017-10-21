package com.beidian.beidiannonxinling.ui.activity;

import com.beidian.beidiannonxinling.adapter.HongZhanTestExpandableListViewAdapter;
import com.beidian.beidiannonxinling.bean.BaseTestBean;
import com.beidian.beidiannonxinling.common.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/20.
 */

public class ShiFenTestActivity extends BaseTestActivity {

    @Override
    protected void initExpandableListView(String jsonFileAbsolutePathName, String workOrder) {
        BaseTestBean.ParentListBean parentBean1 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean2 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean3 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean4 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean5 = new BaseTestBean.ParentListBean();


        List<BaseTestBean.ParentListBean.ChildListBean> childList1 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList2 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList3 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList4 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList5 = new ArrayList<>();

        setChaildListBean(Const.SaveFile.SHIFEN_INFO_COLLECTION, childList1);
        parentBean1.setGroupName("基础信息采集");
        parentBean1.setChildList(childList1);
        parentBean1.setState(isTest(childList1));

        setChaildListBean(Const.SaveFile.SHIFEN_MASTER_DEVICE, childList2);
        parentBean2.setGroupName("主设备测试");
        parentBean2.setChildList(childList2);
        parentBean2.setState(isTest(childList2));

         setChaildListBean(Const.SaveFile.SHIFEN_DISTRIBUTED, childList3);
        parentBean3.setGroupName("分布式系统测试");
        parentBean3.setChildList(childList3);
        parentBean3.setState(isTest(childList3));

        setChaildListBean(Const.SaveFile.SHIFEN_CHANGE, childList4);
        parentBean4.setGroupName("切换测试");
        parentBean4.setChildList(childList4);
        parentBean4.setState(isTest(childList4));

        setChaildListBean(Const.SaveFile.SHIFEN_LEAKED, childList5);
        parentBean5.setGroupName("外泄测试");
        parentBean5.setChildList(childList5);
        parentBean5.setState(isTest(childList5));

        //组数据集合
        List<BaseTestBean.ParentListBean> parentList = new ArrayList<>();
        parentList.add(parentBean1);
        parentList.add(parentBean2);
        parentList.add(parentBean3);
        parentList.add(parentBean4);
        parentList.add(parentBean5);


        mExpandableListViewAdapter = new HongZhanTestExpandableListViewAdapter(this, parentList, jsonFileAbsolutePathName, workOrder,Const.SHIFEN);
        expandableListView.setAdapter(mExpandableListViewAdapter);
        initListView(parentList);

    }

}
