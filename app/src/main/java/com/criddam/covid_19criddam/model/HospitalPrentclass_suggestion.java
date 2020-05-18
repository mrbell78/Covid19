package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalPrentclass_suggestion {


    @SerializedName("error")
    @Expose
    boolean error;

    @SerializedName("msg")
    @Expose
    String msg;

    @SerializedName("data")
    @Expose
    List<hospitalsugetion> lisdata  = null;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<hospitalsugetion> getLisdata() {
        return lisdata;
    }

    public void setLisdata(List<hospitalsugetion> lisdata) {
        this.lisdata = lisdata;
    }
}
