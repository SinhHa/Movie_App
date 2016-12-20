package com.example.dangtuanvn.movie_app.MVP;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.MVP.Interface.NewsTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.NewsTabPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.NewsTabView;
import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.model.News;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public class NewsTabMvp extends MvpFragment<NewsTabView, NewsTabPresenter> implements NewsTabView {
    @Override
    public NewsTabPresenter createPresenter() {
        return new NewsTabPresenterImp(getContext());
    }

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private boolean hasTouch = false;

    public static NewsTabMvp newInstance() {
        Bundle args = new Bundle();
        NewsTabMvp fragment = new NewsTabMvp();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
        return view;
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.movie_tab_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        /* Use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView */
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void setUpNews(List<?> data) {
        List<News> news = (List<News>) data;
        NewsTabAdapter mAdapter = new NewsTabAdapter(getContext(),news);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setRefresh(boolean refresh) {
        swipeLayout.setRefreshing(refresh);
    }


    @Override
    public void setOnItemTouch(final List<?> datalist) {
        if (!hasTouch) {
            final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
            RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {
                @Override

                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    // Check for network connection
                    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && mGestureDetector.onTouchEvent(e)) {
                            List<News> list;
                            list = (List<News>) datalist;
                            Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                            intent.putExtra("data", list.get(0).getShortContent());
                            getContext().startActivity(intent);
                        }


                    } else {
                        Intent intent = new Intent(getContext(), NoInternetActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getContext().startActivity(intent);
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            };
            mRecyclerView.addOnItemTouchListener(listener);
            hasTouch = true;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().getNewsInfo();
        swipeLayout.setOnRefreshListener(creatOnRefreshListener());
    }


    public SwipeRefreshLayout.OnRefreshListener creatOnRefreshListener() {
        SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Handler handlerFDS = new Handler();
                handlerFDS.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPresenter().getNewsInfo();
                        setRefresh(false);
                    }
                }, 500);

            }
        };
        return refreshListener;
    }


}

