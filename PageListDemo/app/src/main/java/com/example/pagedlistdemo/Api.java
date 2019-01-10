package com.example.pagedlistdemo;

import com.example.pagedlistdemo.model.StackApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("answers")
    Call<StackApiResponse> getAns(@Query("page") int page, @Query("pagesize") int pageSize, @Query("site") String site);
}
