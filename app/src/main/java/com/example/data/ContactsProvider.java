package com.example.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactsProvider extends ContentProvider {

    private ContactsDbHelper mDbHelper;

    public static final String LOG_TAG = ContactsProvider.class.getSimpleName();

    /*URI ids*/
    private static final int CONTACT = 100;
    private static final int CONTACT_ID = 101;

    private static final UriMatcher sUriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ContactsContract.CONTENT_AUTHORITY,
                ContactsContract.ContactsEntry.TABLE_NAME, CONTACT);

        sUriMatcher.addURI(ContactsContract.CONTENT_AUTHORITY,
                ContactsContract.ContactsEntry.TABLE_NAME + "/#", CONTACT);

    }


    @Override
    public boolean onCreate() {
        mDbHelper = new ContactsDbHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case CONTACT:
                cursor = database.query(ContactsContract.ContactsEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACT_ID:
                selection = ContactsContract.ContactsEntry.TABLE_NAME + "=?";
                selectionArgs = new String[] {String.valueOf (ContentUris.parseId(uri)) };
                cursor = database.query(ContactsContract.ContactsEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("incorrect uri");

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }



    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (sUriMatcher.match(uri)) {
            case CONTACT:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        // TODO: Insert a new pet into the pets database table with the given ContentValues
        String name = values.getAsString(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
        if(name == null){
            throw new IllegalArgumentException("Name required");
        }

        String phoneNumber = values.getAsString(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER);
        if(phoneNumber == null){
            throw new IllegalArgumentException("Phone number required");
        }




        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ContactsContract.ContactsEntry.TABLE_NAME, null, values);

        if(id == -1){
            Log.e(LOG_TAG, "failed to insert pet");
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CONTACT_ID:
                selection = ContactsContract.ContactsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int rowsDeleted =  database.delete(ContactsContract.ContactsEntry.TABLE_NAME, selection, selectionArgs);
                if(rowsDeleted != 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {

        switch (sUriMatcher.match(uri)) {
            case CONTACT:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case CONTACT_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ContactsContract.ContactsEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported");
        }
    }


    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME)) {
            String name = values.getAsString(ContactsContract.ContactsEntry.COLUMN_CONTACT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Contact requires a name");
            }
        }

        if (values.containsKey(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER)) {
            String phoneNumber  = values.getAsString(ContactsContract.ContactsEntry.COLUMN_PHONE_NUMBER);
            if (phoneNumber == null) {
                throw new IllegalArgumentException("Contact requires phone number");
            }
        }


        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(ContactsContract.ContactsEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACT:
                return ContactsContract.ContactsEntry.CONTENT_LIST_TYPE;
            case CONTACT_ID:
                return ContactsContract.ContactsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI No match");
        }
    }



}
