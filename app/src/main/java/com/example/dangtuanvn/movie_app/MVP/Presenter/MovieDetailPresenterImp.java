package com.example.dangtuanvn.movie_app.MVP.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.MVP.Interface.MovieDetailPresenter;
import com.example.dangtuanvn.movie_app.MVP.View.MovieDetailView;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
