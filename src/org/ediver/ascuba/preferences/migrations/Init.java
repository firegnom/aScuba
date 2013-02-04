package org.ediver.ascuba.preferences.migrations;

import java.util.ArrayList;

import org.ediver.ascuba.preferences.SharedPreferencesDAO;
import org.ediver.ascuba.preferences.serialization.PrefsSerialization;

import mvplan.gas.Gas;
import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.SharedPreferences;

/**
 * The Class Init is responsble for initializing shared preferences to first version.
 */
public class Init implements Migration {
	private static final int INIT_VERSION = 1;
	
	Gson gson = new Gson();
	
	public void migrate(SharedPreferences prefs) {
		Prefs mvPrefs = MvplanInstance.getPrefs();
		PrefsSerialization s = new PrefsSerialization(mvPrefs);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(SharedPreferencesDAO.PREFS_ID_VERSION, ""+INIT_VERSION);
		editor.putString(SharedPreferencesDAO.PREFS_ID_LAST_STOP_DEPTH, ""+ mvPrefs.getLastStopDepth());
		editor.putString(SharedPreferencesDAO.PREFS_ID_PREF_GASES, s.serializeGases());
		editor.putString(SharedPreferencesDAO.PREFS_ID_PREF_SEGMENTS,  s.serializeSegments());
		editor.putString(SharedPreferencesDAO.PREFS_ID_UNITS, ""+mvPrefs.getUnits());
		editor.putString(SharedPreferencesDAO.PREFS_ID_GF_LOW,  ""+(int)((mvPrefs.getGfLow()*100)));
		editor.putString(SharedPreferencesDAO.PREFS_ID_GF_HIGH, ""+(int)((mvPrefs.getGfHigh()*100)));
		editor.putString(SharedPreferencesDAO.PREFS_ID_DIVE_RVM, ""+ mvPrefs.getDiveRMV());
		editor.putString(SharedPreferencesDAO.PREFS_ID_DECO_RVM, ""+ mvPrefs.getDiveRMV());
		editor.putString(SharedPreferencesDAO.PREFS_ID_ASCENT_RATE, ""+ mvPrefs.getAscentRate());
		editor.putString(SharedPreferencesDAO.PREFS_ID_DESCENT_RATE, ""+ mvPrefs.getDescentRate());
		editor.putString(SharedPreferencesDAO.PREFS_ID_ALTITUDE, ""+ mvPrefs.getAltitude());
		
		editor.putString(SharedPreferencesDAO.PREFS_ID_MULTILEVEL, ""+mvPrefs.getGfMultilevelMode());
		editor.putString(SharedPreferencesDAO.PREFS_ID_OUTPUT_STYLE, ""+mvPrefs.getOutputStyle());
		
		editor.commit();
	}

}
