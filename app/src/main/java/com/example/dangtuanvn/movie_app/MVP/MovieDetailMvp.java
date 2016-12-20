package com.example.dangtuanvn.movie_app.MVP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.MVP.Interface.MovieDetailPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MovieDetailPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MovieDetailView;
import com.example.dangtuanvn.movie_app.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sinhhx on 12/19/16.
 */
public class MovieDetailMvp extends MvpActivity<MovieDetailView,MovieDetailPresenter> implements MovieDetailView  {
    private VideoView video;
    private FrameLayout videolayout;
    private Button playbtn;
    private TextView duration;
    private TextView start;
    private SeekBar progress;
    private TextView movieTitle;
    int movieId;
    String posterUrl;
    private TextView PG;
    private TextView IMDB;
    private TextView length;
    private TextView date;
    private TextView movieDescription;
    private TextView directorName;
    private TextView writerName;
    private Button backBtn;
    private TextView starName;
    private RecyclerView allSchedule;
    private Button more;
    private GridView movieSchedule;
    private Calendar dateTime = Calendar.getInstance();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> displayDate = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    @NonNull
    @Override
    public MovieDetailPresenter createPresenter() {

        return new MovieDetailPresenterImp(this) {
        };
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        setUpUI();
        getPresenter().getTrailerPoster(posterUrl);




//        final FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(this, movieId);
//        playbtn.setBackgroundResource(R.drawable.bt_play3);
//
//        final Target mTarget = new Target() {
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                DisplayMetrics metrics = new DisplayMetrics();
//                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                wm.getDefaultDisplay().getMetrics(metrics);
//                int targetWidth = metrics.widthPixels;
//                double aspectRatio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
//                int targetHeight = (int) (targetWidth * aspectRatio);
//                ViewGroup.LayoutParams params = videolayout.getLayoutParams();
//                params.height = targetHeight;
//                params.width = metrics.widthPixels;
//                videolayout.setLayoutParams(params);
//                Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
//                BitmapDrawable ob = new BitmapDrawable(getResources(), result);
//                video.setBackground(ob);
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                Log.d("failed", "failed");
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//
//        // Create a handler with delay of 500 so these code will run after the image has loaded
//
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Picasso.with(MovieDetailActivity.this)
//                        .load(posterUrl)
//                        .into(mTarget);
//                setUpTrailer(movieTrailerFDS);
//
//            }
//        }, 500);
//
//
//
//        IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
//        length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
//        date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_grey, 0, 0, 0);
//
//        FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(this, movieId);
//        movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//            @Override
//            public void onDataRetrievedListener(List<?> list, Exception ex) {
//                List<MovieDetail> detailList = (List<MovieDetail>) list;
//                movieTitle.setText(detailList.get(0).getFilmName());
//                PG.setText(detailList.get(0).getPgRating());
//                IMDB.setText(detailList.get(0).getImdbPoint() + " IMDB");
//                String duration = detailList.get(0).getDuration() / 60 + "h " + detailList.get(0).getDuration() % 60 + "min";
//                length.setText(duration);
//                String date_before = detailList.get(0).getPublishDate();
//                String date_after = formateDateFromstring("yyyy-MM-dd", "dd MMM yyyy", date_before);
//                date.setText(date_after);
//                movieDescription.setText("" + detailList.get(0).getDescriptionMobile());
//                if (detailList.get(0).getDirectorName() == null) {
//                    directorName.setText("");
//                } else {
//                    directorName.setText(" " + detailList.get(0).getDirectorName());
//                }
//                String actor = "";
//                for (int i = 0; i < detailList.get(0).getListActors().size(); i++) {
//                    actor = actor + detailList.get(0).getListActors().get(i);
//                    if (i != detailList.get(0).getListActors().size() - 1) {
//                        actor += ", ";
//                    }
//                }
//                starName.setText(" " + actor);
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (movieDescription.getMaxLines() == 3) {
//                    movieDescription.setMaxLines(Integer.MAX_VALUE);
//                    more.setText("Less ");
//                } else {
//                    movieDescription.setMaxLines(3);
//                    more.setText("More ");
//                }
//            }
//        });
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf = new SimpleDateFormat("EE");
//        SimpleDateFormat f = new SimpleDateFormat("dd-MM");
//        if (dateList.size() < 7) {
//            for (int i = 0; i < 7; i++) {
//
//                if (i > 0) {
//                    dateTime.add(dateTime.DATE, 1);
//                }
//                dateList.add(i, df.format(dateTime.getTime()));
//                timeList.add(i, sdf.format(dateTime.getTime()));
//                displayDate.add(i, f.format(dateTime.getTime()));
//            }
//        }
//
//        final MovieScheduleAdapter movieScheduleAdapter = new MovieScheduleAdapter(this, displayDate, timeList,callback);
//        movieSchedule.setAdapter(movieScheduleAdapter);
//
//        movieSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                view.setSelected(true);
//
//                FeedDataStore scheduleFDS = new ScheduleFeedDataStore(getApplicationContext(), movieId, dateList.get(position));
//                scheduleFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                    @Override
//                    public void onDataRetrievedListener(List<?> list, Exception ex) {
//                        displayScheduleExpandableList((List<Schedule>) list);
//                    }
//                });
//            }
//        });
    }

    public void setUpUI(){
        video = (VideoView) findViewById(R.id.video_view);
        videolayout = (FrameLayout) findViewById(R.id.video_layout);
        playbtn = (Button) findViewById(R.id.play_button);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        PG = (TextView) findViewById(R.id.PG);
        IMDB = (TextView) findViewById(R.id.IMDB);
        length = (TextView) findViewById(R.id.movie_duration);
        date = (TextView) findViewById(R.id.date);
        allSchedule = (RecyclerView) findViewById(R.id.all_schedule_view);
        movieDescription = (TextView) findViewById(R.id.movie_description);
        directorName = (TextView) findViewById(R.id.director_name);
        writerName = (TextView) findViewById(R.id.writer_name);
        starName = (TextView) findViewById(R.id.star_name);
        more = (Button) findViewById(R.id.more);
        movieSchedule = (GridView) findViewById(R.id.movie_schedule);
        backBtn = (Button) findViewById(R.id.back);
        duration = (TextView) findViewById(R.id.duration);
        progress = (SeekBar) findViewById(R.id.progress);
        start = (TextView) findViewById(R.id.current_time);
        movieId = getIntent().getIntExtra("movieId", 0);
        posterUrl = getIntent().getStringExtra("posterUrl");
        backBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.back_btn, 0, 0, 0);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        playbtn.setBackgroundResource(R.drawable.bt_play3);
    }

    @Override
    public void setPoster(Bitmap poster) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int targetWidth = metrics.widthPixels;
        double aspectRatio = (double) poster.getHeight() / (double) poster.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        ViewGroup.LayoutParams params = videolayout.getLayoutParams();
        params.height = targetHeight;
        params.width = metrics.widthPixels;
        videolayout.setLayoutParams(params);
        Bitmap scaledposter = Bitmap.createScaledBitmap(poster, targetWidth, targetHeight, false);
        BitmapDrawable ob = new BitmapDrawable(getResources(), scaledposter);
        video.setBackground(ob);
    }
}
