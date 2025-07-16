package com.gitobu.nissan;

public class Location {
    private int locationID;
    private String locationName;
    private String streetaddress;
    private String city;
    private String state;
    private String zipcCode;

    public Location() {
        locationID = -1;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcCode() {
        return zipcCode;
    }

    public void setZipcCode(String zipcCode) {
        this.zipcCode = zipcCode;
    }
}
