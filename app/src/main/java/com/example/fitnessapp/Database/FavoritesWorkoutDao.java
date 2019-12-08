package com.example.fitnessapp.Database;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavoritesWorkoutDao {

    @Query("SELECT * FROM FavoritesWorkouts")
    LiveData<List<FavoritesWorkouts>> getAll();

    @Query("SELECT * FROM FavoritesWorkouts")
    List<FavoritesWorkouts> getAllSimpleList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkout(FavoritesWorkouts favoritesWorkouts);

    @Delete
    void deleteWorkout(FavoritesWorkouts favoritesWorkouts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWorkout(FavoritesWorkouts favoritesWorkouts);
}
