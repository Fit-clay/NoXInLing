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

public class BaseHongZhanTestBean implements Serializable {


    /**
     * siteInfo : {"address":"北海合浦县解放路青云路交界百货大楼7楼顶","callnum":"","cellinfoList":[{"area":"乡镇","celleci":"3456890","celltype":"室外","id":1,"isrrucell":"是","lat":132.123456,"lineinfoList":[{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":1,"lineexterior":"ENC-202616D4T6","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"},{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":2,"lineexterior":"ENC-202616D4T7","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"}],"linenum":"3","lng":131.222212,"scenes":"道路","siteid":1}],"cellnum":"","commonsite":"","createmode":"","detection":"","devicefactory":"爱立信","employer":"","id":1,"iseasy":"容易","issingleroutes":"","lat":131.123456,"latitude":"","lng":131.123456,"longitude":"","netstandard":"LTE","operator":"北海移动","servicetype":"","sitename":"北海银滩","sitetype":"station","wlanroad":"","workorderno":"HCC_test_0001"}
     */

    private SiteInfoBean siteInfo;

    public static BaseHongZhanTestBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BaseHongZhanTestBean.class);
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

    public static class SiteInfoBean implements Serializable {
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
        private List<CellinfoListBean> cellinfoList;
        private List<String> prictureList=new ArrayList<>();

        public List<String> getPrictureList() {
            return prictureList;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
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

        public static class CellinfoListBean implements Serializable {
            /**
             * area : 乡镇
             * celleci : 3456890
             * celltype : 室外
             * id : 1
             * isrrucell : 是
             * lat : 132.123456
             * lineinfoList : [{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":1,"lineexterior":"ENC-202616D4T6","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"},{"cellid":1,"directionrange":"0-50","electronicuprange":"0-18","id":2,"lineexterior":"ENC-202616D4T7","lineheight":"16","linetype":"美化天线","mechanical":"50","mechanicalup":"13","mechanicaluprange":"0-15","rf":"道路、住宅区 （周边为3-4层居民楼）"}]
             * linenum : 3
             * lng : 131.222212
             * scenes : 道路
             * siteid : 1
             * 收集和填写
             * cleci:eci
             * clchanal:频段
             * clrru：rru数量
             * clpcl: pcl
             * clearfcn：earfcn
             * clcorve:覆盖场景
             *
             *
             */
            private String cellname;
            private String area;
            private String celleci;
            private String celltype;
            private String cgi;
            private int id;
            private String isrrucell;
            private String lat;
            private String linenum;
            private String lng;
            private String scenes;
            private String workfrqband;
            private String earfcn;
            private String pci;
            private int siteid;
            private List<LineinfoListBean> lineinfoList=new ArrayList<>();

            private String rru;

            private String cleci;
            private String clchanal;
            private String clrru;
            private String clpcl;
            private String clearfcn;
            private String clcorve;


            private String iscleci;
            private String isclchanal;
            private String isclrru;
            private String islayuan;
            private String isclcorve;
            private boolean userAdd;
            private String rtName;

            public String getRtName() {
                return rtName;
            }

            public void setRtName(String rtName) {
                this.rtName = rtName;
            }

            public String getEarfcn() {
                return earfcn;
            }

            public void setEarfcn(String earfcn) {
                this.earfcn = earfcn;
            }

            public String getPci() {
                return pci;
            }

            public void setPci(String pci) {
                this.pci = pci;
            }

            public boolean isUserAdd() {
                return userAdd;
            }

            public void setUserAdd(boolean userAdd) {
                this.userAdd = userAdd;
            }

            public String getCellname() {
                return cellname;
            }

            public void setCellname(String cellname) {
                this.cellname = cellname;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCelleci() {
                return celleci;
            }

            public void setCelleci(String celleci) {
                this.celleci = celleci;
            }

            public String getCelltype() {
                return celltype;
            }

            public void setCelltype(String celltype) {
                this.celltype = celltype;
            }

            public String getCgi() {
                return cgi;
            }

            public void setCgi(String cgi) {
                this.cgi = cgi;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIsrrucell() {
                return isrrucell;
            }

            public void setIsrrucell(String isrrucell) {
                this.isrrucell = isrrucell;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLinenum() {
                return linenum;
            }

            public void setLinenum(String linenum) {
                this.linenum = linenum;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getScenes() {
                return scenes;
            }

            public void setScenes(String scenes) {
                this.scenes = scenes;
            }

            public int getSiteid() {
                return siteid;
            }

            public void setSiteid(int siteid) {
                this.siteid = siteid;
            }

            public String getRru() {
                return rru;
            }

            public void setRru(String rru) {
                this.rru = rru;
            }

            public String getWorkfrqband() {
                return workfrqband;
            }

            public void setWorkfrqband(String workfrqband) {
                this.workfrqband = workfrqband;
            }

            public String getCleci() {
                return cleci;
            }

            public void setCleci(String cleci) {
                this.cleci = cleci;
            }

            public String getClchanal() {
                return clchanal;
            }

            public void setClchanal(String clchanal) {
                this.clchanal = clchanal;
            }

            public String getClrru() {
                return clrru;
            }

            public void setClrru(String clrru) {
                this.clrru = clrru;
            }

            public String getClpcl() {
                return clpcl;
            }

            public void setClpcl(String clpcl) {
                this.clpcl = clpcl;
            }

            public String getClearfcn() {
                return clearfcn;
            }

            public void setClearfcn(String clearfcn) {
                this.clearfcn = clearfcn;
            }

            public String getClcorve() {
                return clcorve;
            }

            public void setClcorve(String clcorve) {
                this.clcorve = clcorve;
            }

            public String getIscleci() {
                return iscleci;
            }

            public void setIscleci(String iscleci) {
                this.iscleci = iscleci;
            }

            public String getIsclchanal() {
                return isclchanal;
            }

            public void setIsclchanal(String isclchanal) {
                this.isclchanal = isclchanal;
            }

            public String getIsclrru() {
                return isclrru;
            }

            public void setIsclrru(String isclrru) {
                this.isclrru = isclrru;
            }

            public String getIslayuan() {
                return islayuan;
            }

            public void setIslayuan(String islayuan) {
                this.islayuan = islayuan;
            }

            public String getIsclcorve() {
                return isclcorve;
            }

            public void setIsclcorve(String isclcorve) {
                this.isclcorve = isclcorve;
            }

            public List<LineinfoListBean> getLineinfoList() {
//                if (lineinfoList==null){
//                    return new ArrayList<>();
//                }
                return lineinfoList;
            }

            public void setLineinfoList(List<LineinfoListBean> lineinfoList) {
                this.lineinfoList = lineinfoList;
            }

            public static class LineinfoListBean implements Serializable {
                /**
                 * cellid : 1
                 * directionrange : 0-50
                 * electronicuprange : 0-18
                 * id : 1
                 * lineexterior : ENC-202616D4T6
                 * lineheight : 16
                 * linetype : 美化天线
                 * mechanical : 50
                 * mechanicalup : 13
                 * mechanicaluprange : 0-15
                 * rf : 道路、住宅区 （周边为3-4层居民楼）
                 * cllong:采集经度
                 * cllat:采集纬度
                 * clemechanicalup:采集下倾角
                 * clmechanical:采集方位角
                 * cllineexterior：采集天线平台
                 * islatlng:经纬度是否一致
                 * islineexterior:天线平台是否一致
                 * lltype:美化天线
                 */

                private int cellid;
                private String directionrange;
                private String electronicuprange;
                private int id;
                private String lat;
                private String lineexterior;
                private String lineheight;
                private String linetype;
                private String lng;
                private String mechanical;
                private String mechanicalup;
                private String mechanicaluprange;
                private String rf;

                private String pictureone;
                private String picturetwo;
                private String picturethree;

                private String cllong;
                private String cllat;
                private String clemechanicalup;
                private String clmechanical;
                private String cllineexterior;
                private boolean userAdd;

                public boolean isUserAdd() {
                    return userAdd;
                }

                public void setUserAdd(boolean userAdd) {
                    this.userAdd = userAdd;
                }

                private List<String> imgPathList=new ArrayList<>();

                public List<String> getImgPathList() {
                    return imgPathList;
                }

                private String islatlng;
                private String islineexterior;
                private String lltype;

                public String getIslatlng() {
                    return islatlng;
                }

                public void setIslatlng(String islatlng) {
                    this.islatlng = islatlng;
                }

                public String getIslineexterior() {
                    return islineexterior;
                }

                public void setIslineexterior(String islineexterior) {
                    this.islineexterior = islineexterior;
                }

                public String getLltype() {
                    return lltype;
                }

                public void setLltype(String lltype) {
                    this.lltype = lltype;
                }

                public int getCellid() {
                    return cellid;
                }

                public void setCellid(int cellid) {
                    this.cellid = cellid;
                }

                public String getDirectionrange() {
                    return directionrange;
                }

                public void setDirectionrange(String directionrange) {
                    this.directionrange = directionrange;
                }

                public String getElectronicuprange() {
                    return electronicuprange;
                }

                public void setElectronicuprange(String electronicuprange) {
                    this.electronicuprange = electronicuprange;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLineexterior() {
                    return lineexterior;
                }

                public void setLineexterior(String lineexterior) {
                    this.lineexterior = lineexterior;
                }

                public String getLineheight() {
                    return lineheight;
                }

                public void setLineheight(String lineheight) {
                    this.lineheight = lineheight;
                }

                public String getLinetype() {
                    return linetype;
                }

                public void setLinetype(String linetype) {
                    this.linetype = linetype;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getMechanical() {
                    return mechanical;
                }

                public void setMechanical(String mechanical) {
                    this.mechanical = mechanical;
                }

                public String getMechanicalup() {
                    return mechanicalup;
                }

                public void setMechanicalup(String mechanicalup) {
                    this.mechanicalup = mechanicalup;
                }

                public String getMechanicaluprange() {
                    return mechanicaluprange;
                }

                public void setMechanicaluprange(String mechanicaluprange) {
                    this.mechanicaluprange = mechanicaluprange;
                }

                public String getRf() {
                    return rf;
                }

                public void setRf(String rf) {
                    this.rf = rf;
                }

                public String getPictureone() {
                    if(imgPathList!=null&&imgPathList.size()>0){
                        return imgPathList.get(0);
                    }
                    return pictureone;
                }

                public void setPictureone(String pictureone) {
                    this.pictureone = pictureone;
                }

                public String getPicturetwo() {
                    if(imgPathList!=null&&imgPathList.size()>1){
                        return imgPathList.get(1);
                    }
                    return picturetwo;
                }

                public void setPicturetwo(String picturetwo) {
                    this.picturetwo = picturetwo;
                }

                public String getPicturethree() {
                    if(imgPathList!=null&&imgPathList.size()>2){
                        return imgPathList.get(2);
                    }
                    return picturethree;
                }

                public void setPicturethree(String picturethree) {
                    this.picturethree = picturethree;
                }

                public String getCllong() {
                    return cllong;
                }

                public void setCllong(String cllong) {
                    this.cllong = cllong;
                }

                public String getCllat() {
                    return cllat;
                }

                public void setCllat(String cllat) {
                    this.cllat = cllat;
                }

                public String getClemechanicalup() {
                    return clemechanicalup;
                }

                public void setClemechanicalup(String clemechanicalup) {
                    this.clemechanicalup = clemechanicalup;
                }

                public String getClmechanical() {
                    return clmechanical;
                }

                public void setClmechanical(String clmechanical) {
                    this.clmechanical = clmechanical;
                }

                public String getCllineexterior() {
                    return cllineexterior;
                }

                public void setCllineexterior(String cllineexterior) {
                    this.cllineexterior = cllineexterior;
                }
            }
        }
    }
}
