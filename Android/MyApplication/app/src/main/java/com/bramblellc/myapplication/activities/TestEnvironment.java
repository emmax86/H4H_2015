package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bramblellc.myapplication.R;

public class TestEnvironment extends Activity implements SensorEventListener {

    private float mLastX, mLastY, mLastZ;
    private boolean initialized;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long last_time;

    private long start_time;

    private TextView time_tv;

    private Button start;
    private Button event;
    private Button no_event;

    private boolean trial_in_progress;

    private Frame[] frame_data;
    private int index;

    public class Batch {
        public boolean real;
        public Frame[] frames;

        public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append(real);
            sb.append("\n");
            for(Frame f : frames) {
                sb.append(f.toString() + "\n");
            }
            return sb.toString();
        }
    }


    private class Frame  {
        public float accel_x;
        public float accel_y;
        public float accel_z;
        public int batch_order;

        public String toString() {
            return batch_order + " " + accel_x + " " + accel_y + " " + accel_z;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_environment_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initialized = false;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        start = (Button)findViewById(R.id.start_b);
        event = (Button)findViewById(R.id.true_b);
        no_event = (Button)findViewById(R.id.false_b);

        time_tv = (TextView)findViewById(R.id.time_tv);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trial_in_progress = true;
                time_tv.setText("0");
                start_time = System.currentTimeMillis();

                frame_data = new Frame[25];
                index = 0;
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createBatch(true);
            }
        });

        no_event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createBatch(false);
            }
        });
    }

    private Batch createBatch(boolean real) {
        try{
            Batch b = new Batch();
            b.real = real;
            b.frames = frame_data;
            System.out.println(b.toString());
            return b;
        } catch (Exception ex) {
            // Try to press true or false before recording
        }
        return null;
    }

    protected void onResume() {
        super.onResume();
        // sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        // sensorManager.unregisterListener(this);
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

            mLastX = x;
            mLastY = y;
            mLastZ = z;
            long current = System.currentTimeMillis();
            //System.out.print(x + " " + y + " " + z + " " + (current - last_time) + " ");
            //System.out.println(deltaX + " " + deltaY + " " + deltaZ);
            last_time = current;

            if (trial_in_progress)
            {
                long current_time = System.currentTimeMillis();
                // Push the data to the list
                time_tv.setText(Long.toString((current_time-start_time)));
                if (index < frame_data.length) {
                    Frame c_frame = new Frame();
                    c_frame.accel_x = x;
                    c_frame.accel_y = y;
                    c_frame.accel_z = z;
                    c_frame.batch_order = index;
                    frame_data[index] = c_frame;
                    index++;
                } else {
                    trial_in_progress = false;
                }

            }
        }


    }

}
