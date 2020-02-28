package com.mapakampovasrbije.kampmapa.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mapakampovasrbije.kampmapa.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainTabsActivity.class));
            SplashActivity.this.finish();
        }, 2000);
    }

}
