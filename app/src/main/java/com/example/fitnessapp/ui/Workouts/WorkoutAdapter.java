package com.example.fitnessapp.ui.Workouts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.WorkoutModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private final static String TAG = WorkoutAdapter.class.getSimpleName();

    private int viewHolderCount;
    private Context context;
    private ArrayList<WorkoutModel> workouts;
    final private ListItemClickListener mOnClickListener;

    WorkoutAdapter(Context context, ArrayList<WorkoutModel> workouts, ListItemClickListener listener) {
        this.context = context;
        this.workouts = workouts;
        this.mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_items, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        Context context;

        WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.workout_image);
            textView = itemView.findViewById(R.id.workout_title);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Log.d(TAG, "bind: " + workouts.get(position).getWorkoutImages());
            Glide.with(itemView)
                    .load(workouts.get(position).getWorkoutImages())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(imageView);
            textView.setText(workouts.get(position).getWorkoutName());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
