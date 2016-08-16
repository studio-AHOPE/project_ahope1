package studio.ahope.project_ahope1;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import studio.ahope.project_ahope1.lib.ParsingInfo;
import studio.ahope.project_ahope1.lib.PermissionManager;

public class MainActivity extends AppCompatActivity {

    // setting permission

    public final String[] request = {
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    Drawable drawable;
    ParsingInfo parsing;
    PermissionManager permanager;
    public final int requestPer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permanager.autoRequest(this, this, request);

        setContentView(R.layout.main_activity);
        getResource(R.drawable.testbg);
        getWindow().setBackgroundDrawable(drawable);
        drawable = null;


        /*while working
        if() {
            parsing.getPInfo("open");
        }
        */
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
