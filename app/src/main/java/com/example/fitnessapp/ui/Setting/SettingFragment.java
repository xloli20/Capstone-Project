package com.example.fitnessapp.ui.Setting;

import android.os.Bundle;

import com.example.fitnessapp.R;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingFragment extends PreferenceFragmentCompat {

    private SettingViewModel settingViewModel;
    private SwitchPreference mListPreference;
    private Preference timePreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        mListPreference = (SwitchPreference) getPreferenceManager().findPreference("notifications_reminder");
        mListPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            // your code here
            return false;
        });

//        timePreference =  getPreferenceManager().findPreference("timePrefA_Key");
//        timePreference.setOnPreferenceChangeListener(((preference, newValue) -> {
//
//            return false;
//        }));

    }

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {


//        return inflater.inflate(R.layout.fragment_setting, container, false);
//        settingViewModel =
//                ViewModelProviders.of(this).get(SettingViewModel.class);
//
//        View root = inflater.inflate(R.layout.fragment_setting, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        settingViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;


//    @Override
//    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
//        // Indicate here the XML resource you created above that holds the preferences
//        setPreferencesFromResource(R.xml.settings, rootKey);
//    }


}