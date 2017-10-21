package com.beidian.beidiannonxinling.bean;

import java.util.List;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/10/1523:50
 * @描述: ------------------------------------------
 */

public class SiteSqlBean {
    /**
     * enodeb : 108369
     * directionangle2 : 207
     * city : 上海市
     * directionangle3 : 68
     * directionangle1 : 139
     * county : 闵行
     * eci : 27742476
     * frequency : 38400
     * lineservice3 : TYDA-202616D4T6
     * lineservice4 : TYDA-202616D4T6
     * lineservice1 : TYDA-202616D4T6
     * lineservice2 : TYDA-202616D4T6
     * tac : 6237
     * pci : 13
     * linetype2 : 普通
     * sitename : 闵东莲HL1H
     * linetype3 : 美化
     * linenum : 2
     * linetype4 : 美化
     * id : 11547
     * mechanicalinclination2 : 12
     * mechanicalinclination1 : 14
     * lat : 31.25845
     * linetype1 : 美化
     * mechanicalinclination4 : 15
     * coveragetype : 室外
     * mechanicalinclination3 : 11
     * lng : 121.622187
     * bbunum :
     * workfrequency : F频段
     * enodebid :
     * downbandwidth : 20M
     * rrunum :
     * antennaheight1 : 12
     * linelist : [{"linetype":"美化","antennaheight":"12","directionangle":"139","mechanicalinclination":"14","lineservice":"TYDA-202616D4T6"},{"linetype":"普通","antennaheight":"15","directionangle":"207","mechanicalinclination":"12","lineservice":"TYDA-202616D4T6"},{"linetype":"美化","antennaheight":"14","directionangle":"68","mechanicalinclination":"11","lineservice":"TYDA-202616D4T6"},{"linetype":"美化","antennaheight":"17","directionangle":"114","mechanicalinclination":"15","lineservice":"TYDA-202616D4T6"}]
     * cellname : 闵东莲HL1H_12
     * antennaheight3 : 14
     * antennaheight2 : 15
     * directionangle4 : 114
     * antennaheight4 : 17
     */

    private String enodeb;
    private String directionangle2;
    private String city;
    private String directionangle3;
    private String directionangle1;
    private String county;
    private String eci;
    private String frequency;
    private String lineservice3;
    private String lineservice4;
    private String lineservice1;
    private String lineservice2;
    private String tac;
    private String pci;
    private String linetype2;
    private String sitename;
    private String linetype3;
    private String linenum;
    private String linetype4;
    private int    id;
    private String mechanicalinclination2;
    private String mechanicalinclination1;
    private double lat;
    private String linetype1;
    private String mechanicalinclination4;
    private String coveragetype;
    private String mechanicalinclination3;
    private double lng;
    private String bbunum;
    private String workfrequency;
    private String enodebid;
    private String downbandwidth;
    private String rrunum;
    private String antennaheight1;
    private String cellname;
    private String antennaheight3;
    private String antennaheight2;
    private String directionangle4;
    private String antennaheight4;
    /**
     * linetype : 美化
     * antennaheight : 12
     * directionangle : 139
     * mechanicalinclination : 14
     * lineservice : TYDA-202616D4T6
     */

    private List<LinelistBean> linelist;

    public String getEnodeb() {
        return enodeb;
    }

    public void setEnodeb(String enodeb) {
        this.enodeb = enodeb;
    }

    public String getDirectionangle2() {
        return directionangle2;
    }

