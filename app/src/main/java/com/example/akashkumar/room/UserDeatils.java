package com.example.akashkumar.room;

public class UserDeatils {
    String address,roomdeails,url,userid;


    public UserDeatils() {
    }

    public UserDeatils(String address, String roomdeails, String url, String userid) {
        this.address = address;
        this.roomdeails = roomdeails;
        this.url = url;
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomdeails() {
        return roomdeails;
    }

    public void setRoomdeails(String roomdeails) {
        this.roomdeails = roomdeails;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
