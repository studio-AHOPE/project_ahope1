package studio.ahope.project_ahope1.Event;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import studio.ahope.project_ahope1.MainActivity;
import studio.ahope.project_ahope1.Manager.BroadcastManager;
import studio.ahope.project_ahope1.Manager.PermissionManager;
import studio.ahope.project_ahope1.R;
import studio.ahope.project_ahope1.Service.MainService;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class PermissionEvent {
    private final Context parentsContext;
    private final String NEED_PERMISSION_MESSAGE = "NEED_PERMISSION";
    private final String NEED_PERMISSION = "studio.ahope.project_ahope1." + NEED_PERMISSION_MESSAGE;
    private BroadcastManager broadcastManager_NEED_PERMISSION;
    private final String[] requestPermission = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };
    public PermissionEvent(Context context) {
        parentsContext = context;
    }

    public PermissionEvent start(String message) {
        switch (message) {
            case NEED_PERMISSION_MESSAGE :
                if(broadcastManager_NEED_PERMISSION == null){
                    set(message);
                }
                Intent permissionEvent = new Intent("studio.ahope.project_ahope1.NEED_PERMISSION");
                parentsContext.sendBroadcast(permissionEvent);
                break;
        }
        return this;
    }

    public PermissionEvent stop(String message) {
        switch (message) {
            case NEED_PERMISSION_MESSAGE :
                broadcastManager_NEED_PERMISSION.disableReceiver();
                break;
        }
        return this;
    }

    public PermissionEvent set(String message) {
        switch (message) {
            case NEED_PERMISSION_MESSAGE :
                NEED_PERMISSION(parentsContext);
                break;
        }
        return this;
    }

    public BroadcastManager get(String message) {
        switch (message) {
            case NEED_PERMISSION_MESSAGE :
                return broadcastManager_NEED_PERMISSION;
        }
        return null;
    }

    private void NEED_PERMISSION(Context context) {
        broadcastManager_NEED_PERMISSION = new BroadcastManager(context, NEED_PERMISSION);
        broadcastManager_NEED_PERMISSION.setAction(NEED_PERMISSION);
        broadcastManager_NEED_PERMISSION.setReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NEED_PERMISSION)) {
                    PermissionManager permissionManager = new PermissionManager((MainActivity) context);
                    if(!permissionManager.getAllStatus(requestPermission)) {
                        permissionManager.checkPermission(requestPermission, R.string.requestPermission1Summary);
                        Intent serviceIntent = new Intent(context, MainService.class);
                        context.stopService(serviceIntent);
                    }
                }
            }
        });
        broadcastManager_NEED_PERMISSION.enableReceiver();
    }
}
