package studio.ahope.project_ahope1.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Last update : 2016-11-03
 */
/* while working */

public class BroadcastManager {
    private BroadcastReceiver broadcastReceiver;
    private final IntentFilter intentFilter = new IntentFilter();
    private final String broadcastMessage;
    private final Context parentsContext;
    private final Intent broadcastIntent;

    public BroadcastManager(Context context, String message) {
        broadcastIntent = new Intent(message);
        broadcastMessage = message;
        parentsContext = context;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(broadcastMessage)) {
                    // input your code
                }
            }
        };
    }

    public BroadcastReceiver getReceiver() {
        return broadcastReceiver;
    }

    public String getMessage() {
        return broadcastMessage;
    }

    public BroadcastManager setReceiver(BroadcastReceiver receiver) {
        broadcastReceiver = receiver;
        return this;
    }

    public BroadcastManager setAction(String action) {
        intentFilter.addAction(action);
        return this;
    }

    public BroadcastManager enableReceiver() {
        parentsContext.registerReceiver(broadcastReceiver, intentFilter);
        return this;
    }

    public BroadcastManager disableReceiver() {
        parentsContext.unregisterReceiver(broadcastReceiver);
        return this;
    }

    public void callBroadcast() {
       parentsContext.sendBroadcast(broadcastIntent);
    }
}
