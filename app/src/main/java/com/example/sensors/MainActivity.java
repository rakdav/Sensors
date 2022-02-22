package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private float[] rotationMatrix;
    private float[] orientaion;
    private ImageView image1;
    private ImageView image2;
    private int[] images={R.drawable.one,R.drawable.two,R.drawable.three,
                          R.drawable.four,R.drawable.five,R.drawable.six};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        rotationMatrix=new float[16];
        orientaion=new float[3];
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void loadSensorData(SensorEvent event)
    {
        final int type=event.sensor.getType();
        if(type==Sensor.TYPE_ACCELEROMETER)
        {
            orientaion=event.values.clone();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        loadSensorData(sensorEvent);
        SensorManager.getOrientation(rotationMatrix,orientaion);
        Random rnd1=new Random();
       // int n1=rnd1.nextInt(6);
        int n1=10*Math.abs(Math.round(orientaion[0]));
        if(n1>=0&&n1<6)
            image1.setImageResource(images[n1]);
        Random rnd2=new Random();
        int n2=rnd2.nextInt(6);
        image2.setImageResource(images[n2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}