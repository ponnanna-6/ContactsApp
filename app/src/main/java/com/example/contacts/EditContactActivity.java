package com.example.contacts;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.data.ContactsContract;
import com.example.data.ContactsDbHelper;

public class EditContactActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {


    private EditText mNameEditText;

    private EditText mPhoneNumberEditText;

    private EditText mAgeEditText;

    private RadioGroup mGenderRadioGroup;
    private RadioButton mGenderRadioButton;
    private RadioButton mMaleButton;
    private RadioButton mFemaleButton;
    private RadioButton mUnknownButton;

    private EditText mEmailEditText;

    private EditText mCityEditText;

    private EditText mCollegeEditText;

    private static Uri mContactUri;

    private static final int EXISTING_CONTACTS = 10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        mContactUri = getIntent().getData();

        if(mContactUri == null){
            this.setTitle("Add contact");
        }else{
            this.setTitle("Edit Contact");
            getSupportLoaderManager().initLoader(EXISTING_CONTACTS, null, this);
        }

        mNameEditText = findViewById(R.id.name_id);
        mPhoneNumberEditText = findViewById(R.id.phone_number_id);
        mAgeEditText = findViewById(R.id.age_id);
        mGenderRadioGroup = findViewById(R.id.radio_group_gender);
        mMaleButton = findViewById(R.id.male_gender_id);
        mFemaleButton = findViewById(R.id.female_gender_id);
        mUnknownButton = findViewById(R.id.unknown_gender_id);
        mEmailEditText = findViewById(R.id.mail_id);
        mCityEditText = findViewById(R.id.city_id);
        mCollegeEditText = findViewById(R.id.college_id);
    }

    public void saveContact(){
        mGenderRadioButton = findViewById(mGenderRadioGroup.getCheckedRadioButtonId());

        String nameString =  mNameEditText.getText().toString().trim();
        String phoneNumberString =  mPhoneNumberEditText.getText().toString().trim();
        String ageString =  mAgeEditText.getText().toString();
        String genderString = mGenderRadioButton.getText().toString().trim();
        String emailString = mEmailEditText.getText().toString().trim();
        String cityString  = mCityEditText.getText().toString().trim();
        String collegeString = mCollegeEditText.getText().toString().trim();




        ContentValues values = new ContentValues();
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME,nameString);
        values.put(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER, phoneNumberString);
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_AGE, ageString);
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_GENDER, genderString);
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_EMAIL, emailString);
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_CITY, cityString);
        values.put(ContactsContract.ContactsEntry.COLUMN_CONTACT_COLLEGE, collegeString);


        Uri newUri = getContentResolver().insert(ContactsContract.ContactsEntry.CONTENT_URI, values);
        if(newUri == null){
            Toast.makeText(this, "Error saving contact" , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Contact inserted with id:"+ ContentUris.parseId(newUri), Toast.LENGTH_SHORT).show();
        }


    }

    /*Creating options menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    /*Handling options menu*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:
                /*action on clicking save*/
                saveContact();
                finish();
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
        return new CursorLoader(this, mContactUri,
                ContactsContract.ContactsEntry.PROJECTION_ALL, null, null,
                null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){

            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
            int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER);
            int ageColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_AGE);
            int genderColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_GENDER);
            int emailColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_EMAIL);
            int cityColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_CITY);
            int collegeColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_COLLEGE);

            String name = cursor.getString(nameColumnIndex);
            String phoneNumber = cursor.getString(phoneNumberColumnIndex);
            String age = cursor.getString(ageColumnIndex);
            String gender = cursor.getString(genderColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String city = cursor.getString(cityColumnIndex);
            String college = cursor.getString(collegeColumnIndex);


            mNameEditText.setText(name);
            mPhoneNumberEditText.setText(phoneNumber);
            mAgeEditText.setText(age);


            mEmailEditText.setText(email);
            mCityEditText.setText(city);
            mCollegeEditText.setText(college);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

/*    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, contactUri,
                ContactsContract.ContactsEntry.PROJECTION_ALL, null, null,
                ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){

            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
            int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER);
            int ageColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_AGE);
            int genderColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_GENDER);
            int emailColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_EMAIL);
            int cityColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_CITY);
            int collegeColumnIndex = cursor.getColumnIndex(ContactsContract.ContactsEntry.COLUMN_CONTACT_COLLEGE);

            String name = cursor.getString(nameColumnIndex);
            String phoneNumber = cursor.getString(phoneNumberColumnIndex);
            String age = cursor.getString(ageColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String city = cursor.getString(cityColumnIndex);
            String college = cursor.getString(collegeColumnIndex);


            mNameEditText.setText(name);
            mPhoneNumberEditText.setText(phoneNumber);
            mAgeEditText.setText(age);
            *//*Gender*//*
            mGenderEditText.setSelected(true);
            mEmailEditText.setText(email);
            mCityEditText.setText(city);
            mCollegeEditText.setText(college);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNameEditText.getText().clear();
        mPhoneNumberEditText.getText().clear();
        mAgeEditText.getText().clear();
        mEmailEditText.getText().clear();
        mCityEditText.getText().clear();
        mCollegeEditText.getText().clear();

    }*/
}
