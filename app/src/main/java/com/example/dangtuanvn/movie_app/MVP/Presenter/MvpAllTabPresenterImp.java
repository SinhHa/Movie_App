package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;


//import com.example.dangtuanvn.movie_app.MVP.Interface.MovieComponent;
import com.example.dangtuanvn.movie_app.MVP.Interface.DaggerMovieComponent;
import com.example.dangtuanvn.movie_app.MVP.Interface.MovieComponent;
import com.example.dangtuanvn.movie_app.MVP.Interface.MvpAllTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.Module.MovieComponentModule;
import com.example.dangtuanvn.movie_app.MVP.View.MvpAllTabView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import javax.inject.Inject;


//
//import javax.inject.Inject;

/**
 * Created by sinhhx on 12/7/16.
 */
public class MvpAllTabPresenterImp extends MvpNullObjectBasePresenter<MvpAllTabView> implements MvpAllTabPresenter {
    Context context;

    @Inject MovieFeedDataStore movieShowingFDS;
    int mPage;
    boolean hasTouch = false;
    public MvpAllTabPresenterImp(Context context, int mPage){
        this.context =context;
        this.mPage=mPage;
    }
    @Override
    public void getMoviesInfo() {
        MovieComponent component = DaggerMovieComponent.builder()
                .movieComponentModule(new MovieComponentModule(context,mPage))
                .build();
        component.inject(this);
        if(mPage==0) {
            getMoviedata(movieShowingFDS);
        }
        if(mPage==1){
            getMoviedata(movieShowingFDS);
        }
        if(mPage>1){
            NewsFeedDataStore newsFDS  =
                    new NewsFeedDataStore(context);
            getNewsdata(newsFDS);
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


    public void getNewsdata(NewsFeedDataStore newsFDS){
        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                getView().setUpNews(list);


            }
        });
    }



}
