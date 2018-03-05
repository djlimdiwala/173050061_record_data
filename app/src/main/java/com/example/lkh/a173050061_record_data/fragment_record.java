package com.example.lkh.a173050061_record_data;



/**
 * Created by DJL on 3/4/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class fragment_record extends Fragment {


    SharedPreferences preferences;
    private RadioGroup rrg;
    private RadioButton rbt;



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


                Intent intent = new Intent(getActivity(),accelerometer.class);
                if (isChecked) {

                    Log.e("kahaa hai", "checked main hu");
                    int gps_c = preferences.getInt("gps_check", -1);
                    int acc_c = preferences.getInt("acc_check", -1);

                    if (gps_c == 0 && acc_c == 0) {

                    }
                    else {

                        rrg = (RadioGroup) rootView.findViewById(R.id.record_rg);
                        int selectedId = rrg.getCheckedRadioButtonId();
                        rbt = (RadioButton) rootView.findViewById(selectedId);
                        Log.e("radio", rbt.getText().toString());
                        int g = preferences.getInt("gender_check", -1);
                        String gc;
                        if (g == 1) {
                            gc = "Female";
                        } else {
                            gc = "Male";
                        }

                        intent.putExtra("first", preferences.getString("First_name", ""));
                        intent.putExtra("last", preferences.getString("Last_name", ""));
                        intent.putExtra("mobile", preferences.getString("Mobile_no", ""));
                        intent.putExtra("email", preferences.getString("E_mail", ""));
                        intent.putExtra("gend", preferences.getString("gen",""));
                        intent.putExtra("age", preferences.getString("age_person", ""));
                        intent.putExtra("label", rbt.getText().toString());
                        intent.putExtra("acc_ch",acc_c);
                        intent.putExtra("gps_ch",gps_c);

                        getActivity().startService(intent);

                        if (recordings_list.size() >= 5) {
                            recordings_list.add(0, format);
                            recordings_list.remove(recordings_list.size() - 1);
                        } else {
                            recordings_list.add(0, format);
                    }
                }
                }
                else
                {
                    Log.e("kahaa hai", "UNchecked main hu");
                    getActivity().stopService(intent);
                    arrayAdapter.notifyDataSetChanged();

                }



            }
        });

        return rootView;
    }

}
