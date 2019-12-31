package com.example.siddhant.loginui;

public class routes_list {
    private String Station_name;
    private String Station_number;


    public routes_list(String station_name,String station_number) {
        Station_name = station_name;
        Station_number = station_number;

    }

    public String getStation_name() {
        return Station_name;
    }

    public void setStation_name(String station_name) {
        Station_name = station_name;
    }


    public String getStation_number() {
        return Station_number;
    }

    public void setStation_number(String station_number) {
        Station_number = station_number;
    }
}
