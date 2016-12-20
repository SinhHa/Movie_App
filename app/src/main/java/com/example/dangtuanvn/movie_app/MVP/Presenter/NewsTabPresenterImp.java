package com.example.dangtuanvn.movie_app.MVP.Presenter;


import android.content.Context;

import com.example.dangtuanvn.movie_app.MVP.Interface.NewsTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.NewsTabView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public class NewsTabPresenterImp extends MvpNullObjectBasePresenter<NewsTabView> implements NewsTabPresenter {
    Context context;
    public NewsTabPresenterImp(Context context){
        this.context =context;
    }
    @Override
    public void getNewsInfo() {
        NewsFeedDataStore newsFDS  =
                new NewsFeedDataStore(context);
        getNewsdata(newsFDS);
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
