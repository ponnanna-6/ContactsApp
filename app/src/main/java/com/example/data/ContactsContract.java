package com.example.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ContactsContract {
    public static abstract class ContactsEntry implements BaseColumns{

/*TABLE INFO*/
        /*Table name*/
        public static final String TABLE_NAME = "contacts_list";

        /*<1> Column for contacts id*/
        public static final String _ID = BaseColumns._ID;

        /*<2> Column for contact names*/
        public static final String COLUMN_CONTACT_NAME = "name";

        /*<3> Column for contacts phone number*/
        public static final String COLUMN_PHONE_NUMBER = "number";

        /*<4> Column for contacts age*/
        public static final String COLUMN_CONTACT_AGE = "age";

        /*<5> Column for contacts gender*/
        public static final String COLUMN_CONTACT_GENDER = "gender";

        /*<6> Column for contacts email address*/
        public static final String COLUMN_CONTACT_EMAIL = "email";

        /*<7> Column for contacts city*/
        public static final String COLUMN_CONTACT_CITY = "city";

        /*<8> Column for contacts college name*/
        public static final String COLUMN_CONTACT_COLLEGE = "college";

        /*Options for <5> Column for contacts gender*/
        public static final String GENDER_UNKNOWN = "UNKNOWN";
        public static final String GENDER_MALE = "MALE";
        public static final String GENDER_FEMALE = "FEMALE";

        /*Complete table projection*/
        public static final String[] PROJECTION_ALL = {_ID, COLUMN_CONTACT_NAME, COLUMN_PHONE_NUMBER,
        COLUMN_CONTACT_AGE, COLUMN_CONTACT_GENDER, COLUMN_CONTACT_EMAIL, COLUMN_CONTACT_CITY,
        COLUMN_CONTACT_COLLEGE};

/*TABLE INFO*/

/*URI INFO*/

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
/*URI INFO*/

    }

/*URI INFO*/
    public static final String CONTENT_AUTHORITY = "com.example.contacts";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

/*URI INFO*/
}
