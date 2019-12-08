package com.example.fitnessapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fitnessapp.AppExecutors;
import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.FitnessAppWidget;
import com.example.fitnessapp.MainViewModel;
import com.example.fitnessapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(() -> {
                    // get the position from the viewHolder parameter
                    int position = viewHolder.getAdapterPosition();
                    List<FavoritesWorkouts> favorites = favoritesAdapter.getTasks();
                    //Call deleteTask in the taskDao with the task at that position
                    mDB.favoritesWorkoutDao().deleteWorkout(favorites.get(position));
                });
            }
        }).attachToRecyclerView(fRecyclerView);

        setupViewModel();
    }

    private void setupViewModel() {
        // Declare a ViewModel variable and initialize it by calling ViewModelProviders.of
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // Observe the LiveData object in the ViewModel
        viewModel.getFavorites().observe(this, favoritesWorkouts -> {
            Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
            favoritesAdapter.setmFavorites(favoritesWorkouts);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.widget){
            addWidget();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addWidget() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // Observe the LiveData object in the ViewModel
        viewModel.getFavorites().observe(this, favoritesWorkouts -> {
            Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
            favoritesAdapter.setmFavorites(favoritesWorkouts);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FitnessAppWidget.class));
            if (appWidgetIds.length == 0) {
                Toast.makeText(this, "Please make a home screen widget first!", Toast.LENGTH_SHORT).show();
            } else {
                for (int appWidgetId : appWidgetIds) {
                    FitnessAppWidget.updateAppWidget(this, appWidgetManager, appWidgetId, favoritesWorkouts);
                }
            }
        });

    }
}
