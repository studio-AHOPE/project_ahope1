package studio.ahope.project_ahope1.Task;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import studio.ahope.project_ahope1.Manager.WeatherManager;

/**
 * Last update : 2016-10-30
 */
/* while working */

public class WeatherTask extends AsyncTask<Double, Void, WeatherManager> {

    @Override
    public final WeatherManager doInBackground(Double... params) {

        WeatherManager client = new WeatherManager();

        Double lat = params[0];
        Double lon = params[1];

        try {
            return client.getWeather(lat, lon);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
