package com.grapefruit.schoolmeal;

import android.app.Application;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconics.init(this);
        Iconics.registerFont(new CommunityMaterial());
        Iconics.registerFont(new WeatherIcons());
        Iconics.registerFont(new GoogleMaterial());
    }
}
