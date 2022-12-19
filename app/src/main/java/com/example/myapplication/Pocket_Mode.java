package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class Pocket_Mode extends AppCompatActivity implements SensorEventListener{

    Switch switchCompat;
    TextView textView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket__mode);

        switchCompat=findViewById(R.id.switch_btn);
        textView = findViewById(R.id.temp);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()) {
                    //Toast.makeText(this, "Object is Close..", Toast.LENGTH_SHORT).show();
                    //imageView.setImageResource(R.drawable.one);
                    textView.setText("Switch is ON");
                    if(mediaPlayer!=null)
                    {
                        mediaPlayer.start();
                    }
                    else {
                        playNew();
                    }

                }
                else
                {
                    if(mediaPlayer!=null){
                        mediaPlayer.stop();
                    }
                    else
                    {
                        mediaPlayer.stop();
                    }
                    //textView.setText("Switch is OFF");

                }

            }
        });


    }

    public void play (View v) {
        playNew();

    }

    public void playNew(){

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            Sensor proxi_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            if (proxi_sensor != null) {
                //sensorManager.registerListener(this, proxi_sensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this,proxi_sensor,SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }


    @Override
    public void onSensorChanged (SensorEvent sensorEvent){

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            //((TextView) findViewById(R.id.txt1)).setText("Values : " + sensorEvent.values[0]);

            if (sensorEvent.values[0] > 0) {
                Toast.makeText(this, "Object is far..", Toast.LENGTH_SHORT).show();
                if (switchCompat.isChecked()) {
                    //playNew();
                    mediaPlayer = MediaPlayer.create(this, R.raw.song);
                    mediaPlayer.start();
                }
                else {
                    mediaPlayer.stop();
                }


            } else {
                Toast.makeText(this, "Object is Close..", Toast.LENGTH_SHORT).show();

                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged (Sensor sensor,int i){

    }
}