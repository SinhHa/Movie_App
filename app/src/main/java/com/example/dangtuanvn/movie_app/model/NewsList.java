package com.example.dangtuanvn.movie_app.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sinhhx on 12/27/16.
 */
public class NewsList  implements Serializable {
   @SerializedName("result")
   public List<News> news;
}
