package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class CellinfoListBean implements Serializable {
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
    private List<String> coverList=new ArrayList<>();
    private List<ShiFenBaseInfoAntennaBean> antennaList;
    private List<ShiFenBBU_RRUBean> rrulist;
//    private String rrulist;

    private String rruaddressinfo;

    public String getRruaddressinfo() {
        return rruaddressinfo;
    }

    public void setRruaddressinfo(String rruaddressinfo) {
        this.rruaddressinfo = rruaddressinfo;
    }

    public List<ShiFenBBU_RRUBean> getRrulist() {
        return rrulist;
    }

    public void setRrulist(List<ShiFenBBU_RRUBean> rrulist) {
        this.rrulist = rrulist;
    }

    /*
        public List<ShiFenBBU_RRUBean> getRruList() {
            if(rrulist==null)
            {
                rrulist= new ArrayList<>();
                for(int i=0;i<4;i++){
                    rrulist.add(new ShiFenBBU_RRUBean("aaaaaaaa"+i,"","",""));
                }
                return rrulist;
            }
            return rrulist;
        }

        public void setRruList(List<ShiFenBBU_RRUBean> rrulist) {
            this.rrulist = rrulist;
        }*/
    public void setCoverList(List<String> coverList) {
        this.coverList = coverList;
    }

    public List<ShiFenBaseInfoAntennaBean> getAntennaList() {
        if(antennaList==null){
            antennaList =new ArrayList<>();
            for(int i=0;i<1;i++){
                antennaList.add(new ShiFenBaseInfoAntennaBean("","",""));
            }
            return antennaList;
        }
        return antennaList;
    }

    public void setAntennaList(List<ShiFenBaseInfoAntennaBean> antennaList) {
        this.antennaList = antennaList;
    }


    public List<String> getCoverList() {
        return coverList;
    }

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
        return lineinfoList;
    }

    public void setLineinfoList(List<LineinfoListBean> lineinfoList) {
        this.lineinfoList = lineinfoList;
    }

    public  class LineinfoListBean implements Serializable {
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

        public void setImgPathList(List<String> imgPathList) {
            this.imgPathList = imgPathList;
        }

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
