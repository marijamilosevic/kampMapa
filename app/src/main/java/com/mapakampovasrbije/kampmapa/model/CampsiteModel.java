package com.mapakampovasrbije.kampmapa.model;

public class CampsiteModel {

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
    private String websiteUrl;

    /**
     * Campsite address.
     */
    private Address address;

    public CampsiteModel(int id, String name, String description, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
