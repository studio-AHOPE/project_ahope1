package studio.ahope.project_ahope1.lib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by YuahP on 2016-08-16.
 * Last update : 2016-08-18
 */

public class PermissionManager {
    public static int requestPer = 1;
    public static Boolean iswait = false;
    public static Thread threadwait = new Thread(){
        public void run() {
            if(iswait){

            }
        }
    };
    public static Boolean getStatus(Context context, String permission) {
        if(ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPer(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity,
            new String[]{permission},
            requestPer
        );
    }

    public static void autoRequest(Activity activity, Context context, String[] perList) {
        for (String per : perList) {
            if(!getStatus(context, per)) {
                requestPer(activity, per);
                iswait = true;
                threadwait.start();
            }
        }
    }
}
