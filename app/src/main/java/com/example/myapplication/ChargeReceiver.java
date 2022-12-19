package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.myapplication.R;

public class ChargeReceiver extends BroadcastReceiver {

    Context ct;
    MediaPlayer mediaPlayer;

    ChargeReceiver(){
        
    }

    ChargeReceiver(Context ct){
         mediaPlayer = MediaPlayer.create(ct, R.raw.song);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "Charging is on.", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
        }
        else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context, "Charging is off", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();
        }
    }

    public void stopMediaPlayer(){
        mediaPlayer.stop();
    }
}
