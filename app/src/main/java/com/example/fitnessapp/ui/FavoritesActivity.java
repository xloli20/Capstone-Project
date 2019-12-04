package com.example.fitnessapp.ui;

import android.os.Bundle;
import android.util.Log;

import com.example.fitnessapp.AppExecutors;
import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.MainViewModel;
import com.example.fitnessapp.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {
    private final static String TAG = FavoritesActivity.class.getSimpleName();

    private FavoritesAdapter favoritesAdapter;
    private RecyclerView fRecyclerView;

    //DB object
    private AppDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        fRecyclerView = findViewById(R.id.recycler_view_workouts);

        //setting the favorites movies recycler view
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(mLayoutManager);
        fRecyclerView.setHasFixedSize(true);
        favoritesAdapter = new FavoritesAdapter(this);
        fRecyclerView.setAdapter(favoritesAdapter);


        //DB initialization
        mDB = AppDatabase.getInstance(getApplicationContext());

        //
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // get the position from the viewHolder parameter
                        int position = viewHolder.getAdapterPosition();
                        List<FavoritesWorkouts> favorites = favoritesAdapter.getTasks();
                        //Call deleteTask in the taskDao with the task at that position
                        mDB.favoritesWorkoutDao().deleteWorkout(favorites.get(position));
                    }
                });
            }
        }).attachToRecyclerView(fRecyclerView);

        setupViewModel();
    }


    private void setupViewModel() {
        // Declare a ViewModel variable and initialize it by calling ViewModelProviders.of
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // Observe the LiveData object in the ViewModel
        viewModel.getFavorites().observe(this, new Observer<List<FavoritesWorkouts>>() {
            @Override
            public void onChanged(@Nullable List<FavoritesWorkouts> favoritesWorkouts) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                favoritesAdapter.setmFavorites(favoritesWorkouts);
            }
        });
    }
}
