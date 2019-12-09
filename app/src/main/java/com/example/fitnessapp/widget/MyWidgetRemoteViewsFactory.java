package com.example.fitnessapp.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.fitnessapp.Database.AppDatabase;
import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;

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

        final long identityToken = Binder.clearCallingIdentity();

        //todo: try it with executor
        favoritesWorkoutsList = mDB.favoritesWorkoutDao().getAllSimpleList();
//        MainViewModel viewModel = ViewModelProviders.of(FavoritesActivity.class).get(MainViewModel.class);
//        // Observe the LiveData object in the ViewModel
//        viewModel.getFavorites().observe(this, favoritesWorkouts -> {
//            Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
//
//        });
        Binder.restoreCallingIdentity(identityToken);

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

        favoritesWorkout = favoritesWorkoutsList.get(i);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorites_items);
        rv.setTextViewText(R.id.workout_title, favoritesWorkout.getWorkoutName());
        rv.setTextViewText(R.id.workout_instruction, favoritesWorkout.getWorkoutInstruction());
        rv.setImageViewUri(R.id.workout_image,Uri.parse(favoritesWorkout.getWorkoutImage()));

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
