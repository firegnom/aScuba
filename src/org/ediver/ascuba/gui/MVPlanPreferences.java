package org.ediver.ascuba.gui;

import org.ediver.ascuba.R;
import org.ediver.ascuba.preferences.SharedPreferencesDAO;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.text.InputType;
import android.widget.Toast;

public class MVPlanPreferences extends PreferenceActivity {
	private static final String NAME = "mvPlanSharedPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mvprefs);
		
		makeDecimal(SharedPreferencesDAO.PREFS_ID_LAST_STOP_DEPTH);
		makeNumber(SharedPreferencesDAO.PREFS_ID_GF_HIGH);
		makeNumber(SharedPreferencesDAO.PREFS_ID_GF_LOW);
		makeDecimal(SharedPreferencesDAO.PREFS_ID_DIVE_RVM);
		makeDecimal(SharedPreferencesDAO.PREFS_ID_DECO_RVM);
		makeDecimal(SharedPreferencesDAO.PREFS_ID_DESCENT_RATE);
		makeDecimal(SharedPreferencesDAO.PREFS_ID_ASCENT_RATE);
		makeDecimal(SharedPreferencesDAO.PREFS_ID_ALTITUDE);
		
		// Get the custom preference
		Preference customPref = (Preference) findPreference(SharedPreferencesDAO.PREFS_ID_LAST_STOP_DEPTH);
		customPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
						
						SharedPreferences customSharedPreference = getSharedPreferences(
								NAME, Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = customSharedPreference
								.edit();
						try {
							editor.putString(SharedPreferencesDAO.PREFS_ID_LAST_STOP_DEPTH, ""+Double.parseDouble(newValue.toString()));
						} catch (NumberFormatException e) {
							Toast.makeText(getBaseContext(),
									"Could not set new depth :"+newValue+" make sure it is number",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
						editor.commit();
						
						return true;
					}


				});
	}
	
	private void makeDecimal(String key){
		EditTextPreference prefEditText = (EditTextPreference) findPreference(key);
		prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}
	private void makeNumber(String key){
		EditTextPreference prefEditText = (EditTextPreference) findPreference(key);
		prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
}
