package com.example.fitnessapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkoutModel implements Parcelable {

    private String workoutName;
    private String workoutVideo;
    private String workoutImages;
    private String workoutInstructions;

    public WorkoutModel(String workoutName, String workoutVideo, String workoutImages, String workoutInstructions) {
        this.workoutName = workoutName;
        this.workoutVideo = workoutVideo;
        this.workoutImages = workoutImages;
        this.workoutInstructions = workoutInstructions;
    }

    private WorkoutModel(Parcel in) {
        workoutName = in.readString();
        workoutVideo = in.readString();
        workoutImages = in.readString();
        workoutInstructions = in.readString();
    }

    public static final Creator<WorkoutModel> CREATOR = new Creator<WorkoutModel>() {
        @Override
        public WorkoutModel createFromParcel(Parcel in) {
            return new WorkoutModel(in);
        }

        @Override
        public WorkoutModel[] newArray(int size) {
            return new WorkoutModel[size];
        }
    };

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutVideo() {
        return workoutVideo;
    }

    public void setWorkoutVideo(String workoutVideo) {
        this.workoutVideo = workoutVideo;
    }

    public String getWorkoutImages() {
        return workoutImages;
    }

    public void setWorkoutImages(String workoutImages) {
        this.workoutImages = workoutImages;
    }

    public String getWorkoutInstructions() {
        return workoutInstructions;
    }

    public void setWorkoutInstructions(String workoutInstructions) {
        this.workoutInstructions = workoutInstructions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workoutName);
        parcel.writeString(workoutVideo);
        parcel.writeString(workoutImages);
        parcel.writeString(workoutInstructions);
    }
}
