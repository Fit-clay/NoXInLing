package com.beidian.beidiannonxinling.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by shanpu on 2017/8/30.
 * <p>
 */

public class BaseTestBean {

    private List<ParentListBean> parentList;

    public static BaseTestBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BaseTestBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ParentListBean> getParentList() {
        return parentList;
    }

    public void setParentList(List<ParentListBean> parentList) {
        this.parentList = parentList;
    }

    public static class ParentListBean {
        /**
         * childList : [{"itemName":"基站1-201708301500","itemState":"通过"},{"itemName":"基站22-201708301500","itemState":"通过22"}]
         * groupName : 基础信息采集
         * state : 已测试
         */

        private String groupName;
        private String state;
        private List<ChildListBean> childList;

        public static ParentListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ParentListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<ChildListBean> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildListBean> childList) {
            this.childList = childList;
        }

        public static class ChildListBean {
            /**
             * itemName : 基站1-201708301500
             * itemState : 通过
             * fileAbsolutePath: 文件绝对路径名
             */

            private String itemName;
            private String itemState;
            private String fileAbsolutePath;

            public static ChildListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ChildListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemState() {
                return itemState;
            }

            public void setItemState(String itemState) {
                this.itemState = itemState;
            }

            public String getFileAbsolutePath() {
                return fileAbsolutePath;
            }

            public void setFileAbsolutePath(String fileAbsolutePath) {
                this.fileAbsolutePath = fileAbsolutePath;
            }
        }
    }
}
