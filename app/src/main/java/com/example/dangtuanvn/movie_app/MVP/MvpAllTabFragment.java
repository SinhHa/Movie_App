package com.example.dangtuanvn.movie_app.MVP;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dangtuanvn.movie_app.MVP.Interface.MvpAllTabPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MvpAllTabPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MvpAllTabView;
import com.example.dangtuanvn.movie_app.MovieDetailActivity;
import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieTabAdapter;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Polyline;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sinhhx on 12/7/16.
 */
public class MvpAllTabFragment extends MvpFragment<MvpAllTabView, MvpAllTabPresenter> implements MvpAllTabView {

    private enum Tab {
        Showing,
        Upcoming,
        Cinema,
        News
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;

    private int mPage;
    private Tab mTab;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private GoogleMap map;
    private Polyline polyline;
    private SupportMapFragment mapFragment;
    private SwipeRefreshLayout swipeLayout;
    private Handler handlerFDS = new Handler();
    private boolean hasTouch = false;
    private static int frameId = View.generateViewId();
    private static List<Cinema> cinemaList;

    public static MvpAllTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt("frame_id", frameId);
        args.putSerializable("cinema_list", (Serializable) cinemaList);
        MvpAllTabFragment fragment = new MvpAllTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameId = getArguments().getInt("frame_id");
        mPage = getArguments().getInt(ARG_PAGE);
        cinemaList = (List<Cinema>) getArguments().getSerializable("cinema_list");
        mTab = Tab.values()[mPage];

    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        // Check for network connection
//        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        switch (mTab) {
            case Showing:
            case Upcoming:
            case News:
                view = inflateListView(inflater, container);

                break;

            case Cinema:
                view = inflateListView(inflater, container);
                break;


            default:
        }

        return view;
    }

    @NonNull
    @Override
    public MvpAllTabPresenter createPresenter() {
        return new MvpAllTabPresenterImp(getContext(), mPage);
    }

    @Override
    public void setUpMovies(List<?> data) {
        List<Movie> movie = (List<Movie>) data;
        MovieTabAdapter mAdapter = new MovieTabAdapter(getContext(), movie, mPage);
        mRecyclerView.setAdapter(mAdapter);
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
                        if (childView != null && mGestureDetector.onTouchEvent(e) && mPage < 2) {
                            List<Movie> list = (List<Movie>) datalist;
                            list = (List<Movie>) datalist;

                            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                            intent.putExtra("movieId", list.get(rv.getChildAdapterPosition(childView)).getFilmId());
                            intent.putExtra("posterUrl", list.get(rv.getChildAdapterPosition(childView)).getPosterLandscape());

                            getContext().startActivity(intent);
                        }
                        if (childView != null && mGestureDetector.onTouchEvent(e) && mPage == 3) {
                            List<News> list = (List<News>) datalist;
                            if (childView != null && mGestureDetector.onTouchEvent(e)) {
                                FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(getContext(), list.get(rv.getChildAdapterPosition(childView)).getNewsId());
                                newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                                    @Override
                                    public void onDataRetrievedListener(List<?> list, Exception ex) {
                                        // Start web view
                                        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                                        intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());

                                        getContext().startActivity(intent);
                                    }
                                });
                            }
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
        getPresenter().getMoviesInfo();
        swipeLayout.setOnRefreshListener(creatOnRefreshListener());


    }


    @Override
    public void setRefresh(boolean refresh) {
        swipeLayout.setRefreshing(refresh);
    }

    @Override
    public void setUpNews(List<?> data) {
        List<News> movie = (List<News>) data;
        NewsTabAdapter mAdapter = new NewsTabAdapter(getContext(), movie);
        mRecyclerView.setAdapter(mAdapter);
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


