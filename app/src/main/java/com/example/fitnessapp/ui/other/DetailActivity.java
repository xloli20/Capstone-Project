package com.example.fitnessapp.ui.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;
import com.example.fitnessapp.models.WorkoutModel;
import com.example.fitnessapp.utilites.AppExecutors;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    private final static String TAG = DetailActivity.class.getSimpleName();

    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;

    private TextView mInstructionTextView;
    private CollapsingToolbarLayout mAppBarLayout;
    private ImageView mFavorite;
    private Toolbar toolbar;


    private AppDatabase mDB;
    private WorkoutModel mWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mInstructionTextView = findViewById(R.id.instruction);
        mPlayerView = findViewById(R.id.playerView);
        mAppBarLayout = findViewById(R.id.toolbar_layout);

        mDB = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            mWorkout = savedInstanceState.getParcelable("step");
            mInstructionTextView.setText(mWorkout.getWorkoutInstructions());
            mAppBarLayout.setTitle(mWorkout.getWorkoutName());
            initializePlayer(Uri.parse(mWorkout.getWorkoutVideo()));
            mSimpleExoPlayer.seekTo(savedInstanceState.getLong("video_position"));
        } else {
            Intent intent = getIntent();
            mWorkout = intent.getParcelableExtra("workout");

            mInstructionTextView.setText(mWorkout.getWorkoutInstructions());
            mSimpleExoPlayer = initializePlayer(Uri.parse(mWorkout.getWorkoutVideo()));

            if (mAppBarLayout != null) {
                mAppBarLayout.setTitle(mWorkout.getWorkoutName());
            }
            mFavorite = findViewById(R.id.star);
            mFavorite.setOnClickListener(view -> {
                //change the star color
                mFavorite.setImageResource(R.drawable.ic_star_fill);

                //add movie data to the DB
                String WorkoutName = mWorkout.getWorkoutName();
                String workoutImage = mWorkout.getWorkoutImages();
                String workoutInstruction = mWorkout.getWorkoutInstructions();

                final FavoritesWorkouts favoritesWorkouts = new
                        FavoritesWorkouts(WorkoutName, workoutImage, workoutInstruction);

                AppExecutors.getInstance().diskIO().execute(() -> {
                    mDB.favoritesWorkoutDao().insertWorkout(favoritesWorkouts);
                    Log.d(TAG, "onClick: mWorkout inserted ");
                });
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", mWorkout);
        outState.putLong("video_position", mSimpleExoPlayer.getCurrentPosition());
    }

    private SimpleExoPlayer initializePlayer(Uri mediaUri) {
        if (mSimpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);
            // Produces DataSource instances through which media data is loaded.
            String userAgent = Util.getUserAgent(this, "FitnessApp");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
            // This is the MediaSource representing the media to be played.
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
        return mSimpleExoPlayer;
    }

    private void releasePlayer() {
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
