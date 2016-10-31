package studio.ahope.project_ahope1.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Last update : 2016-11-01
 */
/* while working */

public class DialogManager {
    private final Activity dialogTargetActivity;
    private final Context dialogTargetContext;
    private final AlertDialog.Builder dialog;
    private AlertDialog dialogShow;
    private View dialogView;
    public DialogInterface.OnClickListener getDialogPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };
    public DialogInterface.OnClickListener getDialogNegativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    public DialogManager(Activity activity, int Layout) {
        dialogTargetActivity = activity;
        dialogTargetContext = (Context) dialogTargetActivity;
        dialog = new AlertDialog.Builder(dialogTargetActivity);
        if (Layout != 0) {
            LayoutInflater inflater = (LayoutInflater) dialogTargetContext.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            dialogView = inflater.inflate(Layout, null);
        }
    }

    public View getDialogView() {
        return dialogView;
    }

    public Boolean getDialogShowStatus() {
        if(dialogShow != null) {
            return dialogShow.isShowing();
        } else {
            return false;
        }
    }

    public DialogManager setDialogTitle(int dialogTitle) {
        if(dialog != null) {
            dialog.setTitle(dialogTitle);
        }
        return this;
    }

    public DialogManager setDialogPositiveButton(int dialogPositiveTitle) {
        if(dialog != null) {
            dialog.setNegativeButton(dialogPositiveTitle, getDialogPositiveListener);
        }
        return this;
    }

    public DialogManager setDialogNegativeButton(int dialogNegativeTitle) {
        if(dialog != null) {
            dialog.setPositiveButton(dialogNegativeTitle,getDialogNegativeListener);
        }
        return this;
    }

    public DialogManager setDialogMessage(int dialogMessage) {
        if(dialog != null && dialogView == null) {
            dialog.setMessage(dialogMessage);
        }
        return this;
    }

    public DialogManager dialogShow() {
        if(dialog != null) {
            if(!getDialogShowStatus()) {
                if (dialogView != null) {
                    dialog.setView(dialogView);
                }
                dialogShow = dialog.create();
                dialogShow.show();
            }
        }
        return this;
    }

    public DialogManager dialogHide() {
        if(dialogShow != null) {
            if(getDialogShowStatus()) {
                dialogShow.hide();
            }
        }
        return this;
    }
}
