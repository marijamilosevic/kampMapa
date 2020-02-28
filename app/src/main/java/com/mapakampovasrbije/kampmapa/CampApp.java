package com.mapakampovasrbije.kampmapa;

import android.app.Application;
import android.location.Location;

public class CampApp extends Application {
    private static CampApp app;

    private Location currentLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static CampApp getInstance() {
        return app;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
