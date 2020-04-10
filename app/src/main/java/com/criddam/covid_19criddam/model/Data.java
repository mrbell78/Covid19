package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("usertype")
    @Expose
    String usertype;


    @SerializedName("fullname")
    @Expose
    String fullname;


    @SerializedName("mobile")
    @Expose
    String mobile;



    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("what_u_need")
    @Expose
    String what_u_need;

    @SerializedName("how_soon_do_u_need_it")
    @Expose
    String how_soon_do_u_need_it;

    @SerializedName("what_u_supply")
    @Expose
    String what_u_supply;

    @SerializedName("what_u_supply_other")
    @Expose
    String what_u_supply_other;

    @SerializedName("how_soon_can_u_supply")
    @Expose
    String how_soon_can_u_supply;
    @SerializedName("hospital")
    @Expose
    String hospital;

    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("email_verified_at")
    @Expose
    String email_verified_at;

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("updated_at")
    @Expose
    String updated_at;


    public Data(String usertype, String fullname, String mobile, String username, String location, String what_u_need, String how_soon_do_u_need_it, String hospital, String email, String password) {
        this.usertype = usertype;
        this.fullname = fullname;
        this.mobile = mobile;
        this.username = username;
        this.location = location;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
        this.hospital = hospital;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getWhat_u_supply() {
        return what_u_supply;
    }

    public void setWhat_u_supply(String what_u_supply) {
        this.what_u_supply = what_u_supply;
    }

    public String getWhat_u_supply_other() {
        return what_u_supply_other;
    }

    public void setWhat_u_supply_other(String what_u_supply_other) {
        this.what_u_supply_other = what_u_supply_other;
    }

    public String getHow_soon_can_u_supply() {
        return how_soon_can_u_supply;
    }

    public void setHow_soon_can_u_supply(String how_soon_can_u_supply) {
        this.how_soon_can_u_supply = how_soon_can_u_supply;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
