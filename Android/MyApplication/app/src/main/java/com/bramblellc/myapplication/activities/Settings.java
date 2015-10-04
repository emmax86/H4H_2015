package com.bramblellc.myapplication.activities;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bramblellc.myapplication.R;
import com.bramblellc.myapplication.layouts.CustomActionbar;

public class Settings extends Activity {

    private CustomActionbar settingsCustomActionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        settingsCustomActionbar = (CustomActionbar) findViewById(R.id.settings_custom_actionbar);

        settingsCustomActionbar.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
