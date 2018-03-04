package com.example.lkh.a173050061_record_data;



/**
 * Created by DJL on 3/4/2018.
 */

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.hardware.SensorEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class fragment_record extends Fragment implements SensorEventListener {


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGPS;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private float longitude, latitude;
    private static final int SHAKE_THRESHOLD = 600;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);


        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);






        ListView records_view = (ListView) rootView.findViewById(R.id.records_view);

        String[] recordings = new String[] {};
        // Create a List from String Array elements
        final List<String> recordings_list = new ArrayList<String>(Arrays.asList(recordings));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recordings_list);

        // DataBind ListView with items from ArrayAdapter
        records_view.setAdapter(arrayAdapter);

        Switch check_switch;
        check_switch = (Switch) rootView.findViewById(R.id.record_switch);


        check_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String path = csv + File.separator + "sample.csv";
                String str = "first name,last name,mobile, email,gender,age";

                Log.e("path",path);
                try {
                    FileOutputStream stream = new FileOutputStream(path);
                    stream.write(str.getBytes());
                    stream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (isChecked)
                {

                    getActivity().startService(new Intent(getActivity(),accelerometer.class));

//                    senSensorManager.registerListener(fragment_record.this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

                    if (recordings_list.size() >= 5)
                    {
                        recordings_list.add(0,format);
                        recordings_list.remove(recordings_list.size()-1);
                    }
                    else {
                        recordings_list.add(0,format);
                    }
                }
                else
                {
                    getActivity().stopService(new Intent(getActivity(),accelerometer.class));
                    senSensorManager.unregisterListener(fragment_record.this);
                    arrayAdapter.notifyDataSetChanged();

                }
            }
        });

        return rootView;
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    public void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
