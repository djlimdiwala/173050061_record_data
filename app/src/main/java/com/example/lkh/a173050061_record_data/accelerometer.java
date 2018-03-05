package com.example.lkh.a173050061_record_data;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;

/**
 * Created by DJL on 3/4/2018.
 */

public class accelerometer extends Service implements SensorEventListener, LocationListener {


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private String label = "walking";
    private static final int SHAKE_THRESHOLD = 600;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;

    private LocationListener listener;
    private LocationManager locationManager;



    String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String path = csv + File.separator + "sample.csv";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



                Log.e("status","started");
                label = intent.getExtras().get("label").toString();
                String str = intent.getExtras().get("first") + "," + intent.getExtras().get("last") + "," + intent.getExtras().get("mobile") + "," + intent.getExtras().get("email") + "," + intent.getExtras().get("gend") + "," + intent.getExtras().get("age") + "\n";

                int ac = (int) intent.getExtras().get("acc_ch");
                int gp = (int) intent.getExtras().get("gps_ch");

                Log.e("path",path);
                try {
                    FileOutputStream stream = new FileOutputStream(path);
                    stream.write(str.getBytes());
                    str = "Timestamp,Lat,Long,Accelx,Accely,Accelz,Label\n";
                    stream.write(str.getBytes());
                    stream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.e("gps",String.valueOf(gp));
                Log.e("acc",String.valueOf(ac));

                if (ac == 1 && gp == 1)
                        {
                            senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                            senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());





                            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return START_STICKY;
                            }
                            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                            onLocationChanged(location);

                        }

                else if (ac == 0 && gp == 1)
                        {
                            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return START_STICKY;
                            }
                            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                            onLocationChanged(location);
                        }

                else if (ac == 1 && gp == 0)
                        {
                            senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                            senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());
                        }










        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            last_x = x;
            last_y = y;
            last_z = z;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            String format = simpleDateFormat.format(new Date());
            String str = format + "," + String.valueOf(currentLatitude) + "," + String.valueOf(currentLongitude) + "," + String.valueOf(last_x) + "," + String.valueOf(last_y) + "," + String.valueOf(last_z) + "," + label + "\n";


            try {
                FileOutputStream stream = new FileOutputStream(path, true);
                stream.write(str.getBytes());
                stream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String read = String.valueOf(last_x) + "  " + String.valueOf(last_y) + "  " + String.valueOf(last_z);
//            Log.e("recording",read);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("status","closed");
        stopSelf();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        String str = format + "," + String.valueOf(currentLatitude) + "," + String.valueOf(currentLongitude) + "," + String.valueOf(last_x) + "," + String.valueOf(last_y) + "," + String.valueOf(last_z) + "," + label + "\n";


        try {
            FileOutputStream stream = new FileOutputStream(path, true);
            stream.write(str.getBytes());
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("coordinates", location.getLongitude() + "  " + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
