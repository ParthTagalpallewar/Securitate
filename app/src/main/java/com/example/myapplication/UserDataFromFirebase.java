package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDataFromFirebase {

    //String name1,usermail,username,userpass,usergender,userbloodgroup,userprofession,userdob,userphoneno;

    DatabaseReference reference;
    public String[] retrieve_data(DataSnapshot db)
    {
        String[] str= new String[9];
        //str[0] = "Rahul";

        reference = FirebaseDatabase.getInstance().getReference("Users");

        String user_name = String.valueOf(db.child("name1").getValue());
        String user_blood = String.valueOf(db.child("userbloodgroup").getValue());
        String user_dob = String.valueOf(db.child("userdob").getValue());
        String user_gender = String.valueOf(db.child("usergender").getValue());
        String user_mail = String.valueOf(db.child("usermail").getValue());
        String username = String.valueOf(db.child("username").getValue());
        String user_pass = String.valueOf(db.child("userpass").getValue());
        String user_phone_no = String.valueOf(db.child("userphoneno").getValue());
        String user_profession = String.valueOf(db.child("userprofession").getValue());

        str[0] = user_name;
        str[1] = user_blood;
        str[2] = user_dob;
        str[3] = user_gender;
        str[4] = user_mail;
        str[5] = username;
        str[6] = user_pass;
        str[7] = user_phone_no;
        str[8] = user_profession;

//        reference.child(u).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()){
//                    if (task.getResult().exists())
//                    {
//                        DataSnapshot db = task.getResult();
//
//                        String user_name = String.valueOf(db.child("name1").getValue());
//                        String user_blood = String.valueOf(db.child("userbloodgroup").getValue());
//                        String user_dob = String.valueOf(db.child("userdob").getValue());
//                        String user_gender = String.valueOf(db.child("usergender").getValue());
//                        String user_mail = String.valueOf(db.child("usermail").getValue());
//                        String username = String.valueOf(db.child("username").getValue());
//                        String user_pass = String.valueOf(db.child("userpass").getValue());
//                        String user_phone_no = String.valueOf(db.child("userphoneno").getValue());
//                        String user_profession = String.valueOf(db.child("userprofession").getValue());
//
//                        str[0] = username;
//                        str[1] = user_blood;
//                        str[2] = user_dob;
//                        str[3] = user_gender;
//                        str[4] = user_mail;
//                        str[5] = username;
//                        str[6] = user_pass;
//                        str[7] = user_phone_no;
//                        str[8] = user_profession;
//
//                    }
//            }
//        })
        return str;
    }


}
