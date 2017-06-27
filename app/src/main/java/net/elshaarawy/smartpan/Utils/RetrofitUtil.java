package net.elshaarawy.smartpan.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class RetrofitUtil {
    public static final Retrofit getRetro (String baseURI){
        //to handle connection time out
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit;
    }
    public interface URIs{
        String AUTH_URI = " http://www.smartpan.com.sa:5551/AndriodAPI/";
        String COUNTRIES_URI="https://restcountries.eu/rest/v1/";
    }
}
