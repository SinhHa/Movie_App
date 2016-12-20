package com.example.dangtuanvn.movie_app.MVP.Interface;

import com.example.dangtuanvn.movie_app.MVP.View.MovieTabView;
import com.example.dangtuanvn.movie_app.MVP.View.NewsTabView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface NewsTabPresenter extends MvpPresenter<NewsTabView> {
    void getNewsInfo();
}
