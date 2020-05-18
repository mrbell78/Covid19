package com.criddam.covid_19criddam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class hospitalsugetion {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("address")
    @Expose
    String address;


    public hospitalsugetion(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
