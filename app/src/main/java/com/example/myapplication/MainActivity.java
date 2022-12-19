package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.MyDbHandler;
import com.example.myapplication.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    DatabaseReference reference;
    EditText et1,et2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.rahul);
        btn1.setBackgroundColor(Color.rgb(252, 141, 32));

        final TextView tf1 = (TextView) findViewById(R.id.new_acc);

        tf1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this,new_user_registration.class);
                startActivity(myIntent);
            }
        });


        et1 = findViewById(R.id.et_username);
        et2 = findViewById(R.id.et_pass);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s1 = et1.getText().toString();
//                String s2 = et2.getText().toString();
//
//                if (!s1.isEmpty())
//                {
//                    if (!s2.isEmpty())
//                    {
//                        readData(s1,s2);
//                    }
//                    else
//                    {
//                        et2.setError("Password required");
//                    }
//                }
//                else {
//                    et1.setError("Username required");
//                }
                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(MainActivity.this,Home_page.class);
                startActivity(myIntent2);
            }
        });


    }
    private void readData(String username,String password)
    {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        DataSnapshot dataSnapshot = task.getResult();
//                        String user = String.valueOf(dataSnapshot.child("username").getValue());
//                        String pass = String.valueOf(dataSnapshot.child("userpass").getValue());
//
//                        Log.d("user :",user.toString());
//                        Log.d("pass",pass.toString());

                        int a = 1; // FOR TESTING
                        if(a == 1){ //if(user.equals(username))
                            if(a == 1){ // if(pass.equals(password))
                                MyDbHandler db = new MyDbHandler(MainActivity.this);
                                db.addLogin(username,password);

                                UserDataFromFirebase userDataFromFirebase = new UserDataFromFirebase();
//                                String[] userData = userDataFromFirebase.retrieve_data(dataSnapshot);

                                UserData userDataObject = new UserData();
//                                userDataObject.setName(userData[0]);
//                                userDataObject.setBlood(userData[1]);

                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                Intent myIntent2 = new Intent(MainActivity.this,Home_page.class);
                                startActivity(myIntent2);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(MainActivity.this,"User not found ! Please Register a new user.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}