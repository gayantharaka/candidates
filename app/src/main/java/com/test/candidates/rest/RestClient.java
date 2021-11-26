package com.test.candidates.rest;

import android.content.Context;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestClient {

    private final String API_BASE_URL;

    public RestClient(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }

    public OkHttpClient createOkHttpClient(Context context)
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.d("OkHttp" ,message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        return  httpClient.build();
    }

    private Retrofit createRetrofit(Context context) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(createOkHttpClient(context));

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }

    public ClientRx getClientRx(Context context)
    {
        ClientRx clientRx = createRetrofit(context).create(ClientRx.class);
        return  clientRx;
    }

}
