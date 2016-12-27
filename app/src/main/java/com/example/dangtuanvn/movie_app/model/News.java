package com.example.dangtuanvn.movie_app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/8/16.
 */

public class News implements Serializable {
    @SerializedName("news_id")
    private int IewsId;
    @SerializedName("news_title")
    private String newsTitle;
    @SerializedName("news_description")
    private String newsDescription;
    @SerializedName("short_content")
    private String shortContent;
    @SerializedName("film_id")
    private int filmId;
    @SerializedName("cinema_id")
    private int pCinemaId;
    private List<Integer> listFilm;
    private String url;
    @SerializedName("date_add")
    private String dateAdd;
    @SerializedName("date_update")
    private String dateUpdate;
    @SerializedName("image_full")
    private String imageFull;
    @SerializedName("image")
    private String imageMedium;
    @SerializedName("image2x")
    private String imageSmall;
    private String timeDifference="10";

    public int getNewsId() {
        return IewsId;
    }

    public void setNewsId(int newsId) {
        this.IewsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getImageFull() {
        return imageFull;
    }

    public void setImageFull(String imageFull) {
        this.imageFull = imageFull;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public int getpCinemaId() {
        return pCinemaId;
    }

    public void setpCinemaId(int pCinemaId) {
        this.pCinemaId = pCinemaId;
    }

    public List<Integer> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<Integer> listFilm) {
        this.listFilm = listFilm;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
}
