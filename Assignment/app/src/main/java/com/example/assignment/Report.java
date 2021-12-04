package com.example.assignment;

import java.util.Date;

public class Report {
    private String r_name;
    private String r_description;
    private int pathImage;

    public Report(String rname, int pathImage, String rdes) {
        this.r_name = rname;
        this.pathImage = pathImage;
        this.r_description = rdes;

    }

    public String getR_name() {
        return r_name;
    }

    public String getR_description() {
        return r_description;
    }

    public int getPathImage() {
        return pathImage;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public void setR_description(String r_description) {
        this.r_description = r_description;
    }

    public void setPathImage(int pathImage) {
        this.pathImage = pathImage;
    }


}
