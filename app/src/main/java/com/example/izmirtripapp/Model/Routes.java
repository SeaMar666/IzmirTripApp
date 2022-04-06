package com.example.izmirtripapp.Model;

public class Routes {

    private String address;
    private String addressTitle;
    private String Latitude;
    private String Longitude;
    private String username;
    private String date;
    private String time;

    public Routes() {
    }

    public Routes(String address, String addressTitle, String latitude, String longitude, String username, String date, String time) {
        this.address = address;
        this.addressTitle = addressTitle;
        Latitude = latitude;
        Longitude = longitude;
        this.username = username;
        this.date = date;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
