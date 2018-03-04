package com.example.lkh.a173050061_record_data;



/**
 * Created by DJL on 3/4/2018.
 */

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class fragment_record extends Fragment {


    SharedPreferences preferences;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGPS;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private float longitude, latitude;
    private RadioGroup rrg;
    private RadioButton rbt;
    private static final int SHAKE_THRESHOLD = 600;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);



        preferences = this.getActivity().getSharedPreferences("myAppPrefs", getContext().MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();




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
//                String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//                String path = csv + File.separator + "sample.csv";
//                String str = "first name,last name,mobile, email,gender,age";
//
//                Log.e("path",path);
//                try {
//                    FileOutputStream stream = new FileOutputStream(path);
//                    stream.write(str.getBytes());
//                    stream.close();
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                Intent intent = new Intent(getActivity(),accelerometer.class);
                if (isChecked)
                {
                    rrg = (RadioGroup) rootView.findViewById(R.id.record_rg);
                    int selectedId = rrg.getCheckedRadioButtonId();
                    rbt = (RadioButton) rootView.findViewById(selectedId);
                    Log.e("radio", rbt.getText().toString());
                    int g = preferences.getInt("gender_check",-1);
                    String gc;
                    if (g == 1)
                    {
                        gc = "Female";
                    }
                    else
                    {
                        gc = "Male";
                    }

                    intent.putExtra("first",preferences.getString("first_check",""));
                    intent.putExtra("last",preferences.getString("last_check",""));
                    intent.putExtra("mobile",preferences.getString("mobile_check",""));
                    intent.putExtra("email",preferences.getString("mail_check",""));
                    intent.putExtra("gend",gc);
                    intent.putExtra("age",preferences.getString("age_check",""));
                    intent.putExtra("label",rbt.getText().toString());

                    getActivity().startService(intent);

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
                    getActivity().stopService(intent);
                    arrayAdapter.notifyDataSetChanged();

                }
            }
        });

        return rootView;
    }

}
