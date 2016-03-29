package com.example.garyfimo.milista.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 1/10/16.
 */
public class Step {

    private LatLng startLocation;
    private LatLng endLocation;
    private String distance;
    private String duration;
    private ArrayList<LatLng> geoLocation;

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<LatLng> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(ArrayList<LatLng> geoLocation) {
        this.geoLocation = geoLocation;
    }
}
