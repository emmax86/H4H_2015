package com.bramblellc.myapplication.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bramblellc.myapplication.R;
import com.bramblellc.myapplication.layouts.CustomActionbar;
import com.bramblellc.myapplication.layouts.FullWidthButton;
import com.bramblellc.myapplication.layouts.FullWidthCheckbox;

public class Settings extends Activity {

    private CustomActionbar settingsCustomActionbar;
    private FullWidthButton myDogsFullWidthButton;
    private FullWidthCheckbox phoneAidFullWidthCheckbox;
    private FullWidthButton phoneAidInputFullWidthButton;
    private FullWidthButton testFullWidthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        settingsCustomActionbar = (CustomActionbar) findViewById(R.id.settings_custom_actionbar);
        myDogsFullWidthButton = (FullWidthButton) findViewById(R.id.my_dogs_full_width_button);
        phoneAidFullWidthCheckbox = (FullWidthCheckbox) findViewById(R.id.phone_aid_full_width_checkbox);
        phoneAidInputFullWidthButton = (FullWidthButton) findViewById(R.id.phone_aid_input_full_width_button);
        testFullWidthButton = (FullWidthButton) findViewById(R.id.test_full_width_button);

        settingsCustomActionbar.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myDogsFullWidthButton.getFullWidthButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDogsPressed(v);
            }
        });

        phoneAidFullWidthCheckbox.getFullWidthCheckbox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //phoneAidPressed(v);
            }
        });

        phoneAidInputFullWidthButton.getFullWidthButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //phoneAidInputPressed(v);
            }
        });

        testFullWidthButton.getFullWidthButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFullWidthPressed(v);
            }
        });
    }

    public void testFullWidthPressed(View view) {
        Intent intent = new Intent(Settings.this, TestEnvironment.class);
        startActivity(intent);
    }

    public void myDogsPressed(View view) {
        new MaterialDialog.Builder(this)
                .title("Add or remove Guard Dogs")
                .content("Enter the phone number of the Guard Dog you would like to add or remove.")
                .positiveText("Add")
                .negativeText("Remove")
                .input("", "this needs to be done", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        System.out.println("should have collected input. swag swag");
                    }
                }).show();
    }

}
