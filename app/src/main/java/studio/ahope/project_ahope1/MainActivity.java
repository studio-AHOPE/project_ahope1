package studio.ahope.project_ahope1;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import studio.ahope.project_ahope1.databinding.MainActivityBinding;
import studio.ahope.project_ahope1.lib.ServiceSystem;
import studio.ahope.project_ahope1.lib.PermissionManager;

/**
 * Last update : 2016-08-18
 */

public class MainActivity extends AppCompatActivity {

    // setting permission

    public final String[] request = {
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    Drawable drawable;
    public final int requestPer = 1;
    Intent serviceSystem;
    public static MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionManager.autoRequest(this, this, request);
        serviceSystem = new Intent(this, ServiceSystem.class);
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
            case requestPer: {
                    Toast.makeText(getApplicationContext(), R.string.perDefined, Toast.LENGTH_LONG).show();
                    finish();
            }
        }
    }

    public void getResource(int dr){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = ContextCompat.getDrawable(getApplicationContext(),dr);
        }else{
            drawable = getResources().getDrawable(dr);
        }
    }
}
