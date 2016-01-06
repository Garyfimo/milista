package com.example.garyfimo.milista.util.listener;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class GpsLocationListener extends Service implements LocationListener {

    private final Context mContext;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    public boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 sec

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsLocationListener(Context context) {
        this.mContext = context;
        initialize();
    }

    public void initialize() {

        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        if (isNetworkEnabled() || isGPSEnabled()) {
            this.canGetLocation = true;
            getLocation();
        }
    }

    public Location getLocation() {
        try {

            if (isGPSEnabled())
                setUpLocationByGps();

            if (isNetworkEnabled())
                setUpLocationByNetwork();

        } catch (Exception e) {
            //Log.i("TAG", "getLocation :: Exception" + e.toString()); // e.printStackTrace
        }

        return location;
    }

    /**
     * If GPS enabled, get latitude/longitude using GPS Services
     */
    private void setUpLocationByGps() {
        try {
            //GPS_PROVIDER PASSIVE_PROVIDER
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Log.i("TAG", "gps enabled hora time" + location.getTime());
            //Log.i("TAG", "gps enabled hora time" + location.getTime());
            //Log.i("TAG", "gps enabled hora format" + Fecha.parsearLongDateTime(location.getTime()));
        } catch (Exception e) {
            //Log.i("TAG", "setUpLocationByGps:::Exception::" + e.toString());
        }
    }

    private void setUpLocationByNetwork() {
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Log.i("TAG", "setUpLocationByNetwork:::longitude::" + latitude);
            //Log.i("TAG", "setUpLocationByNetwork:::longitude::" + longitude);

        } catch (Exception e) {
            //Log.i("TAG", "setUpLocationByNetwork:::Exception::" + e.toString());
        }
    }

    private boolean isNetworkEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            return true;

        return false;
    }


    public boolean isGPSEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;

        return false;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     */
    public void stopUsingGPS() {
        if (locationManager != null)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }locationManager.removeUpdates(GpsLocationListener.this);
    }


    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null)
            latitude = location.getLatitude();

        return latitude;
    }


    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null)
            longitude = location.getLongitude();

        return longitude;
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */
    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}