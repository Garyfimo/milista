package com.example.garyfimo.milista.activity;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.example.garyfimo.milista.util.listener.GpsLocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationStoreActivity extends FragmentActivity {

    private GoogleMap mMap;

    @Bind(R.id.map)
    MapView mapView;

    private View mLayout;

    private GpsLocationListener gps;

    private static final int REQUEST_FINE_LOCATION = 0;
    private static final int REQUEST_COARSE_LOCATION = 1;

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
            mMap.moveCamera(CameraUpdateFactory.newLatLng(plazaVeaBolichera));
//            CameraPosition cameraStorePosition = new CameraPosition.Builder().target(new LatLng(plazaVeaBolichera.latitude, plazaVeaBolichera.longitude)).zoom(15).build();
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraStorePosition));
            mMap.setMyLocationEnabled(true);
            Location myLocation = mMap.getMyLocation();
            if(gps.canGetLocation()){
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(gps.getLatitude(), gps.getLongitude())).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else{
                Toast.makeText(LocationStoreActivity.this,"No se puede obtener posicion...", Toast.LENGTH_LONG).show();
            }

        }
    }

    public MarkerOptions makeMarkerStore(){
        LatLng plazaVeaBolichera = new LatLng(-12.1485527, -76.9863245);
        return new MarkerOptions().position(plazaVeaBolichera).title("Plaza Vea Bolichera");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_FINE_LOCATION) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.permision_available_fine_location,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            ActivityCompat.requestPermissions(LocationStoreActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_LOCATION);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_LOCATION);
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
}
