package com.example.dangtuanvn.movie_app.MVP.Interface;

import com.example.dangtuanvn.movie_app.MVP.View.MovieDetailView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface MovieDetailPresenter extends MvpPresenter<MovieDetailView> {
    void getTrailerPoster(String posterUrl);
    void getTrailerContent(int movieId);
    void getMovieDiscription(int movieId);
    void getSchedule(int movieId,String position);
}
