package com.beidian.beidiannonxinling.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/29.
 */
@Entity
public class LineData {


    /**
     * id : 1
     * eci : 27742476
     * enodebid : 460-00-498147
     * cellname : 闵东莲HL1H_12
     * linenum : 2
     * BBUnum : 2
     * RRUnum : 2
     * pci : 13
     * frequency : 38400
     * workfrequency : F频段
     * downbandwidth : 20M
     * lng : 121
     * lat : 31
     * Coveragetype : 室外
     * dishi : 上海市
     * quxian : 闵行
     * lineservice : TYDA-202616D4T6
     * linetype : 美化
     * directionangle : 0
     * mechanicalinclination : 0
     * antennaheight : 30
     */
    @Id(autoincrement = true)
    private Long ids;
    private String eci;
    private String enodebid;
    private String cellname;
    private String linenum;
    private String BBUnum;
    private String RRUnum;
    private String pci;
    private String frequency;
    private String workfrequency;
    private String downbandwidth;
    private String lng;
    private String lat;
    private String Coveragetype;
    private String dishi;
    private String quxian;
    private String lineservice;
    private String linetype;
    private String directionangle;
    private String mechanicalinclination;
    private String antennaheight;
    public String getAntennaheight() {
        return this.antennaheight;
    }
    public void setAntennaheight(String antennaheight) {
        this.antennaheight = antennaheight;
    }
    public String getMechanicalinclination() {
        return this.mechanicalinclination;
    }
    public void setMechanicalinclination(String mechanicalinclination) {
        this.mechanicalinclination = mechanicalinclination;
    }
    public String getDirectionangle() {
        return this.directionangle;
    }
    public void setDirectionangle(String directionangle) {
        this.directionangle = directionangle;
    }
    public String getLinetype() {
        return this.linetype;
    }
    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }
    public String getLineservice() {
        return this.lineservice;
    }
    public void setLineservice(String lineservice) {
        this.lineservice = lineservice;
    }
    public String getQuxian() {
        return this.quxian;
    }
    public void setQuxian(String quxian) {
        this.quxian = quxian;
    }
    public String getDishi() {
        return this.dishi;
    }
    public void setDishi(String dishi) {
        this.dishi = dishi;
    }
    public String getCoveragetype() {
        return this.Coveragetype;
    }
    public void setCoveragetype(String Coveragetype) {
        this.Coveragetype = Coveragetype;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return this.lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getDownbandwidth() {
        return this.downbandwidth;
    }
    public void setDownbandwidth(String downbandwidth) {
        this.downbandwidth = downbandwidth;
    }
    public String getWorkfrequency() {
        return this.workfrequency;
    }
    public void setWorkfrequency(String workfrequency) {
        this.workfrequency = workfrequency;
    }
    public String getFrequency() {
        return this.frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getPci() {
        return this.pci;
    }
    public void setPci(String pci) {
        this.pci = pci;
    }
    public String getRRUnum() {
        return this.RRUnum;
    }
    public void setRRUnum(String RRUnum) {
        this.RRUnum = RRUnum;
    }
    public String getBBUnum() {
        return this.BBUnum;
    }
    public void setBBUnum(String BBUnum) {
        this.BBUnum = BBUnum;
    }
    public String getLinenum() {
        return this.linenum;
    }
    public void setLinenum(String linenum) {
        this.linenum = linenum;
    }
    public String getCellname() {
        return this.cellname;
    }
    public void setCellname(String cellname) {
        this.cellname = cellname;
    }
    public String getEnodebid() {
        return this.enodebid;
    }
    public void setEnodebid(String enodebid) {
        this.enodebid = enodebid;
    }
    public String getEci() {
        return this.eci;
    }
    public void setEci(String eci) {
        this.eci = eci;
    }
    public Long getIds() {
        return this.ids;
    }
    public void setIds(Long ids) {
        this.ids = ids;
    }
    @Generated(hash = 121486724)
    public LineData(Long ids, String eci, String enodebid, String cellname,
            String linenum, String BBUnum, String RRUnum, String pci,
            String frequency, String workfrequency, String downbandwidth,
            String lng, String lat, String Coveragetype, String dishi,
            String quxian, String lineservice, String linetype,
            String directionangle, String mechanicalinclination,
            String antennaheight) {
        this.ids = ids;
        this.eci = eci;
        this.enodebid = enodebid;
        this.cellname = cellname;
        this.linenum = linenum;
        this.BBUnum = BBUnum;
        this.RRUnum = RRUnum;
        this.pci = pci;
        this.frequency = frequency;
        this.workfrequency = workfrequency;
        this.downbandwidth = downbandwidth;
        this.lng = lng;
        this.lat = lat;
        this.Coveragetype = Coveragetype;
        this.dishi = dishi;
        this.quxian = quxian;
        this.lineservice = lineservice;
        this.linetype = linetype;
        this.directionangle = directionangle;
        this.mechanicalinclination = mechanicalinclination;
        this.antennaheight = antennaheight;
    }
    @Generated(hash = 657526810)
    public LineData() {
    }

}
