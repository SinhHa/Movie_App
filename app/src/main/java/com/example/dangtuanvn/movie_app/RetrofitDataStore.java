package com.example.dangtuanvn.movie_app;



import android.os.Bundle;

import android.util.Log;

import com.example.dangtuanvn.movie_app.datastore.RxDataStore;
import com.example.dangtuanvn.movie_app.model.NewsList;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;


/**
 * Created by sinhhx on 12/23/16.
 */
public class RetrofitDataStore implements RxDataStore {
    private static String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private static String X123F_VERSION = "3";
    protected static String BASE_URL = "http://mapp.123phim.vn/android/2.97/";

    public Observable<List<?>> newGetRouteData() {
        return Observable.defer(new Func0<Observable<List<?>>>() {
            @Override
            public Observable<List<?>> call() {

                return Observable.create(new Observable.OnSubscribe<List<?>>() {
                    @Override
                    public void call(final Subscriber<? super List<?>> subscriber) {
                        OkHttpClient client = setUpHeader();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client)
                                .build();
                        RetrofitObject service = retrofit.create(RetrofitObject.class);
                        Call<NewsList> call = service.getNewsDetails();
                        call.enqueue(new Callback<NewsList>() {
                            @Override
                            public void onResponse(Response<NewsList> response) {
                                Log.d("retro",response.toString());
                                if(!subscriber.isUnsubscribed()){
                                    subscriber.onNext(response.body().news);
                                    subscriber.onCompleted();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                subscriber.onError(t);
                            }
                        });
                    }
                });
            }
        });
    }

    public OkHttpClient setUpHeader(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Map<String, String> params = new HashMap<>();
                long timestamp = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
                String accessToken = hashMd5(X123F_TOKEN + timestamp) + " " + timestamp;
                // Request customization: add request headers





                params.put("X-123F-Version", X123F_VERSION);
                params.put("X-123F-Token", accessToken);


                Request request = original.newBuilder()
                        .header("X-123F-Version", X123F_VERSION)
                        .header("X-123F-Token", accessToken)
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        return client;
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
}
