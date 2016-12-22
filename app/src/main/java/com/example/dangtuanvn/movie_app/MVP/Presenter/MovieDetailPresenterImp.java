package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import com.example.dangtuanvn.movie_app.MVP.Interface.MovieDetailPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.MovieDetailView;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.ScheduleFeedDataStore;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import rx.Observer;

/**
 * Created by sinhhx on 12/19/16.
 */
public class MovieDetailPresenterImp extends MvpNullObjectBasePresenter<MovieDetailView> implements MovieDetailPresenter {
    Context context;
    public MovieDetailPresenterImp(Context context){
        this.context= context;
    }
    @Override
    public void getTrailerPoster(String posterUrl) {
    new setTrailerposter().execute(posterUrl);
    }

    @Override
    public void getTrailerContent(int movieId) {
         FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(context, movieId);
        movieTrailerFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                List<MovieTrailer> movieTrailer = (List<MovieTrailer>) list;
                try {
                    Uri uri = Uri.parse(movieTrailer.get(0).getV720p());
                    getView().setUpTrailer(uri);
                } catch (NullPointerException e) {
                    // TODO fix url null
                }
            }
        });
    }

    @Override
    public void getMovieDiscription(int movieId) {
       MovieDetailFeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(context, movieId);
        movieDetailFDS.newGetRouteData().subscribe(new Observer<List<?>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<?> list) {
                getView().setUpMovieDiscription(list);
            }
        });
    }

    @Override
    public void getSchedule(int movieId, String position) {
        FeedDataStore scheduleFDS = new ScheduleFeedDataStore(context, movieId, position);
        scheduleFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
            getView().displayScheduleExpandableList(list);
            }
        });
    }



    //download userposter from url after finish call view to set image to videoview
    private class setTrailerposter extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap poster = getBitmapFromURL(params[0]);
            return poster;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
        getView().setPoster(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    //download the bitmap from url
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
