package com.example.dangtuanvn.movie_app.MVP.Presenter;


import android.content.Context;

import com.example.dangtuanvn.movie_app.MVP.Interface.NewsTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.NewsTabView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.RxDataStore;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import rx.Observer;

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
    public void getNewsdata(RxDataStore newsFDS){
      newsFDS.newGetRouteData().subscribe(new Observer<List<?>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(List<?> list) {

              getView().setUpNews(list);
          }
      });
    }

}
