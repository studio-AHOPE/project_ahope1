package studio.ahope.project_ahope1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import studio.ahope.project_ahope1.databinding.MainActivityBinding;
import studio.ahope.project_ahope1.Service.MainService;
import studio.ahope.project_ahope1.Manager.PermissionManager;

/**
 * Last update : 2016-10-30
 */
/* while working */

public class MainActivity extends AppCompatActivity {

    // setting permission

    public final String[] request = {
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private Drawable drawable;
    private Intent serviceSystem;
    private MainActivityBinding binding;
    private PermissionManager permissionManager = new PermissionManager();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        permissionManager.autoRequest(this, this, request);
        serviceSystem = new Intent(this, MainService.class);
        startService(serviceSystem);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        themeEngine(0);
        //number of 0(zero) theme is Normal theme for application

        /*while working
        if() {
            parsing.getPInfo("open");
        }
        */
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

        stopService(serviceSystem);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                } else {
                    Toast.makeText(getApplicationContext(), R.string.perDefined, Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }

    public MainActivityBinding getBinding(){
        return this.binding;
    }

    public void getResource(int dr){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = ContextCompat.getDrawable(getApplicationContext(),dr);
        }else{
            drawable = getResources().getDrawable(dr);
        }
    }
}
