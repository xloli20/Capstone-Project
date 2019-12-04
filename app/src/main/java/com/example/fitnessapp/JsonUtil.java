package com.example.fitnessapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtil {
    private static final String TAG = JsonUtil.class.getSimpleName();

    public static ArrayList<WorkoutModel> parseWorkoutJson(String json) throws JSONException {

        JSONObject workouts = new JSONObject(convertStandardJSONString(json));

        Log.d(TAG, "parseWorkoutJson: " + workouts);
        JSONArray results = workouts.getJSONArray("workouts");
        Log.d(TAG, "parseWorkoutJson: " + results);

        ArrayList<WorkoutModel> workoutsArrayList = new ArrayList<>();

        if (results.length() != 0) {
            for (int i = 0; i <= results.length() - 1; i++) {
                JSONObject workoutData = results.getJSONObject(i);
                WorkoutModel workout = new WorkoutModel(
                        workoutData.optString("name"),
                        workoutData.optString("video"),
                        workoutData.optString("image"),
                        workoutData.optString("instruction")
                );
                workoutsArrayList.add(workout);
            }
        }
        return workoutsArrayList;
    }

    private static String convertStandardJSONString(String data_json) {
        data_json = data_json.replace("\\", "");
        return data_json;
    }

}
