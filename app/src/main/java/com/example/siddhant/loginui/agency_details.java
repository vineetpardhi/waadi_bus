package com.example.siddhant.loginui;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class agency_details {
    private String agency_name;
    private String agency_address;
    private String agency_number;
    private List<String> vh;


    public  agency_details()
    {

    }
    public  agency_details(String agency_name,String agency_address,String agency_number,List<String> vh)
    {
        this.agency_name=agency_name;
        this.agency_address=agency_address;
        this.agency_number=agency_number;
        this.vh.addAll(vh);


    }


    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getAgency_address() {
        return agency_address;
    }

    public void setAgency_address(String agency_address) {
        this.agency_address = agency_address;
    }

    public String getAgency_number() {
        return agency_number;
    }

    public void setAgency_number(String agency_number) {
        this.agency_number = agency_number;
    }

    public List<String> getVh() {
        return vh;
    }

    public void setVh(List<String> vh) {

        this.vh=vh;


    }
}
