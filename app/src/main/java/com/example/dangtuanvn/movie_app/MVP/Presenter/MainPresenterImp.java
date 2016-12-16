package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;

import com.example.dangtuanvn.movie_app.MVP.Interface.MainPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.MainView;
import com.example.dangtuanvn.movie_app.adapter.TabViewPagerAdapter;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

/**
 * Created by sinhhx on 12/7/16.
 */
public class MainPresenterImp extends MvpNullObjectBasePresenter<MainView> implements MainPresenter {
    ConnectivityManager connectivityManager;
    FragmentManager supportFragmentManager;
    Context context;
    public MainPresenterImp(ConnectivityManager connectivityManager, FragmentManager supportFragmentManager, Context context){
        this.connectivityManager = connectivityManager;
        this.supportFragmentManager = supportFragmentManager;
        this.context =context;
    }
    @Override
    public void getPagerContent() {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getView().setupUI();
          TabViewPagerAdapter adapter=  new TabViewPagerAdapter(supportFragmentManager, context);
            getView().setupViewPager(adapter);
            getView().configTablayout();
    }
        else{
            getView().noInternetError();
        }

}
}
