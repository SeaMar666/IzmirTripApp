package com.example.izmirtripapp.Model;

public class User {
    String name, lastName, email, phoneNo, password;

    public User() {
    }

    public User(String name, String lastName, String email, String phoneNo, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return lastName;
    }

    public void setUsername(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
