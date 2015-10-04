package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.bramblellc.myapplication.R;

public class TestEnvironment extends Activity implements SensorEventListener {

    private float mLastX, mLastY, mLastZ;
    private boolean initialized;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private final float NOISE = (float) 2.0;
    private long last_time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_environment_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initialized = false;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Implying
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!initialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            last_time = System.currentTimeMillis();
            initialized = true;
        } else {
            float deltaX = x - mLastX;
            float deltaY = y - mLastY;
            float deltaZ = z - mLastZ;
            /*
            if (deltaX < NOISE) deltaX = (float)0.0;
            if (deltaY < NOISE) deltaY = (float)0.0;
            if (deltaZ < NOISE) deltaZ = (float)0.0;
            */
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            long current = System.currentTimeMillis();
            System.out.print(x + " " + y + " " + z + " " + (current - last_time) + " ");
            System.out.println(deltaX + " " + deltaY + " " + deltaZ);
            last_time = current;
        }
    }

}
