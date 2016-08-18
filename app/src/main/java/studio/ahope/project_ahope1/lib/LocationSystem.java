package studio.ahope.project_ahope1.lib;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import studio.ahope.project_ahope1.MainActivity;
import studio.ahope.project_ahope1.R;

/**
 * Created by YuahP on 2016-08-16.
 * Last update : 2016-08-18
 */
/* while working */
public class LocationSystem extends Service {
    private LocationManager locationManager;
    private SharedPreferences locationPref;
    private SharedPreferences.Editor lPedit;
    private int requestUpdateTime;
    private int requestUpdateDistance;
    private Double lastLat;
    private Double lastLon;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lPedit.putFloat("Lat", Float.parseFloat(String.valueOf(location.getLatitude())));
            lPedit.putFloat("Lon", Float.parseFloat(String.valueOf(location.getLongitude())));
            lPedit.apply();
            lastLat = location.getLatitude();
            lastLon = location.getLongitude();

            //Log.i("LocationSystem", "Reloaded Location. Lat=" + String.valueOf(location.getLatitude()) + " Lon=" + String.valueOf(location.getLongitude()) );
            MainActivity.binding.winfo2.setText(getCountry());
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

    public String checkProvider(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else {
            return null;
        }
    }

    public void startRequest(Context context) {
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

    public String getCountry() {
        List<Address> address = null;
        Geocoder geoCoder = new Geocoder(this);
        try {
            address = geoCoder.getFromLocation(lastLat, lastLon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address != null && address.size() > 0) {
            Address addresses = address.get(0);
            return addresses.getAdminArea() + " " + addresses.getLocality();
        } else {
            return String.valueOf(R.string.loading);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationPref = getApplicationContext().getSharedPreferences("location", MODE_MULTI_PROCESS);
        lPedit = locationPref.edit();
        lastLat = (double) locationPref.getFloat("Lat", 0);
        lastLon = (double) locationPref.getFloat("Lon", 0);
        requestUpdateTime = locationPref.getInt("Uptime",1000 * 30);
        requestUpdateDistance = locationPref.getInt("Updist",10);

        startRequest(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override

    public void onDestroy() {
        super.onDestroy();

        lPedit.putFloat("Lat", Float.parseFloat(lastLat.toString()));
        lPedit.putFloat("Lon", Float.parseFloat(lastLon.toString()));
        lPedit.putInt("Uptime", requestUpdateTime);
        lPedit.putInt("Updist", requestUpdateDistance);
        lPedit.apply();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
