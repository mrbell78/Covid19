package com.criddam.covid_19criddam.firebasemodel;

public class Firebase_productModel {

    String pName;
    String pDetails;
    String pModel;
    String pArea;
    String pPrice;
    String img1;
    String img2;
    String img3;
    String img4;

    public Firebase_productModel() {
    }

    public Firebase_productModel(String pName, String pDetails, String pModel, String pArea, String pPrice, String img1, String img2, String img3, String img4) {
        this.pName = pName;
        this.pDetails = pDetails;
        this.pModel = pModel;
        this.pArea = pArea;
        this.pPrice = pPrice;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDetails() {
        return pDetails;
    }

    public void setpDetails(String pDetails) {
        this.pDetails = pDetails;
    }

    public String getpModel() {
        return pModel;
    }

    public void setpModel(String pModel) {
        this.pModel = pModel;
    }

    public String getpArea() {
        return pArea;
    }

    public void setpArea(String pArea) {
        this.pArea = pArea;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }
}
