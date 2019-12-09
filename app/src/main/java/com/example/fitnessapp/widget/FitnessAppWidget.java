package com.example.fitnessapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.fitnessapp.Database.FavoritesWorkouts;
import com.example.fitnessapp.R;

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
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fitness_app_widget);
        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.list_view, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
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

