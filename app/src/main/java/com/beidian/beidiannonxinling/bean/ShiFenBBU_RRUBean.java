package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

/**
 * Created by Eric on 2017/9/22.
 */

public class ShiFenBBU_RRUBean  implements Serializable {

    private String adressInfo;
    private String imageUrl;
    private String infactAdress;
    private String planAdress;
    private String title;
    private String pos;
    /*
    public ShiFenBBU_RRUBean(String planInfo, String planAddress, String inFactAddress, String addressPhoto) {
        this.planInfo = planInfo;
        this.planAddress = planAddress;
        this.inFactAddress = inFactAddress;
        this.addressPhoto = addressPhoto;
    }*/

    public ShiFenBBU_RRUBean(String title,String adressInfo, String infactAdress, String planAdress,  String pos) {
        this.adressInfo = adressInfo;
        this.infactAdress = infactAdress;
        this.planAdress = planAdress;
        this.title = title;
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAdressInfo() {
        return adressInfo;
    }

    public void setAdressInfo(String adressInfo) {
        this.adressInfo = adressInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInfactAdress() {
        return infactAdress;
    }

    public void setInfactAdress(String infactAdress) {
        this.infactAdress = infactAdress;
    }

    public String getPlanAdress() {
        return planAdress;
    }

    public void setPlanAdress(String planAdress) {
        this.planAdress = planAdress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
