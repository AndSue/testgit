package com.springboot.www.entity;

public class User {
    private int id;
    private String name;
    private String password;
    private String tel;
    private String job;
    private String photo;
    private String sex;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getTel(){
        return this.tel;
    }
    public void setTel(String tel){
        this.tel=tel;
    }
    public String getJob(){
        return this.job;
    }
    public void setJob(String job){
        this.job=job;
    }
    public String getSex(){
        return this.sex;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public String getPhoto(){
        return this.photo;
    }
    public void setPhoto(String photo){
        this.photo=photo;
    }
}
