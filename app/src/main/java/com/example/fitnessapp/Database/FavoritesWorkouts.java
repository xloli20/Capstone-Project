package com.example.fitnessapp.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoritesWorkouts {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "WORKOUT_NAME")
    private
    String workoutName;

    @ColumnInfo(name = "WORKOUT_IMAGE")
    private
    String workoutImage;

    @ColumnInfo(name = "WORKOUT_INSTRUCTION")
    private
    String workoutInstruction;

    public FavoritesWorkouts(String workoutName, String workoutImage, String workoutInstruction) {
        this.workoutName = workoutName;
        this.workoutImage = workoutImage;
        this.workoutInstruction = workoutInstruction;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutImage() {
        return workoutImage;
    }

    public void setWorkoutImage(String workoutImage) {
        this.workoutImage = workoutImage;
    }

    public String getWorkoutInstruction() {
        return workoutInstruction;
    }

    public void setWorkoutInstruction(String workoutInstruction) {
        this.workoutInstruction = workoutInstruction;
    }
}
