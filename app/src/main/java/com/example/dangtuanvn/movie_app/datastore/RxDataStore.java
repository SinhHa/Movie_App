package com.example.dangtuanvn.movie_app.datastore;

import java.util.List;

import rx.Observable;

/**
 * Created by sinhhx on 12/22/16.
 */
public interface RxDataStore {
    Observable<List<?>> newGetRouteData();
}