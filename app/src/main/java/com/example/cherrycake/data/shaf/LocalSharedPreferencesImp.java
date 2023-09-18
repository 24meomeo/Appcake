package com.example.cherrycake.data.shaf;

import android.content.Context;
import android.content.SharedPreferences;


public class LocalSharedPreferencesImp implements LocalSharedPreferences {

    private final SharedPreferences preferences;

    public LocalSharedPreferencesImp(Context context) {
        preferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }

    @Override
    public void saveOtherColor(int value) {
        preferences.edit().putInt("other_color", value).apply();
    }

    @Override
    public int getOtherColor() {
        return preferences.getInt("other_color", 1);
    }

}
