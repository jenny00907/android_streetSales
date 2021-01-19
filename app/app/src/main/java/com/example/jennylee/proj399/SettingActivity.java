package com.example.jennylee.proj399;

import android.os.Bundle;
import android.app.Activity;


public class SettingActivity extends Activity{
    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment()).commit();
    }

}
