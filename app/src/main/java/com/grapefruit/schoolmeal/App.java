package com.grapefruit.schoolmeal;

import android.app.Application;

import com.mikepenz.iconics.Iconics;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconics.init(this);
    }
}
