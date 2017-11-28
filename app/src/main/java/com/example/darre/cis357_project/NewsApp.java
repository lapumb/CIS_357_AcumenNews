package com.example.darre.cis357_project;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by DARRE on 11/27/2017.
 */

public class NewsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
