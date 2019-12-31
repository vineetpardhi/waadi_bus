package com.example.siddhant.loginui;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class agency_details {
    private String agency_name;
    private String agency_address;
    private String agency_number;
    private String[] vh;
    private List vlist;

    public  agency_details()
    {

    }
    public  agency_details(String agency_name,String agency_address,String agency_number,String[] vh,List vlist)
    {
        this.agency_name=agency_name;
        this.agency_address=agency_address;
        this.agency_number=agency_number;
        this.vh=vh;
        this.vlist=vlist;

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

    public String[] getVh() {
        return vh;
    }

    public void setVh(String[] vh) {

        this.vlist=new ArrayList<String>(Arrays.asList(vh));


    }
}
