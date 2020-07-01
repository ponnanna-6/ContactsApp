package com.example.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "contacts_database.db";

    private static final int DATABASE_VERSION = 1;


    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + ContactsContract.ContactsEntry.TABLE_NAME + " ("
            + ContactsContract.ContactsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL ,"
            + ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER + " TEXT NOT NULL ,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_AGE + " INTEGER DEFAULT 0,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_GENDER + " TEXT,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_EMAIL + " TEXT,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_CITY + " TEXT,"
            + ContactsContract.ContactsEntry.COLUMN_CONTACT_COLLEGE + " TEXT);";


    public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
