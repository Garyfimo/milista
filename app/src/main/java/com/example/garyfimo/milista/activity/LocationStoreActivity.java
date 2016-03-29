package com.example.garyfimo.milista.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.interfaces.ILocationStoreView;
import com.example.garyfimo.milista.model.Step;
import com.example.garyfimo.milista.presenter.LocationStorePresenter;
import com.example.garyfimo.milista.util.listener.GpsLocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationStoreActivity extends FragmentActivity implements ILocationStoreView {

    private GoogleMap mMap;

    @Bind(R.id.map)
    MapView mapView;

    private View mLayout;

    private GpsLocationListener gps;

    private static final int REQUEST_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_store);
        ButterKnife.bind(LocationStoreActivity.this);
        mapView.onCreate(savedInstanceState);
        gps = new GpsLocationListener(LocationStoreActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            mMap = mapView.getMap();
            mMap.addMarker(makeMarkerStore());
            LatLng plazaVeaBolichera = new LatLng(-12.148570, -76.986998);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(plazaVeaBolichera, 2));
            CameraPosition cameraStorePosition = new CameraPosition.Builder().target(new LatLng(plazaVeaBolichera.latitude, plazaVeaBolichera.longitude)).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraStorePosition));
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);
            if (gps.canGetLocation()) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(getMyPositionLatLng()).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                Toast.makeText(LocationStoreActivity.this, R.string.error_getting_position, Toast.LENGTH_LONG).show();
            }
            LocationStorePresenter locationStorePresenter = new LocationStorePresenter(this);
            locationStorePresenter.getRoutes(getMyPositionLatLng(), plazaVeaBolichera, "WALKING");
        }
    }

    public LatLng getMyPositionLatLng() {
        return new LatLng(gps.getLatitude(), gps.getLongitude());
    }

    public MarkerOptions makeMarkerStore() {
        LatLng plazaVeaBolichera = new LatLng(-12.1485527, -76.9863245);
        return new MarkerOptions().position(plazaVeaBolichera).title("Plaza Vea Bolichera");//.flat(true);//.rotation(245);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_FINE_LOCATION) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Snackbar.make(mLayout, R.string.permision_available_fine_location,
//                        Snackbar.LENGTH_SHORT).show();
            } else {
//                Snackbar.make(mLayout, R.string.permissions_not_granted,
//                        Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            ActivityCompat.requestPermissions(LocationStoreActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void OnDirectionsObtained(ArrayList<LatLng> geoLocations) {
        PolylineOptions polylineOptions = new PolylineOptions().width(6).color(Color.RED);
        for (LatLng latLng : geoLocations) {
            polylineOptions.geodesic(true).add(latLng);
        }
        mMap.addPolyline(polylineOptions);
    }
}
