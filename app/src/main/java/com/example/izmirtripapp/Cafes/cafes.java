package com.example.izmirtripapp.Cafes;

public class cafes {
    String name, image, address;

    public cafes() {

    }

    public cafes(String name, String image, String address) {
        this.name = name;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
