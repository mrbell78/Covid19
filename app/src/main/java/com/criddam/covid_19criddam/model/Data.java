package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("mobile")
    @Expose
    String mobile;

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

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("updated_at")
    @Expose
    String updated_at;

    public Data(int id, String mobile, String what_u_need, String how_soon_do_u_need_it, String what_u_supply, String what_u_supply_other, String how_soon_can_u_supply, String hospital, String location) {
        this.id = id;
        this.mobile = mobile;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
        this.what_u_supply = what_u_supply;
        this.what_u_supply_other = what_u_supply_other;
        this.how_soon_can_u_supply = how_soon_can_u_supply;
        this.hospital = hospital;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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


    /*  public Data(String mobile, String what_u_need, String how_soon_do_u_need_it, String what_u_supply, String what_u_supply_other, String how_soon_can_u_supply, String hospital, String location) {
        this.mobile = mobile;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
        this.what_u_supply = what_u_supply;
        this.what_u_supply_other = what_u_supply_other;
        this.how_soon_can_u_supply = how_soon_can_u_supply;
        this.hospital = hospital;
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    }*/
}
