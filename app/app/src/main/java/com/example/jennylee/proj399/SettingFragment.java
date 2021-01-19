package com.example.jennylee.proj399;

import android.preference.PreferenceFragment;
import android.os.Bundle;

public class SettingFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
        addPreferencesFromResource(R.xml.preference);
    }
}
