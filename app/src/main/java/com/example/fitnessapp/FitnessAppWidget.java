package com.example.fitnessapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.fitnessapp.Database.FavoritesWorkouts;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class FitnessAppWidget extends AppWidgetProvider {
    private final static String TAG = FitnessAppWidget.class.getSimpleName();
    private Context context;
    private List<FavoritesWorkouts> favoritesWorkoutsList = new ArrayList<>();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, List<FavoritesWorkouts> favoritesWorkouts) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fitness_app_widget);
        ArrayAdapter<FavoritesWorkouts> adapter = new ArrayAdapter<>(context,
                R.layout.favorites_items, favoritesWorkouts);
        ListView listView = new ListView(context);
        listView.setAdapter(adapter);
        views.addView(R.id.list_view,views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        favoritesWorkoutsList.add(new FavoritesWorkouts(String.valueOf(R.string.app_name),
                "https://p7.hiclipart.com/preview/785/67/656/running-computer-icons-woman-clip-art-workout.jpg",
                String.valueOf(R.string.app_name)));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,favoritesWorkoutsList);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

