package com.example.myapplication;

public class HelperClass {

    String username, phone, email,  password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass(String phone, String email, String username, String password) {
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public HelperClass() {
    }
}
