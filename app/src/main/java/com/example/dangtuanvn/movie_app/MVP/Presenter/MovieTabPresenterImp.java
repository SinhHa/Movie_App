package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;
import com.example.dangtuanvn.movie_app.MVP.Interface.MovieTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.MovieTabView;
import com.example.dangtuanvn.movie_app.RxJavaDataStore;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.RxDataStore;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

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


    public void getMoviedata(RxDataStore movieShowingFDS){
        movieShowingFDS.newGetRouteData().subscribe(new Observer<List<?>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<?> list) {
            getView().setUpMovies(list);
            }
        });
    }



}

