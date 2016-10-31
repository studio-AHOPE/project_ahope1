package studio.ahope.project_ahope1.Manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Last update : 2016-11-01
 */
/* while working */

public class WeatherManager {

    private final String openWeatherURL = "http://api.openweathermap.org/data/2.5/weather";

    private Double lat;
    private Double lon;
    private Double temperature;
    private String weather;
    private int cloudy;
    private int snow;
    private int rain;
    private String city;

    private void setLat(Double lat){ this.lat = lat;}
    private void setLon(Double lon){ this.lon = lon;}
    private void setTemperature(Double t){ this.temperature = t;}
    private void setWeather(String weather){ this.weather = weather;}
    private void setCloudy(int cloudy){ this.cloudy = cloudy;}
    private void setSnow(int snow){ this.snow = snow;}
    private void setRain(int rain){ this.rain = rain;}
    private void setCity(String city){ this.city = city;}

    public final Double getLat(){ return lat;}
    public final Double getIon() { return lon;}
    public final String getWeather(){ return weather;}
    public final Double getTemperature() { return temperature;}
    public final int getCloudy() { return cloudy; }
    public final int getSnow() { return snow; }
    public final int getRain() { return rain; }
    public final String getCity() { return city; }

    public final WeatherManager getWeather(Double lat,Double lon) throws JSONException, IOException{
        String urlString = openWeatherURL + "?lat="+lat+"&lon="+lon+"&APPID=61ddd0ba40ce320b65120bf9f7583dc4";

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        if(in == null){
            return null;
        }
        JSONObject json = new JSONObject(getStringFromInputStream(in));

        parseJSON(json);
        setLon(lon);
        setLat(lat);

        return this;
    }


    private void parseJSON(JSONObject json) throws JSONException {

        setTemperature(json.getJSONObject("main").getDouble("temp") - 273.15);
        setWeather(json.getJSONArray("weather").getJSONObject(0).getString("main"));
        setCity(json.getString("name"));
        setCloudy(json.getJSONObject("clouds").getInt("all"));
        if (json.has("snow")) {
            setSnow(json.getJSONArray("snow").getJSONObject(0).getInt("3h"));
        }
        if (json.has("rain")) {
            setRain(json.getJSONArray("rain").getJSONObject(0).getInt("3h"));
        }
    }


    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}

