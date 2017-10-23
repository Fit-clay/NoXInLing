package com.beidian.beidiannonxinling.ui.activity;

import com.beidian.beidiannonxinling.adapter.HongZhanTestExpandableListViewAdapter;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.BaseTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.common.Const;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/20.
 */

public class HongzhanTestActivity extends BaseTestActivity {

    @Override
    protected void initExpandableListView(String jsonFileAbsolutePathName, String workOrder) {
        BaseTestBean.ParentListBean parentBean1 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean2 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean3 = new BaseTestBean.ParentListBean();
        BaseTestBean.ParentListBean parentBean4 = new BaseTestBean.ParentListBean();


        List<BaseTestBean.ParentListBean.ChildListBean> childList1 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList2 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList3 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList4 = new ArrayList<>();

        setChaildListBean(Const.SaveFile.HONGZHAN_INFO_COLLECTION, childList1);
        parentBean1.setGroupName("基础信息采集");
        parentBean1.setChildList(childList1);
        parentBean1.setState(isTest(childList1));

        setChaildListBean(Const.SaveFile.HONGZHAN_CQT_TEST_COLLECTION, childList2);
        parentBean2.setGroupName("CQT定点测试");
        parentBean2.setChildList(childList2);
        parentBean2.setState(isTest(childList2));

        setChaildListBean(Const.SaveFile.HONGZHAN_DT_TEST, childList3);
        parentBean3.setGroupName("DT测试");
        parentBean3.setChildList(childList3);
        parentBean3.setState(isTest(childList3));

        setChaildListBean(Const.SaveFile.HONGZHAN_NET, childList4);
        parentBean4.setGroupName("网络架构");
        parentBean4.setChildList(childList4);
        parentBean4.setState(isTest(childList4));

        //组数据集合
        List<BaseTestBean.ParentListBean> parentList = new ArrayList<>();
        parentList.add(parentBean1);
        parentList.add(parentBean2);
        parentList.add(parentBean3);
        parentList.add(parentBean4);


        mExpandableListViewAdapter = new HongZhanTestExpandableListViewAdapter(this, parentList, jsonFileAbsolutePathName, workOrder,Const.HONGZHAN);
        expandableListView.setAdapter(mExpandableListViewAdapter);
        initListView(parentList);

    }
    @Override
    protected void handleNetResult(String s, String jsonFileAbsolutePathName, String workOrder) {
        super.handleNetResult(s, jsonFileAbsolutePathName, workOrder);
        Gson gson = new Gson();
        BaseInfoTestBean bean = gson.fromJson(s, BaseInfoTestBean.class);
        BaseInfoTestBean.SiteInfoBean siteInfo = bean.getSiteInfo();
        List<CellinfoListBean> cellinfoList = siteInfo.getCellinfoList();
        tv_cellCount.setText("" + (cellinfoList == null ? "" : (cellinfoList.size())));
    }
}
