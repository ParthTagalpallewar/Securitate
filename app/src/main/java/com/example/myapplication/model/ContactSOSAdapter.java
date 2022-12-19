package com.example.myapplication.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.SosActivity;
import com.example.myapplication.data.MyDbHandler;

import java.util.ArrayList;

public class ContactSOSAdapter extends ArrayAdapter<Contact> {

    MyDbHandler db;
    Context ct;
    private static int isD = 0;

    public ContactSOSAdapter(Context context, ArrayList<Contact> contacts)
    {
        super(context,0,contacts);
        ct= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        db = new MyDbHandler(getContext());

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        // Get the {@link Contact} object located at this position in the list
        Contact currentContact = getItem(position);

        // Get the components object in list_item xml file
        TextView contact_name = listItemView.findViewById(R.id.contact_name);
        TextView contact_number = listItemView.findViewById(R.id.contact_number);

        // Set the text to the list_item componentes from current {@contact} object
        contact_name.setText(currentContact.getName());
        contact_number.setText(currentContact.getPhoneNumber());

        TextView deleteBtn = listItemView.findViewById(R.id.deleteItem);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteSosContact(currentContact.getName());
                refreshActivity();
            }
        });
        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in list view
        return listItemView;
    }
    public void refreshActivity(){
        Intent intent = new Intent(ct, SosActivity.class);
        ct.startActivity(intent);
    }

}
