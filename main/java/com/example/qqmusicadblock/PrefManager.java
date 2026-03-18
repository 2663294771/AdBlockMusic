package com.example.qqmusicadblock;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "qqmusic_adblock_prefs";
    private final SharedPreferences prefs;

    public PrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSplashEnabled() {
        return prefs.getBoolean("pref_splash", true);
    }

    public boolean isBannerEnabled() {
        return prefs.getBoolean("pref_banner", true);
    }

    public boolean isMePageEnabled() {
        return prefs.getBoolean("pref_me_page", true);
    }

    public boolean isPopupEnabled() {
        return prefs.getBoolean("pref_popup", true);
    }

    public void saveSetting(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }
}
