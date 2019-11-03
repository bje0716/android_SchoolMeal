package com.grapefruit.schoolmeal.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PreferenceHelper {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    public PreferenceHelper(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    public void putBoolean(String name, boolean value) {
        editor.putBoolean(name, value).commit();
    }

    public void putString(String name, String value) {
        editor.putString(name, value).commit();
    }

    public boolean getBoolean(String name, boolean value) {
        return sp.getBoolean(name, value);
    }

    public String getString(String name, String value) {
        return sp.getString(name, value);
    }
}
