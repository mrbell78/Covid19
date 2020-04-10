package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.SerializedName;

public class Getdata_doctor {


    @SerializedName("error")
    boolean error;
    @SerializedName("msg")
    String msg;


    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }
}