    public void setDirectionangle2(String directionangle2) {
        this.directionangle2 = directionangle2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirectionangle3() {
        return directionangle3;
    }

    public void setDirectionangle3(String directionangle3) {
        this.directionangle3 = directionangle3;
    }

    public String getDirectionangle1() {
        return directionangle1;
    }

    public void setDirectionangle1(String directionangle1) {
        this.directionangle1 = directionangle1;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getLineservice3() {
        return lineservice3;
    }

    public void setLineservice3(String lineservice3) {
        this.lineservice3 = lineservice3;
    }

    public String getLineservice4() {
        return lineservice4;
    }

    public void setLineservice4(String lineservice4) {
        this.lineservice4 = lineservice4;
    }

    public String getLineservice1() {
        return lineservice1;
    }

    public void setLineservice1(String lineservice1) {
        this.lineservice1 = lineservice1;
    }

    public String getLineservice2() {
        return lineservice2;
    }

    public void setLineservice2(String lineservice2) {
        this.lineservice2 = lineservice2;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getLinetype2() {
        return linetype2;
    }

    public void setLinetype2(String linetype2) {
        this.linetype2 = linetype2;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getLinetype3() {
        return linetype3;
    }

    public void setLinetype3(String linetype3) {
        this.linetype3 = linetype3;
    }

    public String getLinenum() {
        return linenum;
    }

    public void setLinenum(String linenum) {
        this.linenum = linenum;
    }

    public String getLinetype4() {
        return linetype4;
    }

    public void setLinetype4(String linetype4) {
        this.linetype4 = linetype4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMechanicalinclination2() {
        return mechanicalinclination2;
    }

    public void setMechanicalinclination2(String mechanicalinclination2) {
        this.mechanicalinclination2 = mechanicalinclination2;
    }

    public String getMechanicalinclination1() {
        return mechanicalinclination1;
    }

    public void setMechanicalinclination1(String mechanicalinclination1) {
        this.mechanicalinclination1 = mechanicalinclination1;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLinetype1() {
        return linetype1;
    }

    public void setLinetype1(String linetype1) {
        this.linetype1 = linetype1;
    }

    public String getMechanicalinclination4() {
        return mechanicalinclination4;
    }

    public void setMechanicalinclination4(String mechanicalinclination4) {
        this.mechanicalinclination4 = mechanicalinclination4;
    }

    public String getCoveragetype() {
        return coveragetype;
    }

    public void setCoveragetype(String coveragetype) {
        this.coveragetype = coveragetype;
    }

    public String getMechanicalinclination3() {
        return mechanicalinclination3;
    }

    public void setMechanicalinclination3(String mechanicalinclination3) {
        this.mechanicalinclination3 = mechanicalinclination3;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBbunum() {
        return bbunum;
    }

    public void setBbunum(String bbunum) {
        this.bbunum = bbunum;
    }

    public String getWorkfrequency() {
        return workfrequency;
    }

    public void setWorkfrequency(String workfrequency) {
        this.workfrequency = workfrequency;
    }

    public String getEnodebid() {
        return enodebid;
    }

    public void setEnodebid(String enodebid) {
        this.enodebid = enodebid;
    }

    public String getDownbandwidth() {
        return downbandwidth;
    }

    public void setDownbandwidth(String downbandwidth) {
        this.downbandwidth = downbandwidth;
    }

    public String getRrunum() {
        return rrunum;
    }

    public void setRrunum(String rrunum) {
        this.rrunum = rrunum;
    }

    public String getAntennaheight1() {
        return antennaheight1;
    }

    public void setAntennaheight1(String antennaheight1) {
        this.antennaheight1 = antennaheight1;
    }

    public String getCellname() {
        return cellname;
    }

    public void setCellname(String cellname) {
        this.cellname = cellname;
    }

    public String getAntennaheight3() {
        return antennaheight3;
    }

    public void setAntennaheight3(String antennaheight3) {
        this.antennaheight3 = antennaheight3;
    }

    public String getAntennaheight2() {
        return antennaheight2;
    }

    public void setAntennaheight2(String antennaheight2) {
        this.antennaheight2 = antennaheight2;
    }

    public String getDirectionangle4() {
        return directionangle4;
    }

    public void setDirectionangle4(String directionangle4) {
        this.directionangle4 = directionangle4;
    }

    public String getAntennaheight4() {
        return antennaheight4;
    }

    public void setAntennaheight4(String antennaheight4) {
        this.antennaheight4 = antennaheight4;
    }

    public List<LinelistBean> getLinelist() {
        return linelist;
    }

    public void setLinelist(List<LinelistBean> linelist) {
        this.linelist = linelist;
    }

    public static class LinelistBean {
        private String linetype;
        private String antennaheight;
        private String directionangle;
        private String mechanicalinclination;
        private String lineservice;

        public String getLinetype() {
            return linetype;
        }

        public void setLinetype(String linetype) {
            this.linetype = linetype;
        }

        public String getAntennaheight() {
            return antennaheight;
        }

        public void setAntennaheight(String antennaheight) {
            this.antennaheight = antennaheight;
        }

        public String getDirectionangle() {
            return directionangle;
        }

        public void setDirectionangle(String directionangle) {
            this.directionangle = directionangle;
        }

        public String getMechanicalinclination() {
            return mechanicalinclination;
        }

        public void setMechanicalinclination(String mechanicalinclination) {
            this.mechanicalinclination = mechanicalinclination;
        }

        public String getLineservice() {
            return lineservice;
        }

        public void setLineservice(String lineservice) {
            this.lineservice = lineservice;
        }
    }
}
