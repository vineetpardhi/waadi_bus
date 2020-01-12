package com.example.siddhant.loginui;

import java.util.List;

public class cab {
    private String name1;
    private String address1;
    private String mobileno1;
    private String rtobranch1;
    private String rtoaddress1;
    private String dateofregistration1;
    private List<String> places;
    private String username1;
    private String agencyname1;
    private String cno;

    public String getCabno(){return cno;}

    public void setCabno(String cno){this.cno = cno;}

    public String getAgencyname (){return agencyname1;}

    public void setAgencyname(String agencyname1){this.agencyname1 = agencyname1; }

    public String getEmail() {
        return email1;
    }

    public void setEmail(String email) {
        this.email1 = email;
    }

    private String email1;

    public String getUsername() {
        return username1;
    }

    public void setUsername(String username) {
        this.username1 = username;
    }

    public String getPassword() {
        return password1;
    }

    public void setPassword(String password) {
        this.password1 = password;
    }

    private String password1;
    private String working1;

    public List<String> getplaces() {
        return places;
    }

    public void setplaces(List<String> arr) {
        this.places = arr;
    }
    private String Role;



    public String getWorking() {
        return working1;
    }

    public void setWorking(String working) {
        this.working1 = working;
    }


    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getName() {
        return name1;
    }

    public void setName(String name) {
        this.name1 = name;
    }

    public String getAddress() {
        return address1;
    }

    public void setAddress(String address) {
        this.address1 = address;
    }

    public String getMobileno() {
        return mobileno1;
    }

    public void setMobileno(String mobileno) {
        this.mobileno1 = mobileno;
    }

    public String getRtobranch() {
        return rtobranch1;
    }

    public void setRtobranch(String rtobranch) {
        this.rtobranch1 = rtobranch;
    }

    public String getRtoaddress() {
        return rtoaddress1;
    }

    public void setRtoaddress(String rtoaddress) {
        this.rtoaddress1 = rtoaddress;
    }

    public String getDateofregistration() {
        return dateofregistration1;
    }

    public void setDateofregistration(String dateofregistration) {
        this.dateofregistration1 = dateofregistration;
    }



}