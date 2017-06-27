package net.elshaarawy.smartpan.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.smartpan.Adapters.CountriesAdapter;
import net.elshaarawy.smartpan.Data.Entities.CountryEntity;
import net.elshaarawy.smartpan.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.smartpan.Data.SmartPanContract.ProviderUris.CONTETN_URI_COUNTRIES;
import static net.elshaarawy.smartpan.Data.SmartPanContract.COUNTRIES_COLUMNS;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class CountriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID_COUNTRIES = 1;
    private RecyclerView mCountriesRecyclerView;
    private CountriesAdapter mCountriesAdapter;
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderManager = getLoaderManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countries,container,false);

        mCountriesRecyclerView = (RecyclerView) v.findViewById(R.id.rv_countries);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        mCountriesRecyclerView.setLayoutManager(gridLayoutManager);

        mCountriesAdapter = new CountriesAdapter(new ArrayList<CountryEntity>());
        mCountriesRecyclerView.setAdapter(mCountriesAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoaderManager.initLoader(LOADER_ID_COUNTRIES,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_ID_COUNTRIES:
                return new CursorLoader(getContext(),CONTETN_URI_COUNTRIES,null,null,null,null);
            default:
                throw new IllegalArgumentException("not found implementation for id: "+id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        switch (id){
            case LOADER_ID_COUNTRIES:
                loadCountries(data);
                break;
            default:
                throw new IllegalArgumentException("not found implementation for id: "+id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void loadCountries(Cursor cursor){
        cursor.moveToFirst();
        List<CountryEntity> countryEntities = new ArrayList<>(cursor.getCount());
        CountryEntity countryEntity;
        while (cursor.moveToNext()){
            countryEntity = new CountryEntity(
                    cursor.getString(cursor.getColumnIndex(COUNTRIES_COLUMNS.C_NAME)),
                    cursor.getString(cursor.getColumnIndex(COUNTRIES_COLUMNS.C_ALPHA2CODE)),
                    cursor.getString(cursor.getColumnIndex(COUNTRIES_COLUMNS.C_CAPITAL))
            );
            countryEntities.add(countryEntity);
        }
        mCountriesAdapter.swipData(countryEntities);
    }
}
