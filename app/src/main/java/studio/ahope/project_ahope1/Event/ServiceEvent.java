package studio.ahope.project_ahope1.Event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import studio.ahope.project_ahope1.Manager.BroadcastManager;
import studio.ahope.project_ahope1.Service.MainService;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class ServiceEvent {
    private final Context parentsContext;
    private final String ALL_PASSED_PERMISSION_MESSAGE = "ALL_PASSED_PERMISSION";
    private final String ALL_PASSED_PERMISSION = "studio.ahope.project_ahope1." + ALL_PASSED_PERMISSION_MESSAGE;
    private BroadcastManager broadcastManager_ALL_PASSED_PERMISSION;
    public ServiceEvent(Context context) {
        parentsContext = context;
    }

    public ServiceEvent start(String message) {
        switch (message) {
            case ALL_PASSED_PERMISSION_MESSAGE :
                if(broadcastManager_ALL_PASSED_PERMISSION == null){
                    set(message);
                }
                Intent serviceEvent = new Intent("studio.ahope.project_ahope1.ALL_PASSED_PERMISSION");
                parentsContext.sendBroadcast(serviceEvent);
                break;
        }
        return this;
    }

    public ServiceEvent stop(String message) {
        switch (message) {
            case ALL_PASSED_PERMISSION_MESSAGE :
                broadcastManager_ALL_PASSED_PERMISSION.disableReceiver();
                break;
        }
        return this;
    }

    public ServiceEvent set(String message) {
        switch (message) {
            case ALL_PASSED_PERMISSION_MESSAGE :
                ALL_PASSED_PERMISSION(parentsContext);
                break;
        }
        return this;
    }

    public BroadcastManager get(String message) {
        switch (message) {
            case ALL_PASSED_PERMISSION_MESSAGE :
                return broadcastManager_ALL_PASSED_PERMISSION;
        }
        return null;
    }

    private void ALL_PASSED_PERMISSION(Context context) {
        broadcastManager_ALL_PASSED_PERMISSION = new BroadcastManager(context, ALL_PASSED_PERMISSION);
        broadcastManager_ALL_PASSED_PERMISSION.setAction(ALL_PASSED_PERMISSION);
        broadcastManager_ALL_PASSED_PERMISSION.setReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ALL_PASSED_PERMISSION)) {
                    ((MainService) context).onService();
                }
            }
        });
        broadcastManager_ALL_PASSED_PERMISSION.enableReceiver();
    }
}
