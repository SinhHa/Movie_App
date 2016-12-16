package com.example.dangtuanvn.movie_app.MVP.View;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.dangtuanvn.movie_app.adapter.MovieTabAdapter;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by sinhhx on 12/7/16.
 */
public interface MvpAllTabView extends MvpView {
    void setUpMovies( List<?> data);
    void setRefresh(boolean refresh);
    void setUpNews( List<?> data);
    void setOnItemTouch(final List<?> list);
}
