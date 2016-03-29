package com.example.garyfimo.milista.interfaces;

import android.content.Context;

import com.example.garyfimo.milista.model.Step;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 1/10/16.
 */
public interface ILocationStoreView {

    Context getContext();
    void OnDirectionsObtained(ArrayList<LatLng> geoLocations);

}
