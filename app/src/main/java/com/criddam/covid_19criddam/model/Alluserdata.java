package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alluserdata {

    @SerializedName("sl")
    @Expose
    int sl;
    @SerializedName("id")
    @Expose
    int id;
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


    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("type")
    @Expose
    String type;



    public Alluserdata() {
    }

    public Alluserdata(int sl, int id, String what_u_need, String how_soon_do_u_need_it, String what_u_supply, String what_u_supply_other, String how_soon_can_u_supply, String hospital, String location, String name, String mobile, String email, String type) {
        this.sl = sl;
        this.id = id;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
        this.what_u_supply = what_u_supply;
        this.what_u_supply_other = what_u_supply_other;
        this.how_soon_can_u_supply = how_soon_can_u_supply;
        this.hospital = hospital;
        this.location = location;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.type = type;
    }


    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
