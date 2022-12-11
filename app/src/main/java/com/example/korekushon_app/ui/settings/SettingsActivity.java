package com.example.korekushon_app.ui.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.preference.PreferenceActivity;
import androidx.preference.PreferenceManager;

import com.example.korekushon_app.R;

public class SettingsActivity extends PreferenceActivity  {

    public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Log.i("Pref", prefs.getString("PC_API_Key", "NA"));
    }
}