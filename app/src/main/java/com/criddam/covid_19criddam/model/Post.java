package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Post {

    String usertype;
    String fullname;
    String mobile;
    String email;
    @SerializedName("body")
    String what_u_need;
    String how_soon_do_u_need_it;
    String password;
    String hospital;
    String location;

    public Post() {
    }

    public Post(String usertype, String fullname, String mobile, String email, String what_u_need, String how_soon_do_u_need_it, String password, String hospital, String location) {
        this.usertype = usertype;
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
        this.password = password;
        this.hospital = hospital;
        this.location = location;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhat_u_need() {
        return what_u_need;
    }

    public void setWhat_u_need(String what_u_need) {
        this.what_u_need = what_u_need;
    }

    public String getHow_soon_do_u_need_it() {
        return how_soon_do_u_need_it;
    }

    public void setHow_soon_do_u_need_it(String how_soon_do_u_need_it) {
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
