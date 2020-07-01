package com.example.contacts;



import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.data.ContactsContract;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER = 0;

    ContactsCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Setup Floating action button to open EditorActivity*/
        FloatingActionButton fab = findViewById(R.id.fab_id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fabIntent = new Intent(MainActivity.this,EditContactActivity.class);
                startActivity(fabIntent);
            }
        });

        ListView listView = findViewById(R.id.contacts_list_view);

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mCursorAdapter = new ContactsCursorAdapter(this, null);
        listView.setAdapter(mCursorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intentToViewer = new Intent(MainActivity.this, ViewContactActivity.class);

                Uri contactUri = ContentUris.withAppendedId(ContactsContract.ContactsEntry.CONTENT_URI, id);
                intentToViewer.setData(contactUri);

                startActivity(intentToViewer);

            }
        });
        getSupportLoaderManager().initLoader(LOADER, null, this);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        return new CursorLoader(this, ContactsContract.ContactsEntry.CONTENT_URI,
                ContactsContract.ContactsEntry.PROJECTION_ALL, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }



}
