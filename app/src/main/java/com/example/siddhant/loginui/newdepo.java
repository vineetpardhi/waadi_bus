package com.example.siddhant.loginui;

public class newdepo {
    private String deponame;
    private String deponumber;

    public newdepo(String deponame, String deponumber) {
        this.deponame = deponame;
        this.deponumber = deponumber;
    }

    public String getDepoName() {
        return deponame;
    }

    public void setDepoName(String deponame) {
        this.deponame = deponame;
    }

    public String getDepoNumber() { return deponumber;}

    public void setDepoNumber(String deponumber) {
        this.deponumber =deponumber ;
    }
}
