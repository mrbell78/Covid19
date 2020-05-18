package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Alluserdataresponse {


    @SerializedName("error")
    @Expose
    Boolean error;

    @SerializedName("msg")
    @Expose
    String msg;

    @SerializedName("data")
    @Expose
    List<Alluserdata> alluserdataList = null;


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Alluserdata> getAlluserdataList() {
        return alluserdataList;
    }

    public void setAlluserdataList(List<Alluserdata> alluserdataList) {
        this.alluserdataList = alluserdataList;
    }
}
