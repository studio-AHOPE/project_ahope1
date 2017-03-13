package studio.ahope.project_ahope1.Event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import studio.ahope.project_ahope1.MainActivity;
import studio.ahope.project_ahope1.Manager.BroadcastManager;
import studio.ahope.project_ahope1.R;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class ActivityEvent {
    private final Context parentsContext;
    private final String CHANGE_WEATHER_PROFILE_MESSAGE = "CHANGE_WEATHER_PROFILE";
    private final String CHANGE_WEATHER_PROFILE = "studio.ahope.project_ahope1." + CHANGE_WEATHER_PROFILE_MESSAGE;
    private BroadcastManager broadcastManager_CHANGE_WEATHER_PROFILE;
    public ActivityEvent(Context context) {
        parentsContext = context;
    }

    public ActivityEvent start(String message) {
        switch (message) {
            case CHANGE_WEATHER_PROFILE_MESSAGE :
                if(broadcastManager_CHANGE_WEATHER_PROFILE == null){
                    set(message);
                }
                Intent permissionEvent = new Intent("studio.ahope.project_ahope1.CHANGE_WEATHER_PROFILE");
                parentsContext.sendBroadcast(permissionEvent);
                break;
        }
        return this;
    }

    public ActivityEvent stop(String message) {
        switch (message) {
            case CHANGE_WEATHER_PROFILE_MESSAGE :
                broadcastManager_CHANGE_WEATHER_PROFILE.disableReceiver();
                break;
        }
        return this;
    }

    public ActivityEvent set(String message) {
        switch (message) {
            case CHANGE_WEATHER_PROFILE_MESSAGE :
                CHANGE_WEATHER_PROFILE(parentsContext);
                break;
        }
        return this;
    }

    public BroadcastManager get(String message) {
        switch (message) {
            case CHANGE_WEATHER_PROFILE_MESSAGE :
                return broadcastManager_CHANGE_WEATHER_PROFILE;
        }
        return null;
    }

    public ActivityEvent setExtra(String message, Integer integer) {
        switch (message) {
            case CHANGE_WEATHER_PROFILE_MESSAGE :
                Intent permissionEvent = new Intent("studio.ahope.project_ahope1.CHANGE_WEATHER_PROFILE");
                parentsContext.sendBroadcast(permissionEvent);
                break;
        }
        return this;
    }

    private void CHANGE_WEATHER_PROFILE(Context context) {
        broadcastManager_CHANGE_WEATHER_PROFILE = new BroadcastManager(context, CHANGE_WEATHER_PROFILE);
        broadcastManager_CHANGE_WEATHER_PROFILE.setAction(CHANGE_WEATHER_PROFILE);
        broadcastManager_CHANGE_WEATHER_PROFILE.setReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(CHANGE_WEATHER_PROFILE)) {
                    String location = intent.getExtras().getString("Location");
                    Double temp = intent.getExtras().getDouble("Temp");
                    if(intent.getExtras().getString("Location") != null ) {
                        ((MainActivity) context).getBinding().winfo1.setText(Integer.toString((int)Math.round(temp)) + "℃");
                        ((MainActivity) context).getBinding().winfo2.setText(location);
                    } else {
                        ((MainActivity) context).getBinding().winfo2.setText(R.string.failed);
                    }
                }
            }
        });
        broadcastManager_CHANGE_WEATHER_PROFILE.enableReceiver();
    }
}
