package com.example.iWalk;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;



import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private double MagnitudePrevious = 0.0;
    static Integer stepCount = 0;
    DBHelper DB;
    public HomeFragment(){
        // require a empty public constructor
    }
    View layoutView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DB=new DBHelper(getContext());
        layoutView = inflater.inflate(R.layout.fragment_home1, container, false);
        TextView calories = (TextView) layoutView.findViewById(R.id.TV_CALORIES);
        TextView distance = (TextView) layoutView.findViewById(R.id.TV_DISTANCE);
        TextView stepCountDaily = (TextView) layoutView.findViewById(R.id.stepCountDaily);

        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!= null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];
                    double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                    double MagnitudeDelta = Magnitude-MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if (MagnitudeDelta > 3){
                        stepCount++;
                        if(stepCount==100){

                        }

                    }

                    stepCountDaily.setText(stepCount+" steps today");
                    float cal = getCalories(stepCount);
                    float dis = getDistance(stepCount);
                    calories.setText(cal+"");
                    distance.setText(dis+" Km");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return layoutView;
    }
    public float getCalories(int steps){
        float Cal = (float) (steps * 0.045);
        return Cal;
    }
    public float getDistance(int steps){
        float distance =  (float) (steps * 0.000762);
        return distance;
    }
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        if(stepCount!= 0)
        editor.putInt("stepCount", stepCount);
        else
        editor.putInt("stepCount", 0);
        editor.apply();
    }

    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        if(stepCount!= 0)
        editor.putInt("stepCount", stepCount);
        else
            editor.putInt("stepCount", 0);
        editor.apply();
    }


    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt(
                "stepCount", 0);
    }


}
