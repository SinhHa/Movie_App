package com.example.dangtuanvn.movie_app.MVP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
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

import com.example.dangtuanvn.movie_app.GridItemCallBack;
import com.example.dangtuanvn.movie_app.MVP.Interface.MovieDetailPresenter;
import com.example.dangtuanvn.movie_app.MVP.Presenter.MovieDetailPresenterImp;
import com.example.dangtuanvn.movie_app.MVP.View.MovieDetailView;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieScheduleAdapter;
import com.example.dangtuanvn.movie_app.adapter.ScheduleExpandableAdapter;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;



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

        return new MovieDetailPresenterImp(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        setUpUI();
        getPresenter().getTrailerPoster(posterUrl);
        getPresenter().getTrailerContent(movieId);
        setUpVideoPlayer();
        getPresenter().getMovieDiscription(movieId);
        setMoreButtononClick();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        SimpleDateFormat f = new SimpleDateFormat("dd-MM");
        if (dateList.size() < 7) {
            for (int i = 0; i < 7; i++) {

                if (i > 0) {
                    dateTime.add(dateTime.DATE, 1);
                }
                dateList.add(i, df.format(dateTime.getTime()));
                timeList.add(i, sdf.format(dateTime.getTime()));
                displayDate.add(i, f.format(dateTime.getTime()));
            }
        }
        final MovieScheduleAdapter movieScheduleAdapter = new MovieScheduleAdapter(this, displayDate, timeList,callback);
        movieSchedule.setAdapter(movieScheduleAdapter);
        movieSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setSelected(true);
                getPresenter().getSchedule(movieId,dateList.get(position));
            }
        });
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
            finish();
            }
        });
        playbtn.setBackgroundResource(R.drawable.bt_play3);
        IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
        length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
        date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_grey, 0, 0, 0);
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
    @Override
    public void setUpTrailer(Uri uri){
        video.setVideoURI(uri);

    }


    @Override
    public void setUpMovieDiscription(List<?> list){
        List<MovieDetail> detailList = (List<MovieDetail>) list;
        movieTitle.setText(detailList.get(0).getFilmName());
        PG.setText(detailList.get(0).getPgRating());
        IMDB.setText(detailList.get(0).getImdbPoint() + " IMDB");
        String duration = detailList.get(0).getDuration() / 60 + "h " + detailList.get(0).getDuration() % 60 + "min";
        length.setText(duration);
        String date_before = detailList.get(0).getPublishDate();
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd MMM yyyy", date_before);
        date.setText(date_after);
        movieDescription.setText("" + detailList.get(0).getDescriptionMobile());
        if (detailList.get(0).getDirectorName() == null) {
            directorName.setText("");
        } else {
            directorName.setText(" " + detailList.get(0).getDirectorName());
        }
        String actor = "";
        for (int i = 0; i < detailList.get(0).getListActors().size(); i++) {
            actor = actor + detailList.get(0).getListActors().get(i);
            if (i != detailList.get(0).getListActors().size() - 1) {
                actor += ", ";
            }
        }
        starName.setText(" " + actor);
    }

    //more button expand and reduce the move description
    public void setMoreButtononClick(){
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieDescription.getMaxLines() == 3) {
                    movieDescription.setMaxLines(Integer.MAX_VALUE);
                    more.setText("Less ");
                } else {
                    movieDescription.setMaxLines(3);
                    more.setText("More ");
                }
            }
        });
    }



    //add listener for playbutton, progress seekbar, on videoview touch
    public void setUpVideoPlayer(){
        progress.setMax(video.getDuration());
        final DecimalFormat formatter = new DecimalFormat("00");
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    // this is when actually seekbar has been seeked to a new position
                    video.seekTo(i);
                    start.setText((formatter.format((video.getCurrentPosition() / 1000) / 60) + ":" + formatter.format(video.getCurrentPosition() / 1000 % 60)));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                duration.setText((formatter.format((video.getDuration() / 1000) / 60) + ":" + formatter.format(video.getDuration() / 1000 % 60)));
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.setBackgroundResource(0);
                video.start();
                playbtn.setVisibility(View.GONE);

                progress.postDelayed(onEverySecond, 1000);

            }
        });
        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (video.isPlaying() == false) {
                    video.setBackgroundResource(0);
                    video.start();
                    playbtn.setVisibility(View.GONE);
                    progress.postDelayed(onEverySecond, 1000);
                    return false;
                }
                if (video.isPlaying() == true) {
                    video.pause();
                }


                return false;
            }

        });
    }


    //runable thread for progress bar, every second the trailer is playing progress bar will move 1
    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {
            progress.setMax(video.getDuration());
            if (progress != null) {
                progress.setProgress(video.getCurrentPosition());
            }
            if (video.isPlaying()) {
                progress.postDelayed(onEverySecond, 1000);
            }
            DecimalFormat formatter = new DecimalFormat("00");
            start.setText((formatter.format((video.getCurrentPosition() / 1000) / 60) + ":" + formatter.format(video.getCurrentPosition() / 1000 % 60)));

        }
    };

    //format date time value to correct format
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
        }

        return outputDate;

    }


    //DisplaySchedule According to the selected date
    @Override
    public void displayScheduleExpandableList(final List<?> list) {
        List<Schedule> scheduleList = (List<Schedule>) list;
        List<String> cinemaGroupListName = new ArrayList<>();
        for (int i = 0; i < scheduleList.size(); i++) {
            cinemaGroupListName.add(scheduleList.get(i).getpCinemaName());
        }

        Set<String> filterSetName = new LinkedHashSet<>(cinemaGroupListName);
        cinemaGroupListName = new ArrayList<>(filterSetName);


        List<ScheduleCinemaGroupList> groupList = new ArrayList<>();
        for (int i = 0; i < cinemaGroupListName.size(); i++) {
            groupList.add(new ScheduleCinemaGroupList(cinemaGroupListName.get(i)));
        }

        for (Schedule schedule : scheduleList) {
            for (int i = 0; i < groupList.size(); i++) {
                if (schedule.getpCinemaName().equals(groupList.get(i).getCinemaName())) {
                    groupList.get(i).addChildObjectList(schedule);
                    break;
                }
            }
        }
        ScheduleExpandableAdapter recyclerExpandableView = new ScheduleExpandableAdapter(this, groupList);
        allSchedule.setAdapter(recyclerExpandableView);
        allSchedule.setLayoutManager(new LinearLayoutManager(this));
    }

    //callback when gridview is populated with data
    GridItemCallBack callback = new GridItemCallBack() {
        @Override
        public void onFirstItemCreate(View convertView) {
            movieSchedule.performItemClick(convertView,0, 0);

        }
    };

}
