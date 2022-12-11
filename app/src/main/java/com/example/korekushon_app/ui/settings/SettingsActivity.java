package com.example.korekushon_app.ui.settings;

import android.os.Bundle;

import android.preference.PreferenceActivity;

import com.example.korekushon_app.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}