package com.beidian.beidiannonxinling.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanpu on 2017/8/30.
 * <p>
 *     宏站测试基本数据bean
 */

public class BaseInfoTestBean implements Serializable {


    /**
     * siteInfo : {"address":"北海合浦县解放路青云路交界百货大楼7楼顶","callnum":"","cellinfoList":[{"area":"乡镇","celleci":"3456890","celltype":"室外","id":1,"isrrucell":"是","lat":132.123456,"lineinfoList":[{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":1,"lineexterior":"ENC-202616D4T6","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"},{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":2,"lineexterior":"ENC-202616D4T7","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"}],"linenum":"3","lng":131.222212,"scenes":"道路","siteid":1}],"cellnum":"","commonsite":"","createmode":"","detection":"","devicefactory":"爱立信","employer":"","id":1,"iseasy":"容易","issingleroutes":"","lat":131.123456,"latitude":"","lng":131.123456,"longitude":"","netstandard":"LTE","operator":"北海移动","servicetype":"","sitename":"北海银滩","sitetype":"station","wlanroad":"","workorderno":"HCC_test_0001"}
     */

    private SiteInfoBean siteInfo;


    public  BaseInfoTestBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BaseInfoTestBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SiteInfoBean getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(SiteInfoBean siteInfo) {
        this.siteInfo = siteInfo;
    }

    public  class SiteInfoBean implements Serializable {
        /**
         * address : 北海合浦县解放路青云路交界百货大楼7楼顶
         * callnum :
         * cellinfoList : [{"area":"乡镇","celleci":"3456890","celltype":"室外","id":1,"isrrucell":"是","lat":132.123456,"lineinfoList":[{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":1,"lineexterior":"ENC-202616D4T6","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"},{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":2,"lineexterior":"ENC-202616D4T7","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"}],"linenum":"3","lng":131.222212,"scenes":"道路","siteid":1}]
         * cellnum :
         * commonsite :
         * createmode :
         * detection :
         * devicefactory : 爱立信
         * employer :
         * id : 1
         * iseasy : 容易
         * issingleroutes :
         * lat : 131.123456
         * latitude :
         * lng : 131.123456
         * longitude :
         * netstandard : LTE
         * operator : 北海移动
         * servicetype :
         * sitename : 北海银滩
         * sitetype : station
         * wlanroad :
         * workorderno : HCC_test_0001
         *
         * 新增
         *
         *
         *
         *
         *
         *
         */

        private String address;
        private String bbunum;
        private String callnum;
        private String cellnum;
        private String city;
        private String commonsite;
        private String createmode;
        private String detection;
        private String devicefactory;
        private String employer;
        private String enodebid;
        private int id;
        private String iseasy;
        private String issingleroutes;
        private String lat;
        private String latitude;
        private String lng;
        private String longitude;
        private String netstandard;
        private String operator;
        private String rrunum;
        private String servicetype;
        private String siteareacourny;
        private String sitecode;
        private String sitename;
        private String sitetype;
        private String stationcondition;
        private String wlanroad;

        private String workorderno;
        private String itemname;
        private String itemstate;
        private String pictureone;
        private String picturetwo;
        private String picturethree;
        private String buildingZ;
        private String buildingF;
        private String bbuaddressinfo;
        private List<CellinfoListBean> cellinfoList;
        private List<ShiFenBBU_RRUBean> bbulist;
//        private String bbulist;
        private ConstructionBean construction;
        private List<String> prictureList=new ArrayList<String>();
        private List<String>  pannoramicList=new ArrayList<String>();
        private List<String>  antennaPannoramicList=new ArrayList<String>();

        public String getBbuaddressinfo() {
            return bbuaddressinfo;
        }

        public void setBbuaddressinfo(String bbuaddressinfo) {
            this.bbuaddressinfo = bbuaddressinfo;
        }

        public void setPrictureList(List<String> prictureList) {
            this.prictureList = prictureList;
        }

        public void setPannoramicList(List<String> pannoramicList) {
            this.pannoramicList = pannoramicList;
        }

        public void setAntennaPannoramicList(List<String> antennaPannoramicList) {
            this.antennaPannoramicList = antennaPannoramicList;
        }

        public List<String> getPrictureList() {
            return prictureList;
        }

        public List<String> getPannoramicList() {
            return pannoramicList;
        }

        public List<String> getAntennaPannoramicList() {
            return antennaPannoramicList;
        }

        public ConstructionBean getConstruction() {
            if(construction==null){
                return new ConstructionBean();
            }
            return construction;
        }

        public void setConstruction(ConstructionBean construction) {
            this.construction = construction;
        }

