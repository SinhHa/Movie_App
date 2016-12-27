package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.example.dangtuanvn.movie_app.RetrofitDataStore;
import com.example.dangtuanvn.movie_app.RxJavaDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.example.dangtuanvn.movie_app.model.converter.NewsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class NewsFeedDataStore extends RetrofitDataStore {
    private String url = BASE_URL + "news/list?type_id=1";
//    public NewsFeedDataStore(Context context) {
//        super(context);
//    }

//    @Override
//    protected List<News> handleData(String response) {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
//        List<News> newsList;
//        Type type = new TypeToken<List<News>>() {}.getType();
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(News.class, new NewsDeserializer());
//        Gson gson = gsonBuilder.create();
//        newsList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
//        return newsList;
//    }



    public  Observable<List<News>>getNewsList(){
        Observable observable = super.newGetRouteData();
        return observable;
    }

//    @Override
//    protected String setUrl(){
//        return url;
//    }
}
