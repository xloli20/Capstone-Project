package com.example.fitnessapp.ui.Setting;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.other.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * The Preference Fragment which shows the Preferences as a List and handles the Dialogs for the
 * Preferences.
 **/
public class SettingFragment extends PreferenceFragmentCompat {
    private final static String TAG = SettingFragment.class.getSimpleName();

    private Preference mSwitchPreference;
    private Preference mTimePreference;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTimePreference.setVisible(savedInstanceState.getBoolean("time_preference"));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTimePreference.setVisible(savedInstanceState.getBoolean("time_preference"));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTimePreference.setVisible(savedInstanceState.getBoolean("time_preference"));
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.settings);

        mSwitchPreference = findPreference("pref_pref1");
        mSwitchPreference.setOnPreferenceClickListener((preference -> {
            mTimePreference = preference;
            if (mSwitchPreference.shouldDisableDependents()) {
                mTimePreference = findPreference("pref_pref4");
                mTimePreference.setVisible(false);
            } else {
                mTimePreference = findPreference("pref_pref4");
                mTimePreference.setVisible(true);
                Log.d(TAG, "onPreferenceChange: mSwitchPreference.isEnabled...... ");

                // Get token
                // [START retrieve_current_token]
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            // Log
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);
                        });
                // [END retrieve_current_token]

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 22);
                calendar.set(Calendar.MINUTE, 28);

                alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() / 1000,
                        AlarmManager.INTERVAL_DAY, alarmIntent);

                String channelId = getString(R.string.default_notification_channel_id);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(getContext(), channelId)
                                .setSmallIcon(R.drawable.ic_workout)
                                .setContentTitle(getString(R.string.reminder_message))
                                .setContentIntent(pendingIntent)
                                .setContentIntent(alarmIntent)
                                .setWhen(calendar.getTimeInMillis())
                                .setAutoCancel(true);

                NotificationManager notificationManager =
                        (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

            }
            return false;
        }));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("time_preference", mTimePreference.isVisible());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mTimePreference.setVisible(savedInstanceState.getBoolean("time_preference"));
        }
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        // Try if the preference is one of our custom Preferences
        DialogFragment dialogFragment = null;
        if (preference instanceof TimePreference) {
            // Create a new instance of TimeDialog with the key of the related
            // Preference
            dialogFragment = TimeDialog.newInstance(preference.getKey());
        }

        if (dialogFragment != null) {
            // The dialog was created (it was one of our custom Preferences), show the dialog for it
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                    ".PreferenceFragment.DIALOG");
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference);
        }
    }
}