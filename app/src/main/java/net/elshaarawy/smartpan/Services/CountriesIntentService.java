package net.elshaarawy.smartpan.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import net.elshaarawy.smartpan.Activities.LoginActivity;
import net.elshaarawy.smartpan.Data.Entities.CountryEntity;
import net.elshaarawy.smartpan.Data.SmartPanContract;
import net.elshaarawy.smartpan.Utils.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import static android.R.attr.name;
import static net.elshaarawy.smartpan.Activities.LoginActivity.sendBroadCastToMe;
import static net.elshaarawy.smartpan.Utils.RetrofitUtil.getRetro;
import static net.elshaarawy.smartpan.Data.SmartPanContract.ProviderUris;
import static net.elshaarawy.smartpan.Data.SmartPanContract.COUNTRIES_COLUMNS;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class CountriesIntentService extends IntentService implements Callback<List<CountryEntity>> {

    private String name;
    public CountriesIntentService() {
        super("CountriesIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        name = intent.getStringExtra(intent.EXTRA_USER);

        Retrofit retrofit = getRetro(RetrofitUtil.URIs.COUNTRIES_URI);

        Countries countries = retrofit.create(Countries.class);
        Call<List<CountryEntity>> listCall = countries.getCountries();
        listCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<CountryEntity>> call, Response<List<CountryEntity>> response) {

        List<CountryEntity> countryEntities = response.body();
        int listSize = countryEntities.size();

        ContentValues[] values = new ContentValues[listSize];
        ContentValues value;
        CountryEntity countryEntity;

        for (int i = 0; i < listSize; i++) {
            value = new ContentValues();
            countryEntity = countryEntities.get(i);
            value.put(COUNTRIES_COLUMNS.C_NAME, countryEntity.getName());
            value.put(COUNTRIES_COLUMNS.C_ALPHA2CODE, countryEntity.getAlpha2Code());
            value.put(COUNTRIES_COLUMNS.C_CAPITAL, countryEntity.getCapital());
            values[i] = value;
        }

        getContentResolver().delete(ProviderUris.CONTETN_URI_COUNTRIES, "", null);
        getContentResolver().bulkInsert(SmartPanContract.ProviderUris.CONTETN_URI_COUNTRIES, values);
        sendBroadCastToMe(this, true,name);

    }

    @Override
    public void onFailure(Call<List<CountryEntity>> call, Throwable t) {

    }

    private interface Countries {
        @GET("all")
        Call<List<CountryEntity>> getCountries();
    }


    public static void startMe(Context context,String name) {
        Intent intent = new Intent(context, CountriesIntentService.class);
        intent.putExtra(intent.EXTRA_USER,name);
        context.startService(intent);
    }
}
