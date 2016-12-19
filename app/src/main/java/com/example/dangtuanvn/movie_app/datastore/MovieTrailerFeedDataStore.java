package com.example.dangtuanvn.movie_app.datastore;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.example.dangtuanvn.movie_app.model.converter.MovieTrailerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieTrailerFeedDataStore extends DataStore {
    private String url = BASE_URL + "film/trailer?film_id=";
    private int movieId;
    Context context;
    public MovieTrailerFeedDataStore(Context context, int movieId) {
        super(context);
        this.context =context;
        this.movieId = movieId;
    }

    @Override
    protected List<MovieTrailer> handleData(String response) {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
            MovieTrailer movieTrailer;
            Type type = new TypeToken<MovieTrailer>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(MovieTrailer.class, new MovieTrailerDeserializer());
            Gson gson = gsonBuilder.create();
            movieTrailer = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);

            return Collections.singletonList(movieTrailer);
        }
        catch (Exception e){
            messageBox("Retrieve data fail",e.getMessage());
            return null;
        }
    }
    private void messageBox(String method, String message)
    {
        Log.d("EXCEPTION: " + method,  message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }
    @Override
    protected String setUrl(){
        return url + movieId;
    }
}
