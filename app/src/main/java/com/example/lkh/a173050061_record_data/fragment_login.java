package com.example.lkh.a173050061_record_data;

/**
 * Created by DJL on 3/4/2018.
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class fragment_login extends Fragment{


    SharedPreferences preferences;
    private EditText first;
    private EditText last;
    private EditText mobile_no;
    private EditText email;
    private EditText agee;
    private RadioGroup rg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        preferences = this.getActivity().getSharedPreferences("myAppPrefs", getContext().MODE_PRIVATE);
        first = (EditText) rootView.findViewById(R.id.first_name);
        last = (EditText) rootView.findViewById(R.id.last_name);
        mobile_no = (EditText) rootView.findViewById(R.id.mobile);
        email = (EditText) rootView.findViewById(R.id.email);
        agee = (EditText) rootView.findViewById(R.id.age);
        rg = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        final SharedPreferences.Editor editor = preferences.edit();



        editor.putString("First_name","");
        editor.putString("Last_name","");
        editor.putString("E_mail","");
        editor.putString("Mobile_no","");
        editor.putString("age_person","");
        editor.putString("gen", "");
        editor.apply();
        if(!preferences.contains("first_check")) {

            editor.putString("first_check","");
        }
        else
        {
            first.setText(preferences.getString("first_check",""));
        }


        if(!preferences.contains("last_check")) {

            editor.putString("last_check","");
        }
        else
        {
            last.setText(preferences.getString("last_check",""));
        }



        if(!preferences.contains("mail_check")) {

            editor.putString("mail_check","");
        }
        else
        {
            email.setText(preferences.getString("mail_check",""));
        }



        if(!preferences.contains("mobile_check")) {

            editor.putString("mobile_check","");
        }
        else
        {
            mobile_no.setText(preferences.getString("mobile_check",""));
        }



        if(!preferences.contains("age_check")) {

            editor.putString("age_check","");
        }
        else
        {
            agee.setText(preferences.getString("age_check",""));
        }


        if(!preferences.contains("gender_check")) {

            editor.putInt("gender_check",1);
        }
        else
        {
            rg.check(preferences.getInt("gender_check",1));
        }


        editor.apply();


        first.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                editor.putString("first_check",s.toString());
                editor.apply();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });


        last.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                editor.putString("last_check",s.toString());
                editor.apply();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });


        email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                editor.putString("mail_check",s.toString());
                editor.apply();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });



        mobile_no.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                editor.putString("mobile_check",s.toString());
                editor.apply();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });


        agee.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                editor.putString("age_check",s.toString());
                editor.apply();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg.getCheckedRadioButtonId();
                editor.putInt("gender_check",selectedId);
                editor.apply();

            }

        });


        Button log_in = (Button) rootView.findViewById(R.id.login);
        log_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                int flag = 1;

                if (first.getText().toString().length() == 0)
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    first.setError("Enter First Name");
                    first.requestFocus();
                }
                else if (last.getText().toString().length() == 0)
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    last.setError("Enter Last Name");
                    last.requestFocus();
                }
                else if (!mobile_validate(mobile_no.getText().toString()))
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    mobile_no.setError("Invalid mobile");
                    mobile_no.requestFocus();
                }
                else if (!email_validate(email.getText().toString()))
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    email.setError("Invalid Email");
                    email.requestFocus();
                }

                else if (agee.getText().toString().length() == 0)
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    agee.setError("Enter Age");
                    agee.requestFocus();
                }
                else if (!age_validate(agee.getText().toString()))
                {
                    flag = 0;
                    editor.putInt("incomplete",0);
                    editor.apply();
                    agee.setError("Invalid Age");
                    agee.requestFocus();
                }


                if (flag == 1)
                {
                    RadioGroup rrg = (RadioGroup) rootView.findViewById(R.id.radioGroup);
                    int selectedId = rrg.getCheckedRadioButtonId();
                    RadioButton rbt = (RadioButton) rootView.findViewById(selectedId);

                    editor.putString("First_name",first.getText().toString());
                    editor.putString("Last_name",last.getText().toString());
                    editor.putString("E_mail",email.getText().toString());
                    editor.putString("Mobile_no",mobile_no.getText().toString());
                    editor.putString("age_person",agee.getText().toString());
                    editor.putString("gen", rbt.getText().toString());
                    editor.putInt("incomplete",1);
                    editor.apply();

                    Toast.makeText(getContext(), "Successfully submitted...", Toast.LENGTH_LONG).show();

                    first.setText("");
                    last.setText("");
                    email.setText("");
                    mobile_no.setText("");
                    agee.setText("");

                }


            }

        });











        return rootView;
    }


    protected boolean email_validate(String mail)
    {
        if (TextUtils.isEmpty(mail))
        {
            return false;
        }
        else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }
    protected boolean mobile_validate(String mobile)
    {
        if (TextUtils.isEmpty(mobile))
        {
            return false;
        }
        else
        {
            return android.util.Patterns.PHONE.matcher(mobile).matches();
        }
    }


    protected boolean age_validate(String age)
    {
        Integer age_int = Integer.parseInt(age);
        if (age_int <= 0 || age_int > 150)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
