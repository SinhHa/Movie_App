package com.example.dangtuanvn.movie_app;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by sinhhx on 11/15/16.
 */
public class MovieDetailActivity extends AppCompatActivity{

    public static final String API ="AIzaSyBzKfqegqROEnxpAy5H9DzXXzWzWNIuqVU";
    public static final String ID="o-ACsZZncQtd14CERv798CahM03qbHLgqUVPi1EnjOBTu4";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
//        YouTubePlayerView videoplayer = (YouTubePlayerView) findViewById(R.id.videoView);
//        videoplayer.initialize(API,this);
        //Creating MediaController
        final MediaController videoMediaController = new MediaController(this,false);
        videoMediaController.setVisibility(View.INVISIBLE);
        int movieId = getIntent().getIntExtra("movieId",0);

        final VideoView video=(VideoView)findViewById(R.id.video_view);
        FrameLayout videolayout =(FrameLayout) findViewById(R.id.video_layout);
        final Button playbtn = (Button) findViewById(R.id.play_button);
        videoMediaController.setAnchorView(videolayout);
        videoMediaController.setMediaPlayer(video);
        video.setMediaController(videoMediaController);
        FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(this,movieId);
        movieTrailerFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
            List<MovieTrailer> movieTrailer = (List<MovieTrailer>) list;
                Uri uri=Uri.parse(movieTrailer.get(0).getV720p());
                video.setVideoURI(uri);
            }
        });
        playbtn.setBackgroundResource(R.drawable.bt_play);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.start();
                videoMediaController.setVisibility(View.VISIBLE);
                playbtn.setVisibility(View.GONE);
            }
        });




        final TextView movieTitle = (TextView)findViewById(R.id.movie_title);
        final TextView PG = (TextView)findViewById(R.id.PG);
        final TextView IMDB = (TextView)findViewById(R.id.IMDB);
        final TextView length = (TextView)findViewById(R.id.movie_duration);
        final TextView date = (TextView)findViewById(R.id.date);
        final TextView movieDescription = (TextView)findViewById(R.id.movie_description);
        final TextView directorName = (TextView)findViewById(R.id.director_name);
        final TextView writerName = (TextView)findViewById(R.id.writer_name);
        final TextView starName = (TextView)findViewById(R.id.star_name);


        IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
        length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
        date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_white, 0, 0, 0);

        FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(this, movieId);
        movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
            List<MovieDetail> detailList = (List<MovieDetail>) list;
                movieTitle.setText(detailList.get(0).getFilmName());
                PG.setText(detailList.get(0).getPgRating());
                IMDB.setText(detailList.get(0).getImdbPoint()+" IMDB");
                length.setText(detailList.get(0).getDuration()+"");
                date.setText(detailList.get(0).getPublishDate());
                movieDescription.setText(" "+detailList.get(0).getDescriptionMobile());
                directorName.setText(" "+detailList.get(0).getDirectorName());
                String actor="";
                for(int i=0;i<(detailList.get(0).getListActors().size());i++){
                actor = actor+ detailList.get(0).getListActors().get(i)+" ";
                }
                starName.setText(" "+actor);

            }
        });
        final Button more = (Button) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( movieDescription.getMaxLines()==3){
                movieDescription.setMaxLines(Integer.MAX_VALUE);
                   more.setText("Less");
               }
                else{
                   movieDescription.setMaxLines(3);
                   more.setText("More");
               }
            }
        });


    }
}
