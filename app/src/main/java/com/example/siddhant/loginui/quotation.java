package com.example.siddhant.loginui;

public class quotation {


    public String source;
    public String destination;
    public String dt1;
    public String dt2;
    public String bustype;
    public long no_of_seates;
    public String trip;

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDt1() {
        return dt1;
    }

    public void setDt1(String dt1) {
        this.dt1 = dt1;
    }

    public String getDt2() {
        return dt2;
    }

    public void setDt2(String dt2) {
        this.dt2 = dt2;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }

    public long getNo_of_seates() {
        return no_of_seates;
    }

    public void setNo_of_seates(long no_of_seates) {
        this.no_of_seates = no_of_seates;
    }

    public quotation()
    {

    }

    public quotation(String source,String destination,String dt1,String dt2,String bustype,long no_of_seates)
    {

        this.source=source;
        this.destination=destination;
        this.dt1=dt1;
        this.dt2=dt2;
        this.bustype=bustype;
        this.no_of_seates=no_of_seates;
    }
}
