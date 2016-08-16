package studio.ahope.project_ahope1.lib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by YuahP on 2016-08-16.
 */

public class PermissionManager {
    public static int requestPer = 1;
    public static Boolean getStatus(Context context, String permission) {
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            return true;
        }
    }

    public static void requestPer(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity,
            new String[]{permission},
            requestPer
        );
    }

    public static void noticeRequestPer(Activity activity, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            requestPer(activity, permission);
        } else {
            requestPer(activity, permission);
        }
    }

    public static void autoRequest(Activity activity, Context context, String[] perList) {
        for (String per : perList) {
            if(!getStatus(context, per)) {
                noticeRequestPer(activity, per);
            }
        }
    }
}
