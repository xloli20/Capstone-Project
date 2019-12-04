package com.example.fitnessapp;


import android.app.Application;
import android.util.Log;

import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoritesWorkouts>> favorites;

    public MainViewModel(Application application) {
        super(application);
        // use the loadAllTasks of the taskDao to initialize the favorites variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorites movies from the DataBase");
        favorites = database.favoritesWorkoutDao().getAll();
    }

    // a getter for the favorites variable
    public LiveData<List<FavoritesWorkouts>> getFavorites() {
        return favorites;
    }
}
