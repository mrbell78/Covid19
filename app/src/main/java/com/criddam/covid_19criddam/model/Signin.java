package com.criddam.covid_19criddam.model;

public class Signin {

    String mobile;
    String password;

    public Signin(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Signin() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
