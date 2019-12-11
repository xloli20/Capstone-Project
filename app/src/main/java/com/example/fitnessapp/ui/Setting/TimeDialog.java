package com.example.fitnessapp.ui.Setting;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.example.fitnessapp.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;


public class TimeDialog extends PreferenceDialogFragmentCompat {

    private TimePicker mTimePicker;
    private int hours;
    private int minutes;

    /**
     * Creates a new Instance of the TimeDialog and stores the key of the
     * related Preference
     *
     * @param key The key of the related Preference
     * @return A new Instance of the TimeDialog
     */
    static TimeDialog newInstance(String key) {
        final TimeDialog timeDialog = new TimeDialog();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        timeDialog.setArguments(bundle);

        return timeDialog;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mTimePicker = view.findViewById(R.id.edit);

        // Exception: There is no TimePicker with the id 'edit' in the dialog.
        if (mTimePicker == null) {
            throw new IllegalStateException("Dialog view must contain a TimePicker with id 'edit'");
        }

        // Get the time from the related Preference
        Integer minutesAfterMidnight = null;
        DialogPreference preference = getPreference();
        if (preference instanceof TimePreference) {
            minutesAfterMidnight = ((TimePreference) preference).getTime();
        }

        // Set the time to the TimePicker
        if (minutesAfterMidnight != null) {
            hours = minutesAfterMidnight / 60;
            minutes = minutesAfterMidnight % 60;
            boolean is24hour = DateFormat.is24HourFormat(getContext());

            mTimePicker.setIs24HourView(is24hour);
            mTimePicker.setCurrentHour(hours);
            mTimePicker.setCurrentMinute(minutes);
        }
    }

    /**
     * Called when the Dialog is closed.
     *
     * @param positiveResult Whether the Dialog was accepted or canceled.
     */
    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            // Get the current values from the TimePicker
            int hours;
            int minutes;
            if (Build.VERSION.SDK_INT >= 23) {
                hours = mTimePicker.getHour();
                minutes = mTimePicker.getMinute();
            } else {
                hours = mTimePicker.getCurrentHour();
                minutes = mTimePicker.getCurrentMinute();
            }

            // Generate value to save
            int minutesAfterMidnight = (hours * 60) + minutes;

            // Save the value
            DialogPreference preference = getPreference();
            if (preference instanceof TimePreference) {
                TimePreference timePreference = ((TimePreference) preference);
                preference.setSummary(getSummary());

                FirebaseMessaging.getInstance().send(
                        new RemoteMessage.Builder("554269775990" + "@gcm.googleapis.com")
                                .setMessageId("0")
                                .addData("time", String.valueOf(getSummary()))
//                                .setToken(R.string.msg_token_fmt)
                                .build());
                Log.d(getTag(), "onDialogClosed: message....");

                // This allows the client to ignore the user value.
                if (timePreference.callChangeListener(minutesAfterMidnight)) {
                    // Save the value
                    timePreference.setTime(minutesAfterMidnight);
                }
            }
        }
    }

    private CharSequence getSummary() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        java.text.DateFormat sdf = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

        return sdf.format(cal.getTime());
    }
}
