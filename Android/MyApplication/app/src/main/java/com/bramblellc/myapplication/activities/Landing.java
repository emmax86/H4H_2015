package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bramblellc.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Random;

public class Landing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            SharedPreferences prefs = getSharedPreferences("GuardDog", MODE_PRIVATE);
            String un = prefs.getString("username", null);
            SharedPreferences.Editor editor = getSharedPreferences("GuardDog", MODE_PRIVATE).edit();
            editor.putString("old_username",un);
            editor.putString("username", null);
            editor.putString("password", null);
            editor.commit();
            Intent intent = new Intent(this, AccountPortal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
            finish();
            return;
        }
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

        LineChart lineChart = (LineChart) findViewById(R.id.landing_chart);
        lineChart.setDescription("Your current acceleration");
    }

    // LOGIN PATH
    // when the login button is pressed
    public void settingsPressed(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
