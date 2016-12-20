package com.example.dangtuanvn.movie_app.MVP.View;

import android.graphics.Bitmap;
import android.net.Uri;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface MovieDetailView extends MvpView {
    void setPoster(Bitmap poster);
    void setUpTrailer(Uri uri);
    void setUpMovieDiscription(List<?> list);
    void displayScheduleExpandableList(final List<?> list);
}
