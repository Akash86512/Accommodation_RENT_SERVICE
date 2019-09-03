package com.example.akashkumar.room;

public class FetchData {

    String id, latitude, longitude, address, roomdetails, url;

    public FetchData() {
    }

    public FetchData(String id, String latitude, String longitude, String address, String roomdetails, String url) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.roomdetails = roomdetails;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomdetails() {
        return roomdetails;
    }

    public void setRoomdetails(String roomdetails) {
        this.roomdetails = roomdetails;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
