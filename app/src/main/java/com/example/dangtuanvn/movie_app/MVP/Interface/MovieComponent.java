package com.example.dangtuanvn.movie_app.MVP.Interface;



import com.example.dangtuanvn.movie_app.MVP.Module.MovieComponentModule;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MovieTabPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MvpAllTabPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MvpAllTabView;

import dagger.Component;

/**
 * Created by sinhhx on 12/8/16.
 */

@Component(modules = MovieComponentModule.class)
public interface MovieComponent {
    void inject(MvpAllTabPresenterImp MvpAllTabPresenterImp);
}
