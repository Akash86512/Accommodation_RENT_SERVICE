package com.example.akashkumar.room;

public class MyAdUserChat {

    String mobilenumber,name ,id,ownermobilenumber;

    public MyAdUserChat() {
    }

    public MyAdUserChat(String mobilenumber, String name, String id, String ownermobilenumber) {
        this.mobilenumber = mobilenumber;
        this.name = name;
        this.id = id;
        this.ownermobilenumber = ownermobilenumber;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnermobilenumber() {
        return ownermobilenumber;
    }

    public void setOwnermobilenumber(String ownermobilenumber) {
        this.ownermobilenumber = ownermobilenumber;
    }
}
