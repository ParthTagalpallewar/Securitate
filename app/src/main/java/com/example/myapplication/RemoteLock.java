package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.params.Params;
import com.google.android.gms.location.LocationRequest;

public class RemoteLock extends AppCompatActivity {

    Button disableBtn;

    private LocationRequest locationRequest;
    Switch switchCompat;
    MediaPlayer mediaPlayer;
    RemoteLockReceiver findMyPhoneReceiver;

    static final int RESULT_ENABLE = 1 ;
    DevicePolicyManager deviceManger ;
    ComponentName compName ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_lock);

        deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this,DeviceAdmin.class);
        disableBtn = findViewById(R.id.disableButton);
        disableBtn.setVisibility(View.INVISIBLE);

        switchCompat=findViewById(R.id.switch_btn);

        if(Params.remot_lock_fun_state == 0) {
            switchCompat.setChecked(false);
            Params.remoteLockAccess = 0;
        }
        else {
            switchCompat.setChecked(true);
            Params.remoteLockAccess = 1;
        }

        boolean active = deviceManger .isAdminActive( compName ) ;
        if (active) {
            disableBtn .setText( "Disable" ) ;
        } else {
            disableBtn .setText( "Enable" ) ;
        }

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()){
                    Params.charge_fun_state = 1;
                    Params.remoteLockAccess = 1;
                    Toast.makeText(RemoteLock.this, "On", Toast.LENGTH_SHORT).show();
                    boolean active = deviceManger .isAdminActive( compName ) ;
                    if (active) {
                        deviceManger .removeActiveAdmin( compName ) ;
                        disableBtn.setText( "Enable" ) ;
                    } else {
                        Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
                        intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
                        intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , "You should enable the app!" ) ;
                        startActivityForResult(intent , RESULT_ENABLE ) ;
                    }
                    registerReceiver(findMyPhoneReceiver, new IntentFilter());
                }else{
                    Params.fun_state = 0;
                    Params.remoteLockAccess = 0;
                    Toast.makeText(RemoteLock.this, "Off", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(RemoteLock.this, ChargeService.class));
                }
            }
        });



        disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean active = deviceManger .isAdminActive( compName ) ;
                if (active) {
                    deviceManger .removeActiveAdmin( compName ) ;
                    disableBtn.setText( "Enable" ) ;
                } else {
                    Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
                    intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
                    intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , "You should enable the app!" ) ;
                    startActivityForResult(intent , RESULT_ENABLE ) ;
                }
            }
        });

    }

    public void enablePhone(){
        boolean active = deviceManger.isAdminActive( compName ) ;
        if (active) {
            deviceManger.removeActiveAdmin( compName ) ;
            disableBtn .setText( "Enable" ) ;
        } else {
            Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , "You should enable the app!" ) ;
            startActivityForResult(intent , RESULT_ENABLE ) ;
        }
    }

    public void lockPhone(){
        deviceManger.lockNow() ;
    }

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent
            data) {
        super .onActivityResult(requestCode , resultCode , data) ;
        switch (requestCode) {
            case RESULT_ENABLE :
                if (resultCode == Activity. RESULT_OK ) {
                    disableBtn .setText( "Disable" ) ;
                } else {
                    Toast. makeText (getApplicationContext() , "Failed!" ,
                            Toast. LENGTH_SHORT ).show() ;
                }
                return;
        }
    }


}