package studio.ahope.project_ahope1.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Last update : 2016-10-30
 */
/* while working */

public class PermissionManager {
    private int requestPer = 1;
    public Boolean getStatus(Context context, String permission) {
        if(ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPer(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity,
            new String[]{permission},
            requestPer
        );
    }

    public void autoRequest(Activity activity, Context context, String[] perList) {
        for (String per : perList) {
            if(!getStatus(context, per)) {
                requestPer(activity, per);
            }
        }
    }
}
