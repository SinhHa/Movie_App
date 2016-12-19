package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;
import com.example.dangtuanvn.movie_app.MVP.Interface.MovieTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.MovieTabView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by sinhhx on 12/19/16.
 */
public class MovieTabPresenterImp extends MvpNullObjectBasePresenter<MovieTabView> implements MovieTabPresenter {
    Context context;

    MovieFeedDataStore movieShowingFDS;
    int mPage;
    public MovieTabPresenterImp(Context context, int mPage){
        this.context =context;
        this.mPage=mPage;
    }
    @Override
    public void getMoviesInfo() {
        if(mPage==0) {
            movieShowingFDS = new MovieFeedDataStore(context, MovieFeedDataStore.DataType.SHOWING);
            getMoviedata(movieShowingFDS);
        }
        if(mPage==1){
            movieShowingFDS = new MovieFeedDataStore(context, MovieFeedDataStore.DataType.UPCOMING);
            getMoviedata(movieShowingFDS);
        }

    }


    public void getMoviedata(MovieFeedDataStore movieShowingFDS){
        movieShowingFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {

                getView().setUpMovies(list);
                getView().setOnItemTouch(list);
            }
        });
    }



}

