package com.geektach.newsapp42;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {

        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void SaveState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }

    public void saveEditText(String name) {
        preferences.edit().putString("text", name).apply();
    }

    public String isEditText() {
        return preferences.getString("text", "");
    }

    public void saveImageView(String image) {
        preferences.edit().putString("image", image).apply();
    }

    public String isImageView(){
        return preferences.getString("image", "");
    }
}
