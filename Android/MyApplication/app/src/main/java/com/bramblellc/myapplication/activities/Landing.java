package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bramblellc.myapplication.R;

import java.util.Random;

public class Landing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Random rnd = new Random();
        int index = rnd.nextInt(3);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.background);
        if (index == 1) {
            layout.setBackgroundResource(R.drawable.landing_background_1);
        }
        else if (index == 2) {
            layout.setBackgroundResource(R.drawable.landing_background_2);
        }
        else {
            layout.setBackgroundResource(R.drawable.landing_background_3);
        }
    }

    // LOGIN PATH
    // when the login button is pressed
    public void settingsPressed(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
