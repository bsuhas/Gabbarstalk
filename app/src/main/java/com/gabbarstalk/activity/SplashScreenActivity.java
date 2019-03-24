package com.gabbarstalk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.gabbarstalk.R;
import com.gabbarstalk.utils.UserPreferences;


public class SplashScreenActivity extends Activity {
    private static final long SPLASH_DELAY = 3000;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mContext = this;
        displayLandingScreen();
    }

    public void displayLandingScreen() {
        Handler handler = new Handler();
        handler.postDelayed(runnable, SplashScreenActivity.SPLASH_DELAY);
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            checkUserLogin();
        }
    };

    private void checkUserLogin() {
        boolean isLoggedIn = UserPreferences.getInstance(mContext).isUserLogin();

        Intent loginIntent = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
        startActivity(loginIntent);
        finish();


//        if (isLoggedIn) {
//            Intent intent = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent loginIntent = new Intent(SplashScreenActivity.this, RegisterScreenActivity.class);
//            startActivity(loginIntent);
//            finish();
//        }
    }
}

