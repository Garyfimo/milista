package com.example.garyfimo.milista.presenter;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.interfaces.ILocationStoreView;
import com.example.garyfimo.milista.model.Step;
import com.example.garyfimo.milista.util.Constant;
import com.example.garyfimo.milista.util.Util;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 1/10/16.
 */
public class LocationStorePresenter {

    ILocationStoreView iLocationStoreView;
    private RequestQueue queue;

    public LocationStorePresenter(ILocationStoreView iLocationStoreView) {
        this.iLocationStoreView = iLocationStoreView;
        queue = Volley.newRequestQueue(iLocationStoreView.getContext());
    }

    public void getRoutes(LatLng origin, LatLng destination, String mode) {
//        String key = iLocationStoreView.getContext().getResources().getString(R.string.google_maps_key);
        String key = "";

        String url = String.format(Constant.URL_MAPS + "origin=%s,%s&destination=%s,%s&avoid=highways&mode=%s&key=%s",
                origin.latitude, origin.longitude, destination.latitude, destination.longitude, mode, key);

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("OK")) {
//                        stepArrayList = new ArrayList<>();
                        ArrayList<LatLng> arrayLatLng = new ArrayList<>();
                        JSONArray routes = response.getJSONArray("routes");
                        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
                        JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
                        for (int iStep = 0; iStep < steps.length(); iStep++) {
                            JSONObject stepObject = steps.getJSONObject(iStep);
                            arrayLatLng.add(new LatLng(stepObject.getJSONObject("start_location").getDouble("lat"),
                                    stepObject.getJSONObject("start_location").getDouble("lng")));
                            ArrayList<LatLng> array = Util.decodePoly(stepObject.getJSONObject("polyline").getString("points"));
                            for (LatLng latLng : array) {
                                arrayLatLng.add(new LatLng(latLng.latitude, latLng.longitude));
                            }
                            arrayLatLng.add(new LatLng(stepObject.getJSONObject("end_location").getDouble("lat"),
                                    stepObject.getJSONObject("end_location").getDouble("lng")));
                        }
                        iLocationStoreView.OnDirectionsObtained(arrayLatLng);
                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }

}
