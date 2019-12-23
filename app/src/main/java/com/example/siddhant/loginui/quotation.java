package com.example.siddhant.loginui;

public class quotation {


    public String source;
    public String destination;
    public String dt1;
    public String dt2;
    public String bustype;
    public int no_of_seates;




    public quotation()
    {

    }

    public quotation(String source,String destination,String dt1,String dt2,String bustype,int no_of_seates)
    {

        this.source=source;
        this.destination=destination;
        this.dt1=dt1;
        this.dt2=dt2;
        this.bustype=bustype;
        this.no_of_seates=no_of_seates;
    }
}
