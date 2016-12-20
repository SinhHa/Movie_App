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

import com.example.dangtuanvn.movie_app.MVP.Interface.MovieTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MovieTabPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MovieTabView;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieTabAdapter;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public class MovieTabMvp extends MvpFragment<MovieTabView, MovieTabPresenter> implements MovieTabView {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private boolean hasTouch = false;
    int mPage;

    @Override
    public MovieTabPresenter createPresenter() {
        return new MovieTabPresenterImp(getContext(),mPage);
    }

    public static MovieTabMvp newInstance(int mPage) {
        Bundle args = new Bundle();
        args.putInt("cinema_tab", mPage);
        MovieTabMvp fragment = new MovieTabMvp();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("cinema_tab");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
        presenter = createPresenter();
       presenter.attachView(this);
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
    public void setUpMovies(final List<?> data) {
        List<Movie> movie = (List<Movie>) data;
        MovieTabAdapter mAdapter = new MovieTabAdapter(getContext(), movie, mPage);
        mRecyclerView.setAdapter(mAdapter);
        setOnClickListener(data);

    }

    public void setOnClickListener(final List<?> data){
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
                            List<Movie> list;
                            list = (List<Movie>) data;
                            Intent intent = new Intent(getContext(), MovieDetailMvp.class);
                            intent.putExtra("movieId", list.get(rv.getChildAdapterPosition(childView)).getFilmId());
                            intent.putExtra("posterUrl", list.get(rv.getChildAdapterPosition(childView)).getPosterLandscape());
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
    public void setRefresh(boolean refresh) {
        swipeLayout.setRefreshing(refresh);
    }


    @Override
    public void onStart() {
        super.onStart();
        swipeLayout.setOnRefreshListener(creatOnRefreshListener());
        getPresenter().getMoviesInfo();
   }


    public SwipeRefreshLayout.OnRefreshListener creatOnRefreshListener() {
        SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Handler handlerFDS = new Handler();
                handlerFDS.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPresenter().getMoviesInfo();
                        setRefresh(false);
                    }
                }, 500);

            }
        };
        return refreshListener;
    }


}
