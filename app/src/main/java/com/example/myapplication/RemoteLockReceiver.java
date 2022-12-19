package com.example.myapplication;


import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.data.MyDbHandler;
import com.example.myapplication.params.Params;

import static android.Manifest.permission.ANSWER_PHONE_CALLS;

public class RemoteLockReceiver extends BroadcastReceiver {

    private static String SMS = "android.provider.Telephony.SMS_RECEIVED";
    LocationManager locationManager;
    Context cxt;
    MediaPlayer mediaPlayer;
    DevicePolicyManager deviceManger ;
    ComponentName compName ;

    @Override
    public void onReceive(Context context, Intent intent) {
        deviceManger = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        compName = new ComponentName(context, DeviceAdmin.class);


        Toast.makeText(context, "Here", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals(SMS)) {
            Bundle bundle = intent.getExtras();

            Object[] objects = (Object[]) bundle.get("pdus");

            SmsMessage[] messages = new SmsMessage[objects.length];

            for (int i = 0; i < objects.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }


            Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            cursor.moveToFirst();

            // Fetching last Sms String
            String last_sms = messages[0].getMessageBody();
            String msg_from = messages[0].getOriginatingAddress();

            last_sms = last_sms.trim();

            if (last_sms.equalsIgnoreCase("Find My Phone 12345")) {
                Toast.makeText(context, "Yess", Toast.LENGTH_SHORT).show();
                if(Params.remoteLockAccess == 1)
                    deviceManger.lockNow();
                else
                    Toast.makeText(context,"No remoteLockAccess",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
