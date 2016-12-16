package com.example.dangtuanvn.movie_app.MVP.Interface;

import com.example.dangtuanvn.movie_app.MVP.View.MainView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by sinhhx on 12/7/16.
 */
public interface MainPresenter extends MvpPresenter<MainView> {
    void getPagerContent();
}
