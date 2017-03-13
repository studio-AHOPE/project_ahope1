package studio.ahope.project_ahope1;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import studio.ahope.project_ahope1.Event.ActivityEvent;
import studio.ahope.project_ahope1.Event.PermissionEvent;
import studio.ahope.project_ahope1.databinding.MainActivityBinding;
import studio.ahope.project_ahope1.Service.MainService;
import studio.ahope.project_ahope1.Manager.PermissionManager;

/**
 * Last update : 2016-11-08
 */
/* while working */

public class MainActivity extends AppCompatActivity {

    private Drawable drawable;
    private Intent MainService;
    private MainActivityBinding binding;
    private PermissionManager permissionManager;
    ActivityEvent activityEvent;
    PermissionEvent permissionEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionManager = new PermissionManager(this);
        startBroadcast(this);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        themeEngine(0);
        //number of 0(zero) theme is Normal theme for application

        MainService = new Intent(this, MainService.class);
        startService(MainService);
    }

    private void themeEngine(int theme){
        switch(theme){
            case 0:
                // back ground (it may be changed for connected server text
                getResource(R.drawable.testbg);
                getWindow().setBackgroundDrawable(drawable);
                drawable = null; // it should be lunched every time when use getResource void
                // text color adapted by theme
                binding.winfo1.setTextColor(getResources().getColor(android.R.color.black));
                binding.winfo2.setTextColor(getResources().getColor(android.R.color.black));
                binding.comment.setTextColor(getResources().getColor(android.R.color.white));
                // more for btn bg image maybe
            break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        permissionEvent.stop("NEED_PERMISSION");
        stopService(MainService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if(permissionManager.getAllStatus(permissions)) {
                    startService(MainService);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permissionDefined, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    public MainActivityBinding getBinding(){
        return this.binding;
    }

    private void startBroadcast(Context context) {
        permissionEvent = new PermissionEvent(context);
        permissionEvent.start("NEED_PERMISSION");
        activityEvent = new ActivityEvent(this);
        activityEvent.set("CHANGE_WEATHER_PROFILE");
    }

    public void getResource(int dr){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = ContextCompat.getDrawable(getApplicationContext(),dr);
        }else{
            drawable = getResources().getDrawable(dr);
        }
    }
}
