package com.beidian.beidiannonxinling.bean;

/**
 * Created by Eric on 2017/9/19.
 */

public class LoginInfoBean {
    private String name;
    private String password;

    public LoginInfoBean(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
