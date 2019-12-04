package com.example.fitnessapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.AppExecutors;
import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;
import com.example.fitnessapp.WorkoutModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private final static String TAG = DetailActivity.class.getSimpleName();

    private Context context;
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

    private TextView instructionTextView;
    private CollapsingToolbarLayout appBarLayout;

    private AppDatabase mDB;
    private ImageView mFavorite;

    private WorkoutModel workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        instructionTextView = findViewById(R.id.instruction);
        playerView = findViewById(R.id.playerView);
        appBarLayout = findViewById(R.id.toolbar_layout);

        mDB = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            workout = savedInstanceState.getParcelable("step");
            instructionTextView.setText(workout.getWorkoutInstructions());
            appBarLayout.setTitle(workout.getWorkoutName());
            initializePlayer(Uri.parse(workout.getWorkoutVideo()));
            simpleExoPlayer.seekTo(savedInstanceState.getLong("video_position"));

        } else {
            Intent intent = getIntent();
            workout = intent.getParcelableExtra("workout");

            instructionTextView.setText(workout.getWorkoutInstructions());
            initializePlayer(Uri.parse(workout.getWorkoutVideo()));

            if (appBarLayout != null) {
                appBarLayout.setTitle(workout.getWorkoutName());
            }
            mFavorite = findViewById(R.id.star);
            mFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //change the star color
                    mFavorite.setImageResource(R.drawable.ic_star_fill);

                    //add movie data to the DB
                    String WorkoutName = workout.getWorkoutName();
                    String workoutImage = workout.getWorkoutImages();
                    String workoutInstruction = workout.getWorkoutInstructions();

                    final FavoritesWorkouts favoritesWorkouts = new
                            FavoritesWorkouts(WorkoutName, workoutImage, workoutInstruction);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDB.favoritesWorkoutDao().insertWorkout(favoritesWorkouts);
                            Log.d(TAG, "onClick: workout inserted ");
                            finish();
                        }
                    });
                }
            });

        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", workout);
        outState.putLong("video_position", simpleExoPlayer.getCurrentPosition());
    }

    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            // Bind the player to the view.
            playerView.setPlayer(simpleExoPlayer);
            // Produces DataSource instances through which media data is loaded.
            String userAgent = Util.getUserAgent(this, "FitnessApp");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
            // This is the MediaSource representing the media to be played.
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            // Prepare the player with the source.
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
