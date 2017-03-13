package studio.ahope.project_ahope1.Manager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Last update : 2016-11-04
 */
/* while working */

public class DialogManager {
    private final Activity dialogTargetActivity;
    private final Context dialogTargetContext;
    private String progressType;
    private ProgressDialog progressDialog;
    private View progressDialogView;
    private AlertDialog.Builder alertDialog;
    private AlertDialog alertDialogShow;
    private View alertDialogView;
    private DatePickerDialog dateDialog;
    private View dateDialogView;
    private TimePickerDialog timeDialog;
    private View timeDialogView;
    private final Calendar calendar = Calendar.getInstance();
    private final int calendarDateYear = calendar.get(Calendar.YEAR);
    private final int calendarDateMonth = calendar.get(Calendar.MONTH);
    private final int calendarDateDay = calendar.get(Calendar.DAY_OF_WEEK);
    private final int calendarDateHour = calendar.get(Calendar.HOUR);
    private final int calendarDateMin = calendar.get(Calendar.MINUTE);
    private DialogInterface.OnClickListener alertDialogPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };
    private DialogInterface.OnClickListener alertDialogNegativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };
    private DialogInterface.OnClickListener radioDialogListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
        }
    };
    private DialogInterface.OnMultiChoiceClickListener selectDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        }
    };
    private DatePickerDialog.OnDateSetListener dateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    };
    private TimePickerDialog.OnTimeSetListener timeDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };

    public DialogManager(Activity activity) {
        dialogTargetActivity = activity;
        dialogTargetContext = (Context) dialogTargetActivity;
    }

    /*
    * Progress Dialog
    * */

    public DialogManager setProgressDialog() {
        progressDialog = new ProgressDialog(dialogTargetContext);
        return this;
    }

    public View getProgressDialogView() {
        return progressDialogView;
    }

    public Boolean getProgressDialogShowStatus() {
        return progressDialog.isShowing();
    }

    public DialogManager setProgressDialogType(String type) {
        if(progressDialog != null) {
            switch (type) {
                case "SPINNER" :
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    break;
                case "HORIZONTAL" :
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    break;
                default:
                    break;
            }
        }
        progressType = type;
        return this;
    }

    public void setProgressDialogView(View view) {
        progressDialogView = view;
    }

    public DialogManager setProgressLayoutView(int Layout) {
        if (Layout != 0) {
            LayoutInflater inflater = (LayoutInflater) dialogTargetContext.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressDialogView = inflater.inflate(Layout, null);
        }
        return this;
    }

    public DialogManager setProgressDialogTitle(int dialogTitle) {
        if(progressDialog != null) {
            progressDialog.setTitle(dialogTitle);
        }
        return this;
    }

    public DialogManager setProgressDialogMessage(int dialogMessage) {
        if(progressDialog != null) {
            progressDialog.setMessage(dialogTargetContext.getText(dialogMessage));
        }
        return this;
    }

    public DialogManager setProgressDialogCancelable(Boolean cancelable) {
        if(progressDialog != null) {
            progressDialog.setCancelable(cancelable);
        }
        return this;
    }

    public DialogManager setProgressGauge (int progressGauge){
        if(progressDialog != null && progressType == "HORIZONTAL"){
            progressDialog.setProgress(progressGauge);
        }
        return this;
    }

    public DialogManager progressDialogShow(){
        if(progressDialog != null) {
            if(getProgressDialogShowStatus()) {
                progressDialog.show();
            }
        }
        return this;
    }

    public DialogManager progressDialogHide(){
        if(progressDialog != null) {
            if(getProgressDialogShowStatus()) {
                progressDialog.dismiss();
            }
        }
        return this;
    }


    /*
    * Alert Dialog / Radio Dialog (Single Choice Dialog) / Select Dialog (Multi Choice Dialog)
    * */

    public DialogManager setAlertDialog(){
        alertDialog = new AlertDialog.Builder(dialogTargetActivity);
        return this;
    }

    public View getAlertDialogView() {
        return alertDialogView;
    }

    public DialogInterface.OnClickListener getAlertDialogPositiveListener() {
        return alertDialogPositiveListener;
    }

    public DialogInterface.OnClickListener getAlertDialogNegativeListener() {
        return alertDialogNegativeListener;
    }

    public Boolean getAlertDialogShowStatus() {
        if(alertDialogShow != null) {
            return alertDialogShow.isShowing();
        }
        return false;
    }

    public void setAlertDialogPositiveListener(DialogInterface.OnClickListener listener) {
        alertDialogPositiveListener = listener;
    }

    public void setAlertDialogNegativeListener(DialogInterface.OnClickListener listener) {
        alertDialogNegativeListener = listener;
    }

    public void setAlertDialogView(View view) {
        alertDialogView = view;
    }

    public DialogManager setAlertLayoutView(int Layout) {
        if (Layout != 0) {
            LayoutInflater inflater = (LayoutInflater) dialogTargetContext.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            alertDialogView = inflater.inflate(Layout, null);
        }
        return this;
    }

    public DialogManager setAlertDialogTitle(int dialogTitle) {
        if(alertDialog != null) {
            alertDialog.setTitle(dialogTitle);
        }
        return this;
    }

    public DialogManager setAlertDialogPositiveButton(int dialogPositiveTitle) {
        if(alertDialog != null) {
            alertDialog.setNegativeButton(dialogPositiveTitle, alertDialogPositiveListener);
        }
        return this;
    }

    public DialogManager setAlertDialogNegativeButton(int dialogNegativeTitle) {
        if(alertDialog != null) {
            alertDialog.setPositiveButton(dialogNegativeTitle,alertDialogNegativeListener);
        }
        return this;
    }

    public DialogManager setAlertDialogMessage(int dialogMessage) {
        if(alertDialog != null && alertDialogView == null) {
            alertDialog.setMessage(dialogMessage);
        }
        return this;
    }

    public DialogManager setAlertDialogCancelable(Boolean cancelable) {
        if(alertDialog != null) {
            alertDialog.setCancelable(cancelable);
        }
        return this;
    }

    public DialogManager alertDialogShow() {
        if(alertDialog != null) {
            if(!getAlertDialogShowStatus()) {
                alertDialogShow = alertDialog.create();
                alertDialogShow.show();
            }
        }
        return this;
    }

    public DialogManager alertDialogHide() {
        if(alertDialogShow != null) {
            if(getAlertDialogShowStatus()) {
                alertDialogShow.dismiss();
            }
        }
        return this;
    }

    /*
    * Radio Dialog (Single Choice Dialog)
    * */

    public DialogManager setRadioItem(String[] strings, int alreadySelected) {
        if(alertDialog != null) {
            alertDialog.setSingleChoiceItems(strings, alreadySelected, radioDialogListener);
        }
        return this;
    }

    /*
    * Select Dialog (Multi Choice Dialog)
    * */

    public DialogManager setSelectItem(String[] strings, boolean[] alreadySelected) {
        if(alertDialog != null) {
            alertDialog.setMultiChoiceItems(strings, alreadySelected, selectDialogListener);
        }
        return this;
    }

    /*
    * Date Dialog
    * */

    public DialogManager setDateDialog() {
        dateDialog = new DatePickerDialog(dialogTargetContext, dateDialogListener, calendarDateYear, calendarDateMonth, calendarDateDay);
        return this;
    }

    public View getDateDialogView() {
        return dateDialogView;
    }

    public Boolean getDateDialogShowStatus() {
        return dateDialog.isShowing();
    }

    public void setDateDialogView(View view) {
        dateDialogView = view;
    }

    public DialogManager setDateLayoutView(int Layout) {
        if (Layout != 0) {
            LayoutInflater inflater = (LayoutInflater) dialogTargetContext.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            dateDialogView = inflater.inflate(Layout, null);
        }
        return this;
    }

    public DialogManager setDateDialogTitle(int dialogTitle) {
        if(dateDialog != null) {
            dateDialog.setTitle(dialogTitle);
        }
        return this;
    }
    public DialogManager setDateDialogMessage(int dialogMessage) {
        if(dateDialog != null && dateDialogView == null) {
            dateDialog.setMessage(dialogTargetContext.getText(dialogMessage));
        }
        return this;
    }

    public DialogManager setDateDialogCancelable(Boolean cancelable) {
        if(dateDialog != null) {
            dateDialog.setCancelable(cancelable);
        }
        return this;
    }

    public DialogManager dateDialogShow() {
        if(dateDialog != null) {
            if(!getDateDialogShowStatus()) {
                dateDialog.show();
            }
        }
        return this;
    }

    public DialogManager dateDialogHide() {
        if(dateDialog != null) {
            if(getDateDialogShowStatus()) {
                dateDialog.dismiss();
            }
        }
        return this;
    }

    /*
    * Time Dialog
    * */

    public DialogManager setTimeDialog(Boolean enable24h) {
        timeDialog = new TimePickerDialog(dialogTargetContext, timeDialogListener, calendarDateHour, calendarDateMin, enable24h);
        return this;
    }

    public View getTimeDialogView() {
        return timeDialogView;
    }

    public Boolean getTimeDialogShowStatus() {
        return timeDialog.isShowing();
    }

    public void setTimeDialogView(View view) {
        timeDialogView = view;
    }

    public DialogManager setTimeLayoutView(int Layout) {
        if (Layout != 0) {
            LayoutInflater inflater = (LayoutInflater) dialogTargetContext.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            timeDialogView = inflater.inflate(Layout, null);
        }
        return this;
    }

    public DialogManager setTimeDialogTitle(int dialogTitle) {
        if(timeDialog != null) {
            timeDialog.setTitle(dialogTitle);
        }
        return this;
    }
    public DialogManager setTimeDialogMessage(int dialogMessage) {
        if(timeDialog != null && timeDialogView == null) {
            timeDialog.setMessage(dialogTargetContext.getText(dialogMessage));
        }
        return this;
    }

    public DialogManager setTimeDialogCancelable(Boolean cancelable) {
        if(timeDialog != null) {
            timeDialog.setCancelable(cancelable);
        }
        return this;
    }

    public DialogManager timeDialogShow() {
        if(timeDialog != null) {
            if(!getTimeDialogShowStatus()) {
                timeDialog.show();
            }
        }
        return this;
    }

    public DialogManager timeDialogHide() {
        if(timeDialog != null) {
            if(getTimeDialogShowStatus()) {
                timeDialog.dismiss();
            }
        }
        return this;
    }

}
