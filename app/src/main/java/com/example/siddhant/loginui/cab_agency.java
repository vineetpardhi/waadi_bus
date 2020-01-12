package com.example.siddhant.loginui;
import java.util.List;

public class cab_agency {
    private String ownername2;
    private String agencyname2;
    private String password2;
    private String cno2;

    public String getPassword() {
        return password2;
    }

    public void setPassword(String password) {
        this.password2 = password;
    }

    public String getCabno(){return cno2;}
    public void setCabno(String cno2){this.cno2=cno2;}


    public String getOwnername() {
        return ownername2;
    }

    public void setOwnername(String ownername) {
        this.ownername2 = ownername;
    }

    public String getAgencyname() {
        return agencyname2;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname2 = agencyname;
    }

    public String getAgencyaddress() {
        return agencyaddress;
    }

    public void setAgencyaddress(String agencyaddress) {
        this.agencyaddress = agencyaddress;
    }

    public String getAgencyphone() {
        return agencyphone;
    }

    public void setAgencyphone(String agencyphone) {
        this.agencyphone = agencyphone;
    }

    private String agencyaddress;
    private String agencyphone;



}