package net.elshaarawy.smartpan.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.health.ServiceHealthStats;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.elshaarawy.smartpan.Adapters.ContactsAdapter;
import net.elshaarawy.smartpan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID_CONTACTS = 20;
    private RecyclerView mContactsRecyclerView;
    private ContactsAdapter mContactsAdapter;
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderManager = getLoaderManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        mContactsRecyclerView = (RecyclerView) v.findViewById(R.id.content_contacts);
        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactsAdapter = new ContactsAdapter(getContext(), new ArrayList<Pair<String, String>>());
        mContactsRecyclerView.setAdapter(mContactsAdapter);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] permissionArray = new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS};
        int permissionCode = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS);
        if (permissionCode != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( permissionArray, 55);
        }else{
            mLoaderManager.restartLoader(LOADER_ID_CONTACTS, null, this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 55:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLoaderManager.restartLoader(LOADER_ID_CONTACTS, null, this);
                }
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_CONTACTS:
                return new CursorLoader(getContext(),
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            default:
                throw new IllegalArgumentException("not found implementation for id: " + id);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        switch (id) {
            case LOADER_ID_CONTACTS:
                getContacts(data);
                break;
            default:
                throw new IllegalArgumentException("not found implementation for id: " + id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void getContacts(Cursor cursor) {
        List<Pair<String, String>> contactsPairList = new ArrayList<>(cursor.getCount());
        String name,number;
        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactsPairList.add(new Pair<>(name, number));
        }

        mContactsAdapter.swipData(contactsPairList);
    }
}
