package com.example.pagedlistdemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private static RetrofitClient mRetrofitClient;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mRetrofitClient == null) {
            mRetrofitClient = new RetrofitClient();
        }
        return mRetrofitClient;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
