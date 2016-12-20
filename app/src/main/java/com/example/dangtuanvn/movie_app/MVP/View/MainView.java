package com.example.dangtuanvn.movie_app.MVP.View;

import com.example.dangtuanvn.movie_app.adapter.TabViewPagerAdapter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by sinhhx on 12/7/16.
 */
public interface MainView extends MvpView {
    void configTablayout();
    void noInternetError();
}
