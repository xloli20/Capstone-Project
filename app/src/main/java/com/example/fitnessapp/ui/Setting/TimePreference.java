package com.example.fitnessapp.ui.Setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.fitnessapp.R;

import androidx.preference.DialogPreference;

public class TimePreference extends DialogPreference {

    /**
     * In Minutes after midnight
     */
    private int mTime;

    /**
     * Resource of the dialog layout
     */
    private int mDialogLayoutResId = R.layout.pref_dialog_time;

    public TimePreference(Context context) {
        // Delegate to other constructor
        this(context, null);
    }

    public TimePreference(Context context, AttributeSet attrs) {
        // Delegate to other constructor
        // Use the preferenceStyle as the default style
        this(context, attrs, R.attr.preferenceStyle);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        // Delegate to other constructor
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // todo: Du custom stuff here
        // ...
        // read attributes etc.
    }

    /**
     * Gets the time from the Shared Preferences
     *
     * @return The current preference value
     */
    public int getTime() {
        return mTime;
    }

    /**
     * Saves the time to the SharedPreferences
     *
     * @param time The time to save
     */
    public void setTime(int time) {
        mTime = time;

        // Save to SharedPreference
        persistInt(time);
    }

    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // The type of this preference is Int, so we read the default value from the attributes
        // as Int. Fallback value is set to 0.
        return a.getString(index);
    }

    /**
     * Returns the layout resource that is used as the content View for the dialog
     */
    @Override
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }

}
