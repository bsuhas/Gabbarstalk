package com.gabbarstalk;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by SUHAS on 09/04/2019.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/sofia_pro_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
