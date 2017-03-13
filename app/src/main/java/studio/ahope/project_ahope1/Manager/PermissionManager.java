package studio.ahope.project_ahope1.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import studio.ahope.project_ahope1.R;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class PermissionManager {
    private final int requestPermissionCallback = 1;
    private final Activity targetActivity;
    private final Context targetContext;
    private DialogManager requestOverlayDialog;
    private DialogManager requestPermissionDialog;
    public PermissionManager(Activity activity) {
        targetActivity = activity;
        targetContext = (Context) targetActivity;
        requestPermissionDialog = new DialogManager(targetActivity);
        requestOverlayDialog = new DialogManager(targetActivity);
    }

    private void requestPermission(String[] permission) {
        ActivityCompat.requestPermissions(targetActivity, permission, requestPermissionCallback);
    }

    private void requestOverlayPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + targetContext.getPackageName()));
            targetContext.startActivity(intent);
        }
    }

    public final Boolean getStatus(String permission) {
        return ActivityCompat.checkSelfPermission(targetContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public final Boolean getAllStatus(String[] permission) {
        boolean Passed = true;
        for(String per : permission){
            if(!getStatus(per)){
                Passed = false;
            }
        }
        return Passed;
    }

    public final void checkPermission(String[] permission, int alertMessage) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final List<String> list = new ArrayList<>();
            for (String per : permission) {
                if (!getStatus(per)) {
                    list.add(per);
                }
            }
            if (list.size() > 0) {
                requestPermissionDialog.setAlertDialog().setAlertDialogTitle(R.string.alert)
                        .setAlertDialogMessage(alertMessage)
                        .setAlertDialogPositiveListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermission(list.toArray(new String[list.size()]));
                            }
                        });
                requestPermissionDialog.setAlertDialogPositiveButton(R.string.settingPermissionButton)
                        .alertDialogShow();

            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!Settings.canDrawOverlays(targetContext)) {
                    requestOverlayDialog.setAlertDialog().setAlertDialogTitle(R.string.alert)
                            .setAlertDialogMessage(R.string.requestOverlaySummary)
                            .setAlertDialogNegativeListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestOverlayPermission();
                                }
                            });
                    requestOverlayDialog.setAlertDialogPositiveButton(R.string.settingOverlayButton)
                            .alertDialogShow();
                }
            }
        }
    }
}
