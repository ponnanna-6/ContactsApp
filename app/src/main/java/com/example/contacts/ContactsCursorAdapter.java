package com.example.contacts;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.data.ContactsContract;

public class ContactsCursorAdapter extends CursorAdapter {


    public ContactsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if(view == null){
            return;
        }

        TextView nameView = view.findViewById(R.id.name_list_view);
        TextView phoneNumberView = view.findViewById(R.id.phone_number_list_view);
        TextView cityView = view.findViewById(R.id.city_list_view);
        ImageView genderView = view.findViewById(R.id.gender_list_view);
        LinearLayout cityLinearLayout = view.findViewById(R.id.city_list_view_linear_layout);


        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
        int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER);
        int cityColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_CITY);
        int genderColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_GENDER);


        String name =cursor.getString(nameColumnIndex).trim();
        String phoneNumber = cursor.getString(phoneNumberColumnIndex).trim();
        String city = cursor.getString(cityColumnIndex).trim();
        String gender =cursor.getString(genderColumnIndex).trim();

        nameView.setText(name);
        phoneNumberView.setText(phoneNumber);

        if(gender == "Unknown"){
            genderView.setImageResource(R.drawable.gender_unknown);
        }else if(gender == "Male"){
            genderView.setImageResource(R.drawable.gender_male);
        }else{
            genderView.setImageResource(R.drawable.gender_female);

        }

        Log.v("CITYDETAIYLS", city);

        if(city == null){
            cityLinearLayout.setVisibility(View.INVISIBLE);
        }else{
            cityView.setText(city);
        }


    }

}
