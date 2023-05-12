package com.springboot.www.entity;

public class Position {
    private int id;
    private String address;
    private int uid;
    private String tel;
    private String name;
    private boolean isDefault;
    private boolean isShown;

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean first) {
        isDefault = first;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setIsShown(boolean shown) {
        isShown = shown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
