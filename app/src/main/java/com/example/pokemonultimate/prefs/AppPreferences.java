package com.example.pokemonultimate.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static AppPreferences instance;

    private SharedPreferences sharedPreferences;

    private AppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE));
        }
    }

    public static AppPreferences getInstance() {
        return instance;
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

}
