package com.example.fitnessapp.ui.Setting;

import android.os.Bundle;
import android.util.Log;

import com.example.fitnessapp.R;

import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * The Preference Fragment which shows the Preferences as a List and handles the Dialogs for the
 * Preferences.
 **/
public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.settings);

        Preference syncPref = findPreference("pref_pref1");
        syncPref.setOnPreferenceClickListener((new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (syncPref.shouldDisableDependents()) {
                    Log.d(getTag(), "onPreferenceChange: syncPref.isEnabled...... ");
                    preference = findPreference("pref_pref4");
                    preference.setVisible(false);

                } else {
                    Log.d(getTag(), "onPreferenceChange: else...... ");
                    preference = findPreference("pref_pref4");
                    preference.setVisible(true);
                }
                return false;
            }
        }));

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
