package studio.ahope.project_ahope1.Service;

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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import studio.ahope.project_ahope1.Event.ServiceEvent;
import studio.ahope.project_ahope1.Manager.WeatherManager;
import studio.ahope.project_ahope1.Task.WeatherTask;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class MainService extends Service {
    private LocationManager locationManager;
    private SharedPreferences locationPref;
    private SharedPreferences.Editor lPedit;
    private int requestUpdateTime = 1000 * 30;
    private int requestUpdateDistance = 10;
    private Double lastLat;
    private Double lastLon;
    private WeatherManager weatherManager;
    private ServiceEvent serviceEvent;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lPedit.putFloat("Lat", Float.parseFloat(String.valueOf(location.getLatitude())));
            lPedit.putFloat("Lon", Float.parseFloat(String.valueOf(location.getLongitude())));
            lPedit.putString("City", getCountry());
            lPedit.apply();
            lastLat = location.getLatitude();
            lastLon = location.getLongitude();

            checkAvailable();
            weatherExtract();


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

    public String checkProvider() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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

    public Double getLat() {
        return lastLat;
    }

    public Double getLon() {
        return lastLon;
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
            return addresses.getAdminArea() + " " + addresses.getLocality() + " " + addresses.getSubLocality();
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceEvent = new ServiceEvent(this);
        serviceEvent.set("ALL_PASSED_PERMISSION").start("ALL_PASSED_PERMISSION");

        Intent permissionEvent = new Intent("studio.ahope.project_ahope1.NEED_PERMISSION");
        sendBroadcast(permissionEvent);

    }

    public void onService() {
        locationPref = getApplicationContext().getSharedPreferences("location", MODE_MULTI_PROCESS);
        lPedit = locationPref.edit();
        lastLat = (double) locationPref.getFloat("Lat", 0);
        lastLon = (double) locationPref.getFloat("Lon", 0);
        requestUpdateTime = locationPref.getInt("Uptime",1000 * 30);
        requestUpdateDistance = locationPref.getInt("Updist",10);

        startRequest(this);

        weatherExtract();
        checkAvailable();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
        serviceEvent.stop("ALL_PASSED_PERMISSION");
        if(lPedit != null) {
            if (lastLat != null && lastLon != null) {
                lPedit.putFloat("Lat", Float.parseFloat(lastLat.toString()));
                lPedit.putFloat("Lon", Float.parseFloat(lastLon.toString()));
            }
            lPedit.putInt("Uptime", requestUpdateTime);
            lPedit.putInt("Updist", requestUpdateDistance);
            lPedit.putString("City", getCountry());
            lPedit.apply();
        }
    }

    private void weatherExtract() {
        WeatherTask wt = new WeatherTask();

        try {
            weatherManager = wt.execute(lastLat, lastLon).get();

            if(weatherManager != null) {
                lPedit.putFloat("temp", Float.valueOf(weatherManager.getTemperature().toString()));
                lPedit.putString("temp", weatherManager.getWeather());
                lPedit.putInt("cloudy", weatherManager.getCloudy());
                lPedit.putInt("snow", weatherManager.getCloudy());
                lPedit.putInt("rain", weatherManager.getRain());
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void checkAvailable() {
        Intent activityEventIntent = new Intent("studio.ahope.project_ahope1.CHANGE_WEATHER_PROFILE");
        activityEventIntent.putExtra("Location", getCountry());
        activityEventIntent.putExtra("Temp", weatherManager.getTemperature());
        sendBroadcast(activityEventIntent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
