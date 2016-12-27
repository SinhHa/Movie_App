package com.example.dangtuanvn.movie_app;


import com.example.dangtuanvn.movie_app.model.NewsList;

import retrofit2.Call;
import retrofit2.http.GET;



/**
 * Created by sinhhx on 12/23/16.
 */
public interface RetrofitObject {
    @GET("news/list?type_id=1")
    Call<NewsList> getNewsDetails();
}
