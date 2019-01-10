package com.example.pagedlistdemo;

import android.util.Log;

import com.example.pagedlistdemo.model.Item;
import com.example.pagedlistdemo.model.StackApiResponse;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {
    public static final int PAGE_SIZE = 20;
    private static final int PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {
        Log.d("TAG", "loadInitial:size " + params.requestedLoadSize);
        RetrofitClient.getInstance().getApi().getAns(PAGE, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackApiResponse>() {

            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                if (response.body() != null) {
                    Log.d("TAG", "onResponse: " + PAGE);
                    callback.onResult(response.body().items, null, PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        Log.d("TAG", "loadBefore: " + params.key);
        RetrofitClient.getInstance().getApi().getAns(params.key, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                int adjKey = params.key > 1 ? params.key - 1 : null;
                if (response.body() != null) {
                    Log.d("TAG", "onResponse: " + params.key);
                    callback.onResult(response.body().items, adjKey);
                    Log.d("TAG", "onResponse: " + adjKey);
                }
            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        Log.d("TAG", "loadAfter: " + params.key);
        RetrofitClient.getInstance().getApi().getAns(params.key, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                if (response.body() != null) {
                    Log.d("TAG", "onResponse: " + params.key);
                    Integer key = (response.body().has_more) ? params.key + 1 : null;

                    Log.d("TAG", "onResponse: " + key + "next data" + response.body().has_more);
                    callback.onResult(response.body().items, key);
                }
            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });
    }
}
