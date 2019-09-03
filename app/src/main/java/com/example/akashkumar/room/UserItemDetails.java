package com.example.akashkumar.room;

public class UserItemDetails {

    String imageurl,ownername,totalprice,rentdesription;
    UserId userId;

    public UserItemDetails() {
    }

    public UserItemDetails(String imageurl, String ownername, String totalprice, String rentdesription, UserId userId) {
        this.imageurl = imageurl;
        this.ownername = ownername;
        this.totalprice = totalprice;
        this.rentdesription = rentdesription;
        this.userId = userId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public String getRentdesription() {
        return rentdesription;
    }

    public UserId getUserId() {
        return userId;
    }
}
