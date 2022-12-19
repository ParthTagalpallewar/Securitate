package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.api.ApiUtilities;
import com.example.myapplication.data.MyDbHandler;
import com.example.myapplication.model.Contact;
import com.example.myapplication.model.NotificationData;
import com.example.myapplication.model.PushNotification;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.INTERNET;
import static com.example.myapplication.Constants.TOPIC;

public class Home_page extends AppCompatActivity {

    SliderView sliderView;
    int[] images = {R.drawable.one,
    R.drawable.two,
    R.drawable.three,
        R.drawable.four};

    ImageButton imageButton1, callMeBackButton, sosSettingsButton, chargeSafetyButton, findMyPhoneButton;

    PushNotification pushNotification;
    private LocationRequest locationRequest;
    private SmsManager sms;
    private Button sosBtn;
    Vibrator vibrator;
    MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sliderView = findViewById(R.id.image_slider);

        sliderAdapter sliders = new sliderAdapter(images);

        sliderView.setSliderAdapter(sliders);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        imageButton1=findViewById(R.id.pocket_mode_id);
        callMeBackButton = findViewById(R.id.callMeBackId);
        sosSettingsButton = findViewById(R.id.sosSettings);
        chargeSafetyButton = findViewById(R.id.chargeSafety);
        findMyPhoneButton = findViewById(R.id.findMyPhone);

        // Asking all permissions at a time
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ANSWER_PHONE_CALLS)
                        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Home_page.this,
                    new String[]
                            {
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.INTERNET,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.READ_CALL_LOG,
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.ANSWER_PHONE_CALLS
                            }, 2);
        }

        /**Pocket mode button*/
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, Pocket_Mode.class);
                startActivity(intent);
            }
        });

        /**Call me back button*/
        callMeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, CallMeBackActivity.class);
                startActivity(intent);
            }
        });

        /**Sos settings button*/
        sosSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, SosActivity.class);
                startActivity(intent);
            }
        });

        /**Charging safety settings button*/
        chargeSafetyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, ChargingAlert.class);
                startActivity(intent);
            }
        });

        /**Find My Phone setting button*/
        findMyPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, RemoteLock.class);
                startActivity(intent);
            }
        });

//        SOS Notification

        sosBtn = findViewById(R.id.sosBtn);
        dbHandler = new MyDbHandler(Home_page.this);

        sms = SmsManager.getDefault();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        if (checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1000);
        }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{INTERNET}, 1000);
                }

                // Step 1 : Getting Current Location
                getCurrentLocation();
            }
        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void sendSms(String mLatitude, String mLongitude) {


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // the message
            String message = "Latitude : " + mLatitude + "Longitude : " + mLongitude;

            String msg = "Please Help Me !!! \n" + "My Location : " + "https://maps.google.com/?q=" + mLatitude + "," + mLongitude;

            Constants.setmNotificationMessage(msg);

            sendNote(msg);

            // the phone numbers we want to send to
            String numbers[] = {"9096553454", "9960210230"};

            for(int i=0; i<dbHandler.readSosContact().size(); i++){
                ArrayList<Contact> contacts = dbHandler.readSosContact();
                Contact contact = contacts.get(i);
                String number = contact.getPhoneNumber();
                sms.sendTextMessage(number, null, msg, null, null);
            }



//            for (String number : numbers) {
//                sms.sendTextMessage(number, null, msg, null, null);
//            }
        } else {
            ActivityCompat.requestPermissions(Home_page.this, new String[]{Manifest.permission.SEND_SMS}, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Home_page.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(Home_page.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(Home_page.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        Constants.setmLatitude(latitude + "");
                                        Constants.setmLongitude(longitude + "");

                                        // Step 1
//                                        AddressText.setText("Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                                        Toast.makeText(Home_page.this, "Done", Toast.LENGTH_SHORT).show();
                                        vibrator.vibrate(2000);
                                        // Step 2
                                        sendSms(latitude + "", longitude + "");

                                    }

                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    private void sendNote(String msg) {
        Log.e("note","Here in sendNote()");
        pushNotification = new PushNotification(new NotificationData(" \uD83D\uDEA8 Please Help Me \uD83D\uDEA8", msg), TOPIC);
        sendNotification(pushNotification);
    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Home_page.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Home_page.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful())
                    Toast.makeText(Home_page.this, "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Home_page.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(Home_page.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            // Taking all permissions at a time
            case 2:
                if (grantResults.length > 0) {
                    if(requestCode == 1){
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            if (isGPSEnabled()) {

                                getCurrentLocation();

                            } else {
                                turnOnGPS();
                            }
                        }
                    }
                    boolean AnswerPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (AnswerPermission) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

}