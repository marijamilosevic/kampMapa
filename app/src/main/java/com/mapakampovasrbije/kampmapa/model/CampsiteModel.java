package com.mapakampovasrbije.kampmapa.model;

import android.database.Cursor;

import java.io.Serializable;

public class CampsiteModel implements Serializable {

    /**
     * Unique id.
     */
    private int id;

    /**
     * Campsite name.
     */
    private String name;

    /**
     * Campsite description.
     */
    private String description;

    /**
     * Campsite website url.
     */
    private String website;

    /**
     * Campsite address latitude coordinates.
     */
    private double latitude;

    /**
     * Campsite address longitude coordinates.
     */
    private double longitude;

    /**
     * Campsite address.
     */
    private String address;

    /**
     * Campsite phone number.
     */
    private String phoneNumber;

    public CampsiteModel() {
    }

    public static CampsiteModel fromCursor(Cursor cursor) {
        CampsiteModel model = new CampsiteModel();
        model.setId(cursor.getInt(0));
        model.setName(cursor.getString(1));
        model.setDescription(cursor.getString(2));
        model.setWebsite(cursor.getString(3));
        model.setLatitude(cursor.getDouble(4));
        model.setLongitude(cursor.getDouble(5));
        model.setAddress(cursor.getString(6));
        model.setPhoneNumber(cursor.getString(7));
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
