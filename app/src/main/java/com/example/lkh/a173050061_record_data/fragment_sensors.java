package com.example.lkh.a173050061_record_data;


/**
 * Created by DJL on 3/4/2018.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.MODE_PRIVATE;

public class fragment_sensors extends Fragment {

    private CheckBox acc_check;
    private CheckBox gps_check;
    private Context mContext;

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sensors, container, false);


        preferences = this.getActivity().getSharedPreferences("myAppPrefs", getContext().MODE_PRIVATE);

        final SharedPreferences.Editor editor = preferences.edit();
        acc_check = (CheckBox) rootView.findViewById(R.id.check_acc);
        gps_check = (CheckBox) rootView.findViewById(R.id.check_gps);

        editor.putString("Name","Harneet");
        if(!preferences.contains("acc_check")) {

            if (!acc_check.isChecked()) {
                editor.putInt("acc_check", 1);
            } else {
                editor.putInt("acc_check", 0);
            }
        }
        else
        {

            int i = preferences.getInt("acc_check",0);
            editor.putInt("acc_check", 1);
             if (i == 1)
             {
                 acc_check.setChecked(true);
             }
             else
             {
                 acc_check.setChecked(false);
             }

        }

        if(!preferences.contains("gps_check")) {
            if (!gps_check.isChecked())
            {
                editor.putInt("gps_check",1);
            }
            else
            {
                editor.putInt("gps_check",0);
            }
        }

        else
        {

            int i = preferences.getInt("gps_check",0);

            editor.putInt("gps_check", 1);
            if (i == 1)
            {
                gps_check.setChecked(true);
            }
            else
            {
                gps_check.setChecked(false);
            }
        }
        editor.apply();


        acc_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                   if (isChecked)
                                                   {
                                                       editor.putInt("acc_check",1);
                                                   }
                                                   else
                                                   {
                                                       editor.putInt("acc_check",0);
                                                   }
                                                   editor.apply();
                                               }
                                           }
        );

        gps_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                     if (isChecked)
                                                     {
                                                         editor.putInt("gps_check",1);
                                                     }
                                                     else
                                                     {
                                                         editor.putInt("gps_check",0);
                                                     }
                                                     editor.apply();
                                                 }
                                             }
        );


        return rootView;
    }
}
