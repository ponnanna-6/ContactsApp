package com.example.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.data.ContactsContract;

public class ViewContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView mNameView;
    private TextView mPhoneNumberView;
    private TextView mAgeView;
    private RadioGroup mGenderView;
    private TextView mEmailView;
    private TextView mCityView;
    private TextView mCollegeView;
    private Uri contactsUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        /*call the number*/
        ImageView callButton = findViewById(R.id.call_icon_in_view_page);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + "9353611626"));
                startActivity(callIntent);
            }
        });

        /*Mail to input mail id*/
        final ImageView mailButton = findViewById(R.id.email_icon_in_view_page);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","ponnanna2001@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });

        mNameView = findViewById(R.id.name_in_view_page);
        mPhoneNumberView = findViewById(R.id.phone_number_in_view_page);
        mAgeView = findViewById(R.id.age_in_view_page);
        mGenderView = findViewById(R.id.radio_group_gender);
        mEmailView = findViewById(R.id.email_in_view_page);
        mCityView = findViewById(R.id.city_in_view_page);
        mCollegeView = findViewById(R.id.college_in_view_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_edit:
                /*action on clicking edit icon*/
                Intent editIntent = new Intent(this, EditContactActivity.class);
                startActivity(editIntent);
                return true;

            case R.id.action_save:
                /*action on clicking save*/
                return true;

            case R.id.action_delete:
                /*action on clicking delete*/
                return true;

            case R.id.action_share:
                /*action on clicking share*/
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, contactsUri,
                ContactsContract.ContactsEntry.PROJECTION_ALL,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
