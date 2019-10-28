package com.mapakampovasrbije.kampmapa.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Camping location address.
 */
public class Address {

    /**
     * The address text.
     */
    private String address;

    /**
     * The map location coordinates.
     */
    private LatLng latLng;

    public Address(String address, LatLng latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "Adresa: " + address;
    }
}
