package com.example.iWalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.CompoundButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch switchBtn = findViewById(R.id.switch1);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    buttonView.setText("Night Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    buttonView.setText("Light Mode");
                }
            }

        });
        //is night mode on ?
        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        switchBtn.setChecked(isNightModeOn);
        if (isNightModeOn) {
            switchBtn.setText("Night Mode");
        } else {
            switchBtn.setText("Light Mode");
        }
    }

    @Override
    public void recreate() {
   finish();
   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
   startActivity(getIntent());
   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void onClick(View v) {
        Intent settingsIntent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(settingsIntent);


    }
}