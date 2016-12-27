package com.example.dangtuanvn.movie_app.MVP.View;

import com.example.dangtuanvn.movie_app.model.News;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface NewsTabView  extends MvpView {
    void setUpNews( List<News> data);
    void setRefresh(boolean refresh);
}
