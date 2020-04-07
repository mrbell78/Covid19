package com.criddam.covid_19criddam.model;

import retrofit2.http.Field;

public class Post_supply {


    String usertype,fullname,mobile,email,what_u_supply,how_soon_can_u_supply,password,location,what_u_supply_other;



    public Post_supply(String usertype, String fullname, String mobile, String email, String what_u_supply, String how_soon_can_u_supply, String password, String location,String what_u_supply_other) {
        this.usertype = usertype;
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.what_u_supply = what_u_supply;
        this.how_soon_can_u_supply = how_soon_can_u_supply;
        this.password = password;
        this.location = location;
        this.what_u_supply_other=what_u_supply_other;
    }

    public String getWhat_u_supply_other() {
        return what_u_supply_other;
    }

    public void setWhat_u_supply_other(String what_u_supply_other) {
        this.what_u_supply_other = what_u_supply_other;
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

    public String getWhat_u_supply() {
        return what_u_supply;
    }

    public void setWhat_u_supply(String what_u_supply) {
        this.what_u_supply = what_u_supply;
    }

    public String getHow_soon_can_u_supply() {
        return how_soon_can_u_supply;
    }

    public void setHow_soon_can_u_supply(String how_soon_can_u_supply) {
        this.how_soon_can_u_supply = how_soon_can_u_supply;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
