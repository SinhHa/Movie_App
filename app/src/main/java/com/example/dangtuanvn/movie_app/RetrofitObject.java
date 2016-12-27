package com.example.dangtuanvn.movie_app;

import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsList;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by sinhhx on 12/23/16.
 */
public interface RetrofitObject {
    @GET("news/list?type_id=1")
    Call<NewsList> getNewsDetails();
}
