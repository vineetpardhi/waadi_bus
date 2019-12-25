package com.example.siddhant.loginui;

import java.util.List;

public class Driver {
    private String name;
    private String address;
    private String mobileno;
    private String rtobranch;
    private String rtoaddress;
    private String dateofregistration;
    private List<String> places;
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

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

    private String password;
    private String working;

    public List<String> getplaces() {
        return places;
    }

    public void setplaces(List<String> arr) {
        this.places = arr;
    }
    private String Role;



    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }


    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getRtobranch() {
        return rtobranch;
    }

    public void setRtobranch(String rtobranch) {
        this.rtobranch = rtobranch;
    }

    public String getRtoaddress() {
        return rtoaddress;
    }

    public void setRtoaddress(String rtoaddress) {
        this.rtoaddress = rtoaddress;
    }

    public String getDateofregistration() {
        return dateofregistration;
    }

    public void setDateofregistration(String dateofregistration) {
        this.dateofregistration = dateofregistration;
    }



}