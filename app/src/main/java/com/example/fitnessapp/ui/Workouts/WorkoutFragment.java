package com.example.fitnessapp.ui.Workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.models.WorkoutModel;
import com.example.fitnessapp.ui.other.DetailActivity;
import com.example.fitnessapp.utilites.JsonUtil;
import com.example.workoutslib.WorkoutsData;

import org.json.JSONException;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutFragment extends Fragment implements WorkoutAdapter.ListItemClickListener {

    private WorkoutsData workoutsData = new WorkoutsData();
    private String workout = workoutsData.getWorkout();
    private ArrayList<WorkoutModel> workouts;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_workouts);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        try {
            workouts = JsonUtil.parseWorkoutJson(workout);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setWorkoutAdapter();

//        AdView mAdView = (AdView) root.findViewById(R.id.adView);
//        // Create an ad request. Check logcat output for the hashed device ID to
//        // get test ads on a physical device. e.g.
//        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        mAdView.loadAd(adRequest);

        return root;
    }

    private void setWorkoutAdapter() {
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(getContext(), workouts, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(workoutAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //intent
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("workout", workouts.get(clickedItemIndex));
        startActivity(intent);
    }

}