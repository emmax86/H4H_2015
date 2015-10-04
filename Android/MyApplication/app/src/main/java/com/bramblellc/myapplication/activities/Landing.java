package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bramblellc.myapplication.R;

public class Landing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    // LOGIN PATH
    // when the login button is pressed
    public void settingsPressed(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
