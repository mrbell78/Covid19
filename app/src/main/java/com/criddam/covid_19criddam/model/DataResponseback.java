package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataResponseback {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("what_u_need")
    @Expose
    String what_u_need;

    @SerializedName("how_soon_do_u_need_it")
    @Expose
    String how_soon_do_u_need_it;

    public DataResponseback(int id, String what_u_need, String how_soon_do_u_need_it) {
        this.id = id;
        this.what_u_need = what_u_need;
        this.how_soon_do_u_need_it = how_soon_do_u_need_it;
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
}
