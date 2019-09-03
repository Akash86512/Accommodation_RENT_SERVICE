package com.example.akashkumar.room;

public class UploadData {

    String userid, latitude, longitude,  imageurl,ownerlocation,ownername,roomtype,bentoutTo,bacheloer,bathroom,floornumber,diamention,totalprice,securitymoney,fulladress,rentdesription,mobilenumber;

    public UploadData() {
    }

    public UploadData(String userid, String latitude, String longitude, String imageurl, String ownerlocation, String ownername, String roomtype, String bentoutTo, String bacheloer, String bathroom, String floornumber, String diamention, String totalprice, String securitymoney, String fulladress, String rentdesription, String mobilenumber) {
        this.userid = userid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageurl = imageurl;
        this.ownerlocation = ownerlocation;
        this.ownername = ownername;
        this.roomtype = roomtype;
        this.bentoutTo = bentoutTo;
        this.bacheloer = bacheloer;
        this.bathroom = bathroom;
        this.floornumber = floornumber;
        this.diamention = diamention;
        this.totalprice = totalprice;
        this.securitymoney = securitymoney;
        this.fulladress = fulladress;
        this.rentdesription = rentdesription;
        this.mobilenumber = mobilenumber;
    }

    public String getUserid() {
        return userid;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getOwnerlocation() {
        return ownerlocation;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public String getBentoutTo() {
        return bentoutTo;
    }

    public String getBacheloer() {
        return bacheloer;
    }

    public String getBathroom() {
        return bathroom;
    }

    public String getFloornumber() {
        return floornumber;
    }

    public String getDiamention() {
        return diamention;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public String getSecuritymoney() {
        return securitymoney;
    }

    public String getFulladress() {
        return fulladress;
    }

    public String getRentdesription() {
        return rentdesription;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }
}
