package com.example.fitnessapp.ui.Workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessapp.JsonUtil;
import com.example.fitnessapp.R;
import com.example.fitnessapp.WorkoutModel;
import com.example.fitnessapp.ui.DetailActivity;
import com.example.workoutslib.WorkoutsData;

import org.json.JSONException;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutFragment extends Fragment implements WorkoutAdapter.ListItemClickListener {

    private WorkoutViewModel workoutViewModel;
    WorkoutsData workoutsData = new WorkoutsData();
    private String workout = workoutsData.getWorkout();
    private ArrayList<WorkoutModel> workouts;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workoutViewModel =
                ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.recycler_view_workouts);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        try {
            workouts = JsonUtil.parseWorkoutJson(workout);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setWorkoutAdapter();


        workoutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
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