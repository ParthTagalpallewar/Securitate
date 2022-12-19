package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class new_user_registration extends AppCompatActivity {

    EditText mDateFormat;
    //DatePickerDialog
    DatePickerDialog.OnDateSetListener onDateSetListener;

    EditText newName,newMail,newNumber,userPass,newusername;
    AutoCompleteTextView newGen,newBloodGrp,newProfession;
    TextInputLayout newDOB;
    Button sign_up_btn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_registration);


        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.auto_1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.myarray));
        textView.setAdapter(adapter);


        //set spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(spinner.getSelectedItem().toString());
                textView.dismissDropDown();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText(spinner.getSelectedItem().toString());
                textView.dismissDropDown();
            }
        });

        //Blood Group Spinner
        final AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.auto_2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.blood_group));
        textView2.setAdapter(adapter2);
        //set spinner
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner_2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView2.setText(spinner2.getSelectedItem().toString());
                textView2.dismissDropDown();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView2.setText(spinner2.getSelectedItem().toString());
                textView2.dismissDropDown();
            }
        });

        //Profession Spinner
        final AutoCompleteTextView textView3 = (AutoCompleteTextView) findViewById(R.id.auto_3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.profession));
        textView3.setAdapter(adapter3);
        //set spinner
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner_3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView3.setText(spinner3.getSelectedItem().toString());
                textView3.dismissDropDown();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView3.setText(spinner3.getSelectedItem().toString());
                textView3.dismissDropDown();
            }
        });

        //Date Picker

        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateFormat);

        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(new_user_registration.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                String date = i2+"/"+i1+"/"+i;
                mDateFormat.setText(date);
            }
        };

        final TextView tf2 = (TextView) findViewById(R.id.already_have_btn);
        tf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(new_user_registration.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        newName = findViewById(R.id.user_name);
        newusername = findViewById(R.id.user_username);
        newMail = findViewById(R.id.user_mail);
        userPass = findViewById(R.id.user_pass);
        newNumber = findViewById(R.id.user_number);
        newGen = findViewById(R.id.auto_1);
        newBloodGrp = findViewById(R.id.auto_2);
        newProfession = findViewById(R.id.auto_3);
        newDOB = findViewById(R.id.dateTIL);
        sign_up_btn = findViewById(R.id.sign_up);



        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");





                //Getting all the values from components
                String name = newName.getText().toString();
                String mail = newMail.getText().toString();
                String username = newusername.getText().toString();
                String password = userPass.getText().toString();
                String number = newNumber.getText().toString();
                String gender = newGen.getText().toString();
                String blood_grp = newBloodGrp.getText().toString();
                String profession = newProfession.getText().toString();
                String dob = mDateFormat.getText().toString();


                if (!name.isEmpty()) {
                    if (!mail.isEmpty()){
                        if (!username.isEmpty()) {
                            if (!password.isEmpty()) {
                                if (!number.isEmpty()) {
                                    if (!dob.isEmpty()) {

                                        UserHelperClass userHelperClass = new UserHelperClass(name, mail,username, password, gender, blood_grp, profession, dob, number);
                                        reference.child(username).setValue(userHelperClass);
                                        Toast.makeText(new_user_registration.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                                        Intent myintent2 = new Intent(new_user_registration.this,MainActivity.class);
                                        startActivity(myintent2);
                                    } else {
                                        mDateFormat.setError("Field can not be empty");
                                    }
                                } else {
                                    newNumber.setError("Number required");
                                }
                            }
                            else{
                                userPass.setError("Password required");
                            }
                        }
                        else {
                            newusername.setError("Username required");
                        }

                    }
                    else{
                        newMail.setError("Mail Id required");
                    }

                }
                else {
                    newName.setError("Username required");
                }
            }
        });

    }
}
