package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.model.Contact;
import com.example.myapplication.model.UserData;
import com.example.myapplication.params.Params;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {


    // Sharing data to base class's constructor
    public MyDbHandler(Context context){
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating table for call me back activity
        String createCallMeBack = "CREATE TABLE " + Params.TABLE_NAME + "(" + Params.KEY_ID + " INTEGER PRIMARY KEY," +
                        Params.KEY_NAME + " TEXT, " + Params.KEY_PHONE + " TEXT" + ")";
        db.execSQL(createCallMeBack);

        // Creating table for sos activity
        String createSos = "CREATE TABLE " + Params.SOS_TABLE_NAME + "(" + Params.SOS_KEY_ID + " INTEGER PRIMARY KEY," +
                Params.SOS_KEY_NAME + " TEXT, " + Params.SOS_KEY_PHONE + " TEXT" + ")";
        db.execSQL(createSos);

        // Creating table for holding login details
        String createLoginQuery = "CREATE TABLE " + Params.LOGIN_TABLE_NAME + "(" + Params.LOGIN_USER_ID + " TEXT, " +
                Params.LOGIN_PASSWORD + " TEXT " + ")" ;
        Log.d("dbname","Query is " + createLoginQuery);
        db.execSQL(createLoginQuery);


        // Creating SQLite table for user creating details
        String createUserQuery = "CREATE TABLE " +
                Params.USER_TABLE_NAME +
                "(" + Params.USER_NAME + " TEXT, " +
                Params.USER_BLOOD_GP + " TEXT " +
                Params.USER_DOB + " TEXT " +
                Params.USER_GENDER + " TEXT " +
                Params.USER_MAIL + " TEXT " +
                Params.USER_PROFESSION + " TEXT " +
                Params.USER_PHONE + " TEXT " +
                ")" ;
        Log.d("dbname","Query is " + createLoginQuery);
        db.execSQL(createLoginQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Method to add the contact in db
    public void addContact(Contact contact){

        // Create object of db for writing purpose
        SQLiteDatabase db = this.getWritableDatabase();

        // Object of ContentValues
        ContentValues contentValues = new ContentValues();
        // Inserting values at specific columns in database using object of ContentValues
        contentValues.put(Params.KEY_NAME,contact.getName());
        contentValues.put(Params.KEY_PHONE,contact.getPhoneNumber());

        db.insert(Params.TABLE_NAME,null, contentValues);

        // Closing connection with database
        db.close();
    }

    // Method to add the contact in db
    public void addSosContact(Contact contact){

        // Create object of db for writing purpose
        SQLiteDatabase db = this.getWritableDatabase();

        // Object of ContentValues
        ContentValues contentValues = new ContentValues();
        // Inserting values at specific columns in database using object of ContentValues
        contentValues.put(Params.SOS_KEY_NAME,contact.getName());
        contentValues.put(Params.SOS_KEY_PHONE,contact.getPhoneNumber());

        db.insert(Params.SOS_TABLE_NAME,null, contentValues);

        // Closing connection with database
        db.close();
    }

    // Method to read the contact from the db
    public ArrayList<Contact> readContact(){
        ArrayList<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the qeury to read from the database
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        // Creating Cursor's object to walk through in database
        Cursor cursor = db.rawQuery(query,null);

        // Walk through now
        if(cursor.moveToFirst()){ // Take cursor at first row
            do {
                Contact contact = new Contact();
                // Fetching data from database and adding it to contacts object
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

    // Method to read the contact from the db
    public ArrayList<Contact> readSosContact(){
        ArrayList<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the qeury to read from the database
        String query = "SELECT * FROM " + Params.SOS_TABLE_NAME;
        // Creating Cursor's object to walk through in database
        Cursor cursor = db.rawQuery(query,null);

        // Walk through now
        if(cursor.moveToFirst()){ // Take cursor at first row
            do {
                Contact contact = new Contact();
                // Fetching data from database and adding it to contacts object
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

    public boolean isPhoneNumberPresent(String phoneNum){
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the qeury to read from the database
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        // Creating Cursor's object to walk through in database
        Cursor cursor = db.rawQuery(query,null);

        // Walk through now
        if(cursor.moveToFirst()){ // Take cursor at first row
            do {
                String phoneNumber = cursor.getString(2);
                Log.e("pn","dbPhone = " + phoneNumber + "|| callPhone = " + phoneNum);
                if(phoneNum.equalsIgnoreCase(phoneNumber))
                    return true;
            }while(cursor.moveToNext());
        }
        return false;
    }

    // Method to update the contact in db
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.KEY_NAME,contact.getName());
        contentValues.put(Params.KEY_PHONE,contact.getPhoneNumber());

        // Updating the contact
        /**
         * METHOD : db.update()
         * Param 1 : (String) TABLE_NAME
         * Param 2 : Object of ContentValues
         * Param 3 : String (Where Clause)
         * Param 4 : String array (Clause Arguments)
         * Returns : Number of rows affected (INT)
         * */
        return db.update(Params.TABLE_NAME, contentValues, Params.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
    }

    // Method to Delete the contact with id
    public void deleteContact(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Params.TABLE_NAME, Params.KEY_NAME + "=?", new String[]{String.valueOf(name)});
        db.close();
    }

    public void deleteSosContact(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.SOS_TABLE_NAME, Params.KEY_NAME + "=?", new String[]{String.valueOf(name)});
        db.close();
    }

    /** Method to get count of contacts in db */
    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    /** Method to get count of sos contacts in db */
    public int getSosCount(){
        String query = "SELECT * FROM " + Params.SOS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    /** Method which adds login information to db to avoid repeated login*/
    public void addLogin(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.LOGIN_USER_ID,user);
        contentValues.put(Params.LOGIN_PASSWORD,pass);
        db.insert(Params.LOGIN_TABLE_NAME,null, contentValues);
        db.close();
    }

    public void addArpitaLogin(String user, String pass, String profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.LOGIN_USER_ID,user);
        contentValues.put(Params.LOGIN_PASSWORD,pass);
        db.insert(Params.LOGIN_TABLE_NAME,null, contentValues);
        db.close();
    }

    public void addUserObject(UserData userData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String name = userData.getName();
        String bg = userData.getBlood();
        String dob = userData.getDob();
        String gender = userData.getGender();
        String mail = userData.getMail();
        String phone = userData.getPhone();
        String profession = userData.getProfession();

        contentValues.put(Params.USER_NAME,name);
        contentValues.put(Params.USER_BLOOD_GP,bg);
        contentValues.put(Params.USER_DOB,dob);
        contentValues.put(Params.USER_GENDER,gender);
        contentValues.put(Params.USER_MAIL,mail);
        contentValues.put(Params.USER_PHONE,phone);
        contentValues.put(Params.USER_PROFESSION,profession);
        db.insert(Params.USER_TABLE_NAME,null, contentValues);
        db.close();
    }

    /** Method return true if any data is present in login table */
    public boolean isDataPresent(){
        SQLiteDatabase db = this.getReadableDatabase();
        // Generate the qeury to read from the database
        String query = "SELECT * FROM " + Params.LOGIN_TABLE_NAME;
        // Creating Cursor's object to walk through in database
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){ // Take cursor at first row
            do {
                return true;
            }while(cursor.moveToNext());
        }
        return false;
    }
}
