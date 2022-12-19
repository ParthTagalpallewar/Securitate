package com.example.myapplication;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.data.MyDbHandler;
import com.example.myapplication.params.Params;

import static android.Manifest.permission.ANSWER_PHONE_CALLS;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            if(Params.fun_state == 1) {
                MyDbHandler db = new MyDbHandler(context);
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.e("callChk", "phone number : " + phoneNumber);
                Toast.makeText(context, "Call is ringing", Toast.LENGTH_SHORT);
                if (context.checkSelfPermission(ANSWER_PHONE_CALLS)
                        != PackageManager.PERMISSION_GRANTED) {
//                context.requestPermissions(new String[]{ANSWER_PHONE_CALLS}, 1000);
                }
                TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(phoneNumber != null) {
                        if (db.isPhoneNumberPresent(phoneNumber) && Params.fun_state == 1)
                            telecomManager.acceptRingingCall();
                        else
                            Toast.makeText(context, "Phone NUmber is not present : " + phoneNumber, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error here in oreo", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
