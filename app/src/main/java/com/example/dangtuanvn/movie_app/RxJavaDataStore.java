package com.example.dangtuanvn.movie_app;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dangtuanvn.movie_app.datastore.RxDataStore;
import com.example.dangtuanvn.movie_app.datastore.SingletonQueue;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by sinhhx on 12/21/16.
 */
public abstract class RxJavaDataStore implements RxDataStore {
    private static String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private static String X123F_VERSION = "3";
    protected static String BASE_URL = "http://mapp.123phim.vn/android/2.97/";
    private Context context;

    public RxJavaDataStore(Context context) {
        this.context = context;
    }

    public Observable<List<?>> newGetRouteData() {
        return Observable.defer(new Func0<Observable<List<?>>>() {
            @Override
            public Observable<List<?>> call() {

                    return Observable.create(new Observable.OnSubscribe<List<?>>() {
                        @Override
                        public void call(final Subscriber<? super List<?>> subscriber) {
                                String url = setUrl();
                                final StringRequest stringRequest = new StringRequest
                                        (Request.Method.GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(!subscriber.isUnsubscribed()){
                                                    subscriber.onNext(handleData(response));
                                                    subscriber.onCompleted();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                subscriber.onError(error);
                                            }
                                        }) {

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();


                                        long timestamp = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
                                        String accessToken = hashMd5(X123F_TOKEN + timestamp) + " " + timestamp;

                                        params.put("X-123F-Version", X123F_VERSION);
                                        params.put("X-123F-Token", accessToken);

                                        return params;
                                    }
                                };

                                SingletonQueue.getInstance(context).addRequest(stringRequest);

                        }
                    });
            }
        });
    }
    // http://stackoverflow.com/questions/4846484/md5-hashing-in-android
    private String hashMd5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected List<?> handleData(String response){
        return null;
    }
    protected List<?> handleData(String response,int id){
        return null;
    }

    protected String setUrl(){
        return BASE_URL;
    }
}

