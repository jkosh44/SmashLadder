package hu.ait.android.smashladder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentStartMainMenu = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intentStartMainMenu);
                finish();
            }
        }, SPLASH_TIMER);
    }

}