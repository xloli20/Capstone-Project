package com.example.fitnessapp.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;
import com.example.fitnessapp.utilites.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final static String TAG = MyWidgetRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private List<FavoritesWorkouts> favoritesWorkoutsList = new ArrayList<>();
    private FavoritesWorkouts favoritesWorkout;
    private AppDatabase mDB;


    MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        mDB = AppDatabase.getInstance(mContext);
    }

    @Override
    public void onDataSetChanged() {
//        final long identityToken = Binder.clearCallingIdentity();
        AppExecutors.getInstance().diskIO().execute(() ->
                favoritesWorkoutsList = mDB.favoritesWorkoutDao().getAllSimpleList());
//        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return favoritesWorkoutsList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.d(TAG, "getViewAt: "+i);
        favoritesWorkout = favoritesWorkoutsList.get(i);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorites_items);
        rv.setTextViewText(R.id.workout_title, favoritesWorkout.getWorkoutName());
        Log.d(TAG, "getViewAt: "+favoritesWorkout.getWorkoutName());
        rv.setTextViewText(R.id.workout_instruction, "...");
        Log.d(TAG, "getViewAt: "+favoritesWorkout.getWorkoutInstruction());
        rv.setImageViewUri(R.id.workout_image,Uri.parse(""));
        Log.d(TAG, "getViewAt: "+favoritesWorkout.getWorkoutImage());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
