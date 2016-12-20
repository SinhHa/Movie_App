package com.example.dangtuanvn.movie_app.MVP.View;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface MovieTabView extends MvpView {
    void setUpMovies( List<?> data);
    void setRefresh(boolean refresh);
}
