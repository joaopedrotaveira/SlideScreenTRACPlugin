package com.taveiranet.slidescreen.trac.preference;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;

import com.taveiranet.slidescreen.trac.R;

public class SlideTracPluginPreferences extends PreferenceActivity {
    private static final String TAG = SlideTracPluginPreferences.class.getName();

	private SharedPreferences mPrefs;
	
	private PreferenceScreen prefsScreen = null;
	private PreferenceCategory serverPreferences = null;
	private EditTextPreference serverUrl = null; 
	private ListPreference authMethod = null;
	private EditTextPreference username = null;
	private EditTextPreference password = null;

	private PreferenceCategory queryPreferences = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"* onCreate");

		prefsScreen = getPreferenceManager().createPreferenceScreen(this);
		prefsScreen.getPreferenceManager().setSharedPreferencesName("tracplugin.prefs");
		prefsScreen.getPreferenceManager().setSharedPreferencesMode(MODE_PRIVATE);
		setPreferenceScreen(prefsScreen);
		
		
		serverPreferences = new PreferenceCategory(this);
		serverPreferences.setTitle(R.string.preferences_server_settings);
		
		getPreferenceScreen().addPreference(serverPreferences);
		
		serverUrl = new EditTextPreference(this);
		serverUrl.setTitle(R.string.preferences_server_url);
		serverUrl.setDialogTitle(R.string.preferences_server_url);
		serverUrl.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_URI | InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
		serverUrl.setKey("serverUrl");
		serverPreferences.addPreference(serverUrl);
		
		authMethod = new ListPreference(this);
		authMethod.setTitle(R.string.preferences_server_authorization);
		authMethod.setDialogTitle(R.string.preferences_server_authorization);
		authMethod.setEntries(R.array.authMethods);
		authMethod.setEntryValues(new CharSequence[]{"anonymous","basic"});
		authMethod.setKey("authMethod");
		serverPreferences.addPreference(authMethod);
		
		username = new EditTextPreference(this);
		username.setTitle(R.string.preferences_server_username);
		username.setDialogTitle(R.string.preferences_server_username);
		username.setKey("username");
		serverPreferences.addPreference(username);

		password = new EditTextPreference(this);
		password.setTitle(R.string.preferences_server_password);
		password.setDialogTitle(R.string.preferences_server_password);
		password.getEditText().setTransformationMethod(new PasswordTransformationMethod());
		password.setKey("password");
		serverPreferences.addPreference(password);

		
		if("anonymous".equals(prefsScreen.getSharedPreferences().getString("authMethod", "anonymous"))){
			username.setEnabled(false);
			password.setEnabled(false);
		}
		
		authMethod.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if("anonymous".equals(newValue)){
					username.setEnabled(false);
					password.setEnabled(false);
				}else{
					username.setEnabled(true);
					password.setEnabled(true);
				}
				populateQueryOptions();
				return true;
			}
		});
		

		queryPreferences = new PreferenceCategory(this);
		queryPreferences.setKey("trac.query");
		queryPreferences.setTitle(R.string.preferences_query);
		queryPreferences.setSummary(R.string.preferences_query);
		getPreferenceScreen().addPreference(queryPreferences);
		
		
		mPrefs = getSharedPreferences("tracplugin.prefs", MODE_PRIVATE);
		Map<String, ?> prefsKeys = mPrefs.getAll();
		for(String key: prefsKeys.keySet()){
			Log.d(TAG,"Key: " + key);
		}

		populateQueryOptions();

		PreferenceCategory aboutPreferences = new PreferenceCategory(this);

		aboutPreferences .setTitle(R.string.pref_about_title);
		aboutPreferences .setSummary(R.string.pref_about_summary);
		getPreferenceScreen().addPreference(aboutPreferences );
		
		PreferenceScreen about = getPreferenceManager().createPreferenceScreen(this);
		about.setKey("trac.about");
		about.setTitle(R.string.pref_about_title);
		about.setSummary(R.string.pref_about_title);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setComponent(new ComponentName("com.taveiranet.slidescreen.trac", "com.taveiranet.slidescreen.trac.preference.AboutActivity"));
		about.setIntent(intent);
		aboutPreferences.addPreference(about);
		
	}

	private void populateQueryOptions(){
		String server = mPrefs.getString("serverUrl", null);
		String authMethod = mPrefs.getString("authMethod", "anonymous");
		String username = mPrefs.getString("username", null);
		String password = mPrefs.getString("password", null);
		
		try {
			new URL(server);
			if("anonymous".equals(authMethod) || ("basic".equals(authMethod) && username != null && password != null)){
				//Trac trac = new Trac(this, server, username, password);
				
//				trac.getAllResolutions();
//				trac.getAllStatus();
//				trac.getAllTypes();
//				trac.getAllPriorities();
				
				EditTextPreference owner = new EditTextPreference(this);
				owner.setTitle("Owner");
				owner.setDialogTitle("Owner");
				owner.setKey("owner");
				queryPreferences.addPreference(owner);
			}
		} catch (MalformedURLException e) {
		}
		
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG,"* onPause");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.d(TAG,"* onStop");
		Map<String, ?> prefsKeys = getPreferenceScreen().getSharedPreferences().getAll();
		for(String key: prefsKeys.keySet()){
			Log.d(TAG,"Key: " + key);
		}
		super.onStop();
	}
}
