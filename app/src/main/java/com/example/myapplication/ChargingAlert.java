package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.params.Params;

public class ChargingAlert extends AppCompatActivity {

    ChargeReceiver chargeReceiver;
    Switch switchCompat;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_alert);

        switchCompat=findViewById(R.id.switch_btn);

        if(Params.charge_fun_state == 0) {
            switchCompat.setChecked(false);
        }
        else {
            switchCompat.setChecked(true);
        }

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()){
                    Params.charge_fun_state = 1;
                    Toast.makeText(ChargingAlert.this, "On", Toast.LENGTH_SHORT).show();
                    startService(new Intent(ChargingAlert.this, ChargeService.class));
                }else{
                    Params.fun_state = 0;
                    Toast.makeText(ChargingAlert.this, "Off", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(ChargingAlert.this, ChargeService.class));

                }
            }
        });

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(chargeReceiver);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        chargeReceiver = new ChargeReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
//        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
//        registerReceiver(chargeReceiver, intentFilter);
//    }
}