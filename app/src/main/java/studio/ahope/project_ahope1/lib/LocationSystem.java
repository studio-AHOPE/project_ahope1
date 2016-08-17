package studio.ahope.project_ahope1.lib;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

/**
 * Created by YuahP on 2016-08-16.
 * Last update : 2016-08-17
 */
/* while working */
public class LocationSystem {
    static LocationManager locationManager;
    static int requestUpdateTime = 1000;
    static int requestUpdateDistance = 10;
    static Double lastLat = 0.0;
    static Double lastLon = 0.0;
    static Context contexts;

    static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLat = location.getLatitude();
            lastLon = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    public static String checkProvider(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else {
            return null;
        }
    }

    public static void startRequest(Context context) {
        contexts = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                requestUpdateTime,
                requestUpdateDistance, locationListener);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                requestUpdateTime,
                requestUpdateDistance, locationListener);
        }
    }

    public static Double getLat(Context context) {
        return lastLat;
    }

    public static Double getLon(Context context) {
        return lastLon;
    }

    public static String getCountry(Context context) {
        List<Address> address = null;
        Geocoder geoCoder = new Geocoder(context);
        try {
            address = geoCoder.getFromLocation(getLat(context), getLon(context), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(address != null && address.size() > 0){
            Address addresses = address.get(0);
            return addresses.getAdminArea() +" "+ addresses.getLocality();
        }
        return null;
    }
}
