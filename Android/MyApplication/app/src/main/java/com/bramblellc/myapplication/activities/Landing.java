package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bramblellc.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
        init();
    }

    public void init() {
        boolean setup = getIntent().getBooleanExtra("setup", false);
        if (setup) {
            new MaterialDialog.Builder(this)
                    .title("WELCOME")
                    .content("Let's start out by adding some Guard Dogs to your account!")
                    .positiveText("Next")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            addDogsChain();
                        }
                    })
                    .show();
        }
    }

    public void addDogsChain() {
        new MaterialDialog.Builder(this)
                .title("ADD A GUARD DOG")
                .content("Enter the phone number of the Guard Dog you would like to add.")
                .positiveText("Add")
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SharedPreferences prefs = getSharedPreferences("GuardDog", MODE_PRIVATE);
                        SharedPreferences.Editor editor = getSharedPreferences("GuardDog", MODE_PRIVATE).edit();
                        Set<String> dogSet = prefs.getStringSet("dogs", null);
                        if (dogSet == null) {
                            dogSet = new HashSet<>();
                        }
                        dogSet.add(input.toString());
                        editor.putStringSet("dogs", dogSet);
                        editor.commit();
                        addAnotherDog();
                    }
                }).show();
    }

    public void addAnotherDog() {
        new MaterialDialog.Builder(this)
                .content("Would you like to add more Guard Dogs?")
                .positiveText("Add another")
                .negativeText("No")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        addDogsChain();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        phoneRepairContact();
                    }
                })
                .show();
    }

    public void phoneRepairContact() {
        new MaterialDialog.Builder(this)
                .title("PHONE REPAIR")
                .content("Do you have a warranty or insurance for your phone and want to add their " +
                        "phone number to be contacted if your phone is damaged?")
                .positiveText("Yes")
                .negativeText("No")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        addPhoneService();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        congrats();
                    }
                })
                .show();
    }

    public void congrats() {
        new MaterialDialog.Builder(this)
                .title("WELCOME TO THE PACK!")
                .content("Thanks for downloading Guard Dog. We hope we can make you feel as safe as man's best friend.")
                .positiveText("Get started")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                    }
                })
                .show();
    }

    public void addPhoneService() {
        new MaterialDialog.Builder(this)
                .title("ADD A PHONE SERVICE CONTACT")
                .content("Enter the phone number of the phone service provider you would like to add.")
                .positiveText("Add")
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SharedPreferences.Editor editor = getSharedPreferences("GuardDog", MODE_PRIVATE).edit();
                        String contact = input.toString();
                        editor.putString("phone", contact);
                        editor.commit();
                        congrats();
                    }
                }).show();
    }

    // LOGIN PATH
    // when the login button is pressed
    public void settingsPressed(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
