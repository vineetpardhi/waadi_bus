package com.example.siddhant.loginui;

public class Bus_data {


    private String bus_name;
    private String bus_time;
    private String num_of_stops;
    private String bus_number;

    public  Bus_data()
    {

    }

    public Bus_data(String bus_name, String bus_time, String num_of_stops, String bus_number) {
        this.bus_name = bus_name;
        this.bus_time = bus_time;
        this.num_of_stops = num_of_stops;
        this.bus_number = bus_number;
    }


    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_time() {
        return bus_time;
    }

    public void setBus_time(String bus_time) {
        this.bus_time = bus_time;
    }

    public String getNum_of_stops() {
        return num_of_stops;
    }

    public void setNum_of_stops(String num_of_stops) {
        this.num_of_stops = num_of_stops;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }
}
