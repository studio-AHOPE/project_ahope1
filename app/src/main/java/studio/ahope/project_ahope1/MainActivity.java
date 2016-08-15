package studio.ahope.project_ahope1;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getResource(R.drawable.testbg);
        getWindow().setBackgroundDrawable(drawable);
        drawable = null;
    }

    public void getResource(int dr){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = ContextCompat.getDrawable(getApplicationContext(),dr);
        }else{
            drawable = getResources().getDrawable(dr);
        }
    }
}
