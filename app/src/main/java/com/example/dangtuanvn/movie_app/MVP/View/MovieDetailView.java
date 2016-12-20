package com.example.dangtuanvn.movie_app.MVP.View;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by sinhhx on 12/19/16.
 */
public interface MovieDetailView extends MvpView {
    void setPoster(Bitmap poster);
}
