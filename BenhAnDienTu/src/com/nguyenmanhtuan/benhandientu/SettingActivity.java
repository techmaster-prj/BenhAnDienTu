package com.nguyenmanhtuan.benhandientu;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingActivity extends Activity {
    private Spinner spinnerctrl;
    private Locale  myLocale;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        spinnerctrl = (Spinner) findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 1) {
                    Toast.makeText(arg0.getContext(), "You have selected English", Toast.LENGTH_SHORT).show();
                    setLocale("en");
                    Intent i = new Intent(SettingActivity.this, MenuActivity.class);
                    startActivity(i);
                } else if (arg2 == 2) {
                    Toast.makeText(arg0.getContext(), "You have selected VietNam", Toast.LENGTH_SHORT).show();
                    setLocale("vi");
                    Intent i1 = new Intent(SettingActivity.this, MenuActivity.class);
                    startActivity(i1);
                } else if (arg2 == 3) {
                    Toast.makeText(arg0.getContext(), "You have selected Japan", Toast.LENGTH_SHORT).show();
                    setLocale("ja");
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                
            }
            
        });
    };
    
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingActivity.class);
        startActivity(refresh);
    }
}
