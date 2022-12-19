package com.example.myapplication;

import android.media.MediaPlayer;

public class Constants {
    public static final String BASE_URL = "https://fcm.googleapis.com";
    public static final String SERVER_KEY = "AAAAKUA0fsA:APA91bFCSj88vphuooqQM_4TKfyT0kAbcyhXafO9QkjHAQJYK_7_gwK-kH-2bom27dt6cpsGwbeh580xDLYkeIqKOPZpFy8PttfelwJx_dEDaAQWnXOYYq_TdXI-7AEJye8oei87jNcb";
    public static final String CONTENT_TYPE = "application/json";
    public static final String TOPIC = "/topics/sangways";

    // Location Statics
    private static String mLatitude;
    private static String mLongitude;
    private static String mNotificationMessage;

    // Charging safety
    public static MediaPlayer mediaPlayer;
    public static int mediaPlayerFlag = 0;

    public int getMediaPlayerFlag() {
        return mediaPlayerFlag;
    }

    public void setMediaPlayerFlag(int mediaPlayerFlag) {
        this.mediaPlayerFlag = mediaPlayerFlag;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public static String getmNotificationMessage() {
        return mNotificationMessage;
    }

    public static void setmNotificationMessage(String mNotificationMessage) {
        com.example.myapplication.Constants.mNotificationMessage = mNotificationMessage;
    }

    public static String getmLatitude() {
        return mLatitude;
    }

    public static void setmLatitude(String mLatitude) {
        com.example.myapplication.Constants.mLatitude = mLatitude;
    }

    public static String getmLongitude() {
        return mLongitude;
    }

    public static void setmLongitude(String mLongitude) {
        com.example.myapplication.Constants.mLongitude = mLongitude;
    }
}
