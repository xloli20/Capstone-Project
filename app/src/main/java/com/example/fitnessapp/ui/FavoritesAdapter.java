package com.example.fitnessapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<FavoritesWorkouts> mFavorites;

    private Context context;

    FavoritesAdapter(Context context) {
        this.context = context;
    }
    public FavoritesAdapter(Context context, List<FavoritesWorkouts> favoritesWorkouts) {
        this.context = context;
        mFavorites = favoritesWorkouts;
    }

    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorites_items, viewGroup, false);
        return new FavoritesAdapter.FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        favoritesViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        if (mFavorites == null) {
            return 0;
        }
        return mFavorites.size();
    }

    void setmFavorites(List<FavoritesWorkouts> favoritesWorkouts) {
        mFavorites = favoritesWorkouts;
        notifyDataSetChanged();
    }

    List<FavoritesWorkouts> getTasks() {
        return mFavorites;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textViewInstruction;

        FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.workout_image);
            textView = itemView.findViewById(R.id.workout_title);
            textViewInstruction = itemView.findViewById(R.id.workout_instruction);
        }

        void bind(int position) {
            FavoritesWorkouts favoritesWorkouts = mFavorites.get(position);

            Glide.with(itemView)
                    .load(favoritesWorkouts.getWorkoutImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_setting)
                    .into(imageView);
            textView.setText(favoritesWorkouts.getWorkoutName());
            textViewInstruction.setText(favoritesWorkouts.getWorkoutInstruction());
        }
    }
}