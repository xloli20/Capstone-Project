package com.example.fitnessapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<FavoritesWorkouts> implements View.OnClickListener{

private ArrayList<FavoritesWorkouts> dataSet;
        Context mContext;

// View lookup cache
private static class ViewHolder {
    ImageView imageView;
    TextView textViewTitle;
    TextView textViewInstruction;
}

    public ListViewAdapter(ArrayList<FavoritesWorkouts> data, Context context) {
        super(context, R.layout.favorites_items, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        FavoritesWorkouts dataModel=(FavoritesWorkouts)object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FavoritesWorkouts dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.favorites_items , parent, false);
            viewHolder.imageView = convertView.findViewById(R.id.workout_image);
            viewHolder.textViewTitle = convertView.findViewById(R.id.workout_title);
            viewHolder.textViewInstruction = convertView.findViewById(R.id.workout_instruction);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

//        viewHolder.txtName.setText(dataModel.getName());
//        viewHolder.txtType.setText(dataModel.getType());
//        viewHolder.txtVersion.setText(dataModel.getVersion_number());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}