   /*     public List<ShiFenBBU_RRUBean> getBbulist() {
            if(bbulist==null)
            {
                bbulist= new ArrayList<>();
                for(int i=0;i<3;i++){
                    bbulist.add(new ShiFenBBU_RRUBean("aaaaaaaa"+i,"","",""));
                     }
                return bbulist;
            }
            return bbulist;
        }

        public void setBbulist(List<ShiFenBBU_RRUBean> bbulist) {
            this.bbulist = bbulist;
        }*/

        public List<ShiFenBBU_RRUBean> getBbulist() {
            return bbulist;
        }

        public void setBbulist(List<ShiFenBBU_RRUBean> bbulist) {
            this.bbulist = bbulist;
        }

        public String getBuildingZ() {
            return buildingZ;
        }

        public void setBuildingZ(String buildingZ) {
            this.buildingZ = buildingZ;
        }

        public String getBuildingF() {
            return buildingF;
        }

        public void setBuildingF(String buildingF) {
            this.buildingF = buildingF;
        }



        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemName) {
            this.itemname = itemName;
        }

        public String getItemstate() {
            return itemstate;
        }

        public void setItemstate(String itemstate) {
            this.itemstate = itemstate;
        }

        public String getPictureone() {
            if(prictureList!=null&&prictureList.size()>0){
                return prictureList.get(0);
            }
            return pictureone;
        }

        public void setPictureone(String pictureone) {
            this.pictureone = pictureone;
        }

        public String getPicturetwo() {
            if(prictureList!=null&&prictureList.size()>1){
                return prictureList.get(1);
            }
            return picturetwo;
        }

        public void setPicturetwo(String picturetwo) {
            this.picturetwo = picturetwo;
        }

        public String getPicturethree() {
            if(prictureList!=null&&prictureList.size()>2){
                return prictureList.get(2);
            }
            return picturethree;
        }
        public void setPicturethree(String picturethree) {
            this.picturethree = picturethree;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBbunum() {
            return bbunum;
        }

        public void setBbunum(String bbunum) {
            this.bbunum = bbunum;
        }

        public String getCallnum() {
            return callnum;
        }

        public void setCallnum(String callnum) {
            this.callnum = callnum;
        }

        public String getCellnum() {
            return cellnum;
        }

        public void setCellnum(String cellnum) {
            this.cellnum = cellnum;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCommonsite() {
            return commonsite;
        }

        public void setCommonsite(String commonsite) {
            this.commonsite = commonsite;
        }

        public String getCreatemode() {
            return createmode;
        }

        public void setCreatemode(String createmode) {
            this.createmode = createmode;
        }

        public String getDetection() {
            return detection;
        }

        public void setDetection(String detection) {
            this.detection = detection;
        }

        public String getDevicefactory() {
            return devicefactory;
        }

        public void setDevicefactory(String devicefactory) {
            this.devicefactory = devicefactory;
        }

        public String getEmployer() {
            return employer;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }

        public String getEnodebid() {
            return enodebid;
        }

        public void setEnodebid(String enodebid) {
            this.enodebid = enodebid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIseasy() {
            return iseasy;
        }

        public void setIseasy(String iseasy) {
            this.iseasy = iseasy;
        }

        public String getIssingleroutes() {
            return issingleroutes;
        }

        public void setIssingleroutes(String issingleroutes) {
            this.issingleroutes = issingleroutes;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getNetstandard() {
            return netstandard;
        }

        public void setNetstandard(String netstandard) {
            this.netstandard = netstandard;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getRrunum() {
            return rrunum;
        }

        public void setRrunum(String rrunum) {
            this.rrunum = rrunum;
        }

        public String getServicetype() {
            return servicetype;
        }

        public void setServicetype(String servicetype) {
            this.servicetype = servicetype;
        }

        public String getSiteareacourny() {
            return siteareacourny;
        }

        public void setSiteareacourny(String siteareacourny) {
            this.siteareacourny = siteareacourny;
        }

        public String getSitecode() {
            return sitecode;
        }

        public void setSitecode(String sitecode) {
            this.sitecode = sitecode;
        }

        public String getSitename() {
            return sitename;
        }

        public void setSitename(String sitename) {
            this.sitename = sitename;
        }

        public String getSitetype() {
            return sitetype;
        }

        public void setSitetype(String sitetype) {
            this.sitetype = sitetype;
        }

        public String getStationcondition() {
            return stationcondition;
        }

        public void setStationcondition(String stationcondition) {
            this.stationcondition = stationcondition;
        }

        public String getWlanroad() {
            return wlanroad;
        }

        public void setWlanroad(String wlanroad) {
            this.wlanroad = wlanroad;
        }

        public String getWorkorderno() {
            return workorderno;
        }

        public void setWorkorderno(String workorderno) {
            this.workorderno = workorderno;
        }

        public List<CellinfoListBean> getCellinfoList() {
            return cellinfoList;
        }

        public void setCellinfoList(List<CellinfoListBean> cellinfoList) {
            this.cellinfoList = cellinfoList;
        }

    }
}

