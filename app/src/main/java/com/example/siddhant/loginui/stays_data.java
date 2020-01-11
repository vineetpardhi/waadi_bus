package com.example.siddhant.loginui;

import java.util.List;

public class stays_data {
    private String Ammenties;

    private String Comments;

    private String Coordinates;

    private String Description;

    private String Location;

    private String Name;

    private String Near;

    private String Price_per_night;

    private String Ratings;


    private List<String> img_url;
    private String number_of_rooms;



    public stays_data(String ammenties, String comments, String coordinates, String description, String location, String name, String near, String price_per_night, String ratings,List<String> img_url, String number_of_rooms) {
        Ammenties = ammenties;
        Comments = comments;
        Coordinates = coordinates;
        Description = description;
        Location = location;
        Name = name;
        Near = near;
        Price_per_night = price_per_night;
        Ratings = ratings;
        this.img_url=img_url;

        this.number_of_rooms = number_of_rooms;
    }

    public String getAmmenties() {
        return Ammenties;
    }

    public void setAmmenties(String ammenties) {
        Ammenties = ammenties;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNear() {
        return Near;
    }

    public void setNear(String near) {
        Near = near;
    }

    public String getPrice_per_night() {
        return Price_per_night;
    }

    public void setPrice_per_night(String price_per_night) {
        Price_per_night = price_per_night;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }




    public String getNumber_of_rooms() {
        return number_of_rooms;
    }

    public void setNumber_of_rooms(String number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }
}
