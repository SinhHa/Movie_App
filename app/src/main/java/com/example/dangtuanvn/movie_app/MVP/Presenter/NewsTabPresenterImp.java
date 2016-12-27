package com.example.dangtuanvn.movie_app.MVP.Presenter;


import android.content.Context;

import com.example.dangtuanvn.movie_app.MVP.Interface.NewsTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.NewsTabView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.RxDataStore;
import com.example.dangtuanvn.movie_app.model.News;
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
        NewsFeedDataStore newsFDS  = new NewsFeedDataStore();
        getNewsdata(newsFDS);
    }
    public void getNewsdata(NewsFeedDataStore newsFDS){
      newsFDS.getNewsList().subscribe(new Observer<List<News>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(List<News> list) {

              getView().setUpNews(list);
          }
      });
    }

}
