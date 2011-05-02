package com.taveiranet.slidescreen.trac.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.taveiranet.slidescreen.trac.R;

public class AboutActivity extends PreferenceActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);
    }
}