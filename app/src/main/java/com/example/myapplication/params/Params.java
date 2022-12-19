package com.example.myapplication.params;

public class Params {
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "contact_db";
    public static final String TABLE_NAME = "contacts_table";

//    Keys of our tables in database
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone_number";

    // Static variable for function state
    public static int fun_state = 0;
    public static int charge_fun_state = 0;
    public static int remot_lock_fun_state = 0;
    public static int remoteLockAccess = 0;

    // Table for contacts in sos functionality
    public static final String SOS_TABLE_NAME = "sos_table";
    public static final String SOS_KEY_ID = "id";
    public static final String SOS_KEY_NAME = "name";
    public static final String SOS_KEY_PHONE = "phone_number";

    // Table for holding login details
    public static final String LOGIN_TABLE_NAME = "login_table_1";
    public static final String LOGIN_USER_ID = "user_id";
    public static final String LOGIN_PASSWORD = "password";

    // Table for holding login details
    public static final String USER_TABLE_NAME = "user_info_table";
    public static final String USER_BLOOD_GP = "bloog_gp";
    public static final String USER_DOB= "dob";
    public static final String USER_GENDER= "user_gender";
    public static final String USER_MAIL= "user_mail";
    public static final String USER_PHONE= "user_phone";
    public static final String USER_PROFESSION= "user_profession";
    public static final String USER_NAME= "name";



}
