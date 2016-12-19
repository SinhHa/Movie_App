package com.example.dangtuanvn.movie_app.MVP.Interface;

import com.example.dangtuanvn.movie_app.MVP.View.MovieTabView;
import com.example.dangtuanvn.movie_app.MVP.View.MvpAllTabView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface MovieTabPresenter extends MvpPresenter<MovieTabView> {
    void getMoviesInfo();
}
