package com.example.emmanuel.lisheapp.user;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String region;
    private int age;
    private String sex;
    private String regDate;

    public User(){

    }

    public User(int id){
        this.id = id;
    }

    public User (int id,String fname, String lname){
        this(id);
        this.fname = fname;
        this.lname = lname;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFname(String fname){
        this.fname = fname;
    }

    public void setLname(String lname){
        this.lname = lname;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public void setRegDate(String regDate){
        this.regDate = regDate;
    }

    public int getId(){
        return this.id;
    }

    public String getFname(){
        return this.fname;
    }

    public String getLname(){
        return this.lname;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getEmail(){
        return this.email;
    }

    public int getAge(){
        return this.age;
    }

    public String getRegion(){
        return this.region;
    }

    public String getSex(){
        return this.sex;
    }

    public String getRegDate(){
        return this.regDate;
    }


}
