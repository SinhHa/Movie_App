package com.example.dangtuanvn.movie_app.MVP.Module;

import android.content.Context;

import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sinhhx on 12/8/16.
 */

@Module
public class MovieComponentModule {
    private Context context;
    int type;
    public MovieComponentModule(Context context, int type){
     this.context =context;
     this.type=type;
    }
    @Provides
    public MovieFeedDataStore provideMovieFeedDataStore(){
        if(type==0){
        return new MovieFeedDataStore(context, MovieFeedDataStore.DataType.SHOWING);}
        else{
            return new MovieFeedDataStore(context, MovieFeedDataStore.DataType.UPCOMING);
        }
    }

}
