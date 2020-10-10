package com.example.samaritan;

import java.sql.Date;

public class User {
    public String address;
    public String city;
    public String email;
    public String mobile;
    public String name;
    public String pass;
    public String pincode;
    public String state;
    public  String imageUrl;
    String sender;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String datetime;
    public String dpurl;
    public String pid;

    public String getDpurl() {
        return dpurl;
    }

    public void setDpurl(String dpurl) {
        this.dpurl = dpurl;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String receiver;
    String message;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setTrustid(String trustid) {
        this.trustid = trustid;
    }

    public String trustid;
    public String userid;
    public String write;

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid2) {
        this.userid = userid2;
    }



    public String getTrustid() {
        return this.trustid;
    }



    public String getCity() {
        return this.city;
    }

    public void setCity(String city2) {
        this.city = city2;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state2) {
        this.state = state2;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String pincode2) {
        this.pincode = pincode2;
    }

    public String getName() {
        return this.name;
    }



    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile2) {
        this.mobile = mobile2;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass2) {
        this.pass = pass2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }
}
