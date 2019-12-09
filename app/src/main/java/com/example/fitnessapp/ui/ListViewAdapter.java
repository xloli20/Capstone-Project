package com.example.fitnessapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;

import java.util.List;

import androidx.annotation.NonNull;

public class ListViewAdapter extends ArrayAdapter<FavoritesWorkouts> {

    Context context;
    int resourse;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<FavoritesWorkouts> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resourse, parent, false);

        //ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.image);
        ImageView tx2 = convertView.findViewById(R.id.workout_image);
        TextView tx3 =  convertView.findViewById(R.id.workout_title);
        TextView tx4 =  convertView.findViewById(R.id.workout_instruction);

        FavoritesWorkouts An = getItem(position);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(An.getImage(), 0 , An.getImage().length);
        //imageButton.setImageBitmap(bitmap);
        Glide.with(convertView)
                .load(An.getWorkoutImage())
                .placeholder(R.drawable.ic_workout)
                .into(tx2);
        tx3.setText(An.getWorkoutName());
        tx4.setText(An.getWorkoutInstruction());

        return convertView;
    }
}