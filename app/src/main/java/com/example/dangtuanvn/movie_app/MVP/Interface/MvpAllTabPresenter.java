package com.example.dangtuanvn.movie_app.MVP.Interface;

import com.example.dangtuanvn.movie_app.MVP.View.MvpAllTabView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by sinhhx on 12/7/16.
 */
public interface MvpAllTabPresenter extends MvpPresenter<MvpAllTabView> {
    void getMoviesInfo();
}
