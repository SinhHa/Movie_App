package com.example.dangtuanvn.movie_app.MVP;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.dangtuanvn.movie_app.MVP.Interface.MainPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MainPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MainView;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.TabViewPagerAdapter;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

/**
 * Created by sinhhx on 12/7/16.
 */
public class MainActivityMVP extends MvpActivity<MainView,MainPresenter> implements MainView {
    ViewPager viewPager;
    TabLayout tabLayout;
    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};
    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImp((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE), getSupportFragmentManager(),this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresenter().getPagerContent();
        getPresenter().attachView(this);
    }

    @Override
    public void setupUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void setupViewPager(TabViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @Override
    public void configTablayout() {
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 4; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }

    @Override
    public void noInternetError() {
        Intent intent = new Intent(this, NoInternetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView(false);
    }




}
