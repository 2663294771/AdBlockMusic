package com.example.qqmusicadblock;

import android.os.Bundle;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PrefManager pref = new PrefManager(this);

        CheckBox cbSplash = findViewById(R.id.cb_splash);
        CheckBox cbBanner = findViewById(R.id.cb_banner);
        CheckBox cbMePage = findViewById(R.id.cb_me_page);
        CheckBox cbPopup = findViewById(R.id.cb_popup);

        cbSplash.setChecked(pref.isSplashEnabled());
        cbBanner.setChecked(pref.isBannerEnabled());
        cbMePage.setChecked(pref.isMePageEnabled());
        cbPopup.setChecked(pref.isPopupEnabled());

        cbSplash.setOnCheckedChangeListener((buttonView, isChecked) ->
                pref.saveSetting("pref_splash", isChecked));
        cbBanner.setOnCheckedChangeListener((buttonView, isChecked) ->
                pref.saveSetting("pref_banner", isChecked));
        cbMePage.setOnCheckedChangeListener((buttonView, isChecked) ->
                pref.saveSetting("pref_me_page", isChecked));
        cbPopup.setOnCheckedChangeListener((buttonView, isChecked) ->
                pref.saveSetting("pref_popup", isChecked));
    }
}
