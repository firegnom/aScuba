package org.ediver.ascuba.preferences;

import java.util.Map;

import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;
import mvplan.prefs.PrefsDAO;
import mvplan.prefs.PrefsException;

import org.ediver.ascuba.WTFExeption;
import org.ediver.ascuba.preferences.migrations.Init;
import org.ediver.ascuba.preferences.migrations.Migration;
import org.ediver.ascuba.preferences.serialization.PrefsSerialization;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesDAO implements PrefsDAO{
	
	

	/** PREFS_VERSION stored in shared prefs is used for migrating shared
	 *  preferenceces between versions . */
	public static final int PREFS_VERSION = 1;
	
	//not visible 
	public static final String PREFS_ID_PREF_GASES = "ascuba.preferences.prefGases";
	public static final String PREFS_ID_PREF_SEGMENTS = "ascuba.preferences.prefSegments";
	public static final String PREFS_ID_CHANGE_GASES = "ascuba.preferences.changeGases";
	
	public static final String PREFS_ID_SHOW_LATER = "ascuba.preferences.showLater";
	public static final String PREFS_ID_LAST_STOP_DEPTH = "ascuba.preferences.lastStopDepth";
	public static final String PREFS_ID_VERSION = "ascuba.preferences.version";
	public static final String PREFS_ID_UNITS = "ascuba.preferences.units";
	public static final String PREFS_ID_GF_LOW = "ascuba.preferences.GFLow";
	public static final String PREFS_ID_GF_HIGH = "ascuba.preferences.GFHigh";
	public static final String PREFS_ID_DIVE_RVM = "ascuba.preferences.diveRVM";
	public static final String PREFS_ID_DECO_RVM = "ascuba.preferences.decoRVM";
	public static final String PREFS_ID_ASCENT_RATE = "ascuba.preferences.ascentRate";
	public static final String PREFS_ID_DESCENT_RATE = "ascuba.preferences.descentRate";
	public static final String PREFS_ID_ALTITUDE = "ascuba.preferences.altitude";
	public static final String PREFS_ID_MULTILEVEL = "ascuba.preferences.multilevel";
	public static final String PREFS_ID_DECO_MODEL = "ascuba.preferences.decoModel";
	public static final String PREFS_ID_OUTPUT_STYLE = "ascuba.preferences.outputStyle";
	
	
	

	
	
	
	
	
	private final SharedPreferences prefs;
	


	public SharedPreferencesDAO(SharedPreferences prefs){
		this.prefs = prefs;
	}

	public void savePrefs(Prefs mvPrefs) throws PrefsException {
		PrefsSerialization s = new PrefsSerialization(mvPrefs);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(SharedPreferencesDAO.PREFS_ID_VERSION, ""+PREFS_VERSION);
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
		editor.putBoolean(SharedPreferencesDAO.PREFS_ID_MULTILEVEL, mvPrefs.getGfMultilevelMode());
		editor.putString(SharedPreferencesDAO.PREFS_ID_OUTPUT_STYLE, ""+mvPrefs.getOutputStyle());
		
		editor.commit();
		
	}

	public Prefs loadPrefs() throws PrefsException {
		
		Prefs mvPrefs = MvplanInstance.getPrefs();
				
		// migrate to current version or initialise preferences
		migrate();
		
		PrefsSerialization s = new PrefsSerialization(mvPrefs);
		
		s.deserializeGases(prefs.getString(PREFS_ID_PREF_GASES,""));
		s.deserializeSegments(prefs.getString(PREFS_ID_PREF_SEGMENTS,""));
		
		mvPrefs.setLastStopDepth(Double.parseDouble(prefs.getString(PREFS_ID_LAST_STOP_DEPTH, ""+ mvPrefs.getLastStopDepth())));
		mvPrefs.setUnitsTo(Integer.parseInt(prefs.getString(PREFS_ID_UNITS,""+ mvPrefs.getUnits())));

		mvPrefs.setGfLow(Integer.parseInt(prefs.getString(PREFS_ID_GF_LOW, ""+ ((int)(mvPrefs.getGfLow()*100))))/100.0);
		mvPrefs.setGfHigh(Integer.parseInt(prefs.getString(PREFS_ID_GF_HIGH, ""+ ((int)(mvPrefs.getGfHigh()*100))))/100.0);
		
		mvPrefs.setDiveRMV(Double.parseDouble(prefs.getString(PREFS_ID_DIVE_RVM, ""+ mvPrefs.getDiveRMV())));
		mvPrefs.setDecoRMV(Double.parseDouble(prefs.getString(PREFS_ID_DECO_RVM, ""+ mvPrefs.getDecoRMV())));
		mvPrefs.setAscentRate(Double.parseDouble(prefs.getString(PREFS_ID_ASCENT_RATE, ""+ mvPrefs.getAscentRate())));
		mvPrefs.setDescentRate(Double.parseDouble(prefs.getString(PREFS_ID_DESCENT_RATE,""+ mvPrefs.getDescentRate())));
		mvPrefs.setAltitude(Double.parseDouble(prefs.getString(PREFS_ID_ALTITUDE,""+ mvPrefs.getAltitude())));
		
		mvPrefs.setModelClassName(prefs.getString(PREFS_ID_DECO_MODEL,mvPrefs.getModelClassName()));
		mvPrefs.setGfMultilevelMode(prefs.getBoolean(PREFS_ID_MULTILEVEL,mvPrefs.getGfMultilevelMode()));
		mvPrefs.setOutputStyle(Integer.parseInt(prefs.getString(PREFS_ID_OUTPUT_STYLE, ""+mvPrefs.getOutputStyle())));
		mvPrefs.setOcDeco(prefs.getBoolean(PREFS_ID_CHANGE_GASES, mvPrefs.getOcDeco()));
		
		mvPrefs.validatePrefs();
		MvplanInstance.setPrefs(mvPrefs);
		return mvPrefs;
	}
	
	
	private Map<Integer,Migration> migrations ;
	
	private void migrate(){
		Prefs mvPrefs = MvplanInstance.getPrefs();
		
		//get version 
		int version = Integer.parseInt(prefs.getString(PREFS_ID_VERSION,"0"));
		if (version == 0 ){
			// make default migration
			new Init().migrate(prefs);
		}
		version = Integer.parseInt(prefs.getString(PREFS_ID_VERSION,"-1"));
		if (version == -1 ){
			// something is wrong this should be initialized .
			throw new WTFExeption("TODO : create warning message handling this condition");
		}
		
		// everything is up to date no need to migrate 
		if (version == PREFS_VERSION) return;
		
		
		if (version > PREFS_VERSION) {
			// this condition is a tricky one that means we have loaded older 
			// software than one which last stored this preferences 
			// i think i must quit with some kind of warning message 
			throw new WTFExeption("TODO : create warning message handling this condition");
		};
		
		// iterate over all versions and migrate each
		for (int i = version;i <= PREFS_VERSION ; i++) {
			Migration migration = migrations.get(i);
			if (migration == null) {
				// migration not avilable throw exception
				throw new WTFExeption("TODO : create warning message handling this condition");
			}
			migration.migrate(prefs);
			
		}
		
	}

	public boolean getShowLater() {
		return prefs.getBoolean(SharedPreferencesDAO.PREFS_ID_SHOW_LATER, true);
	}
	public void setShowLater(boolean value) {
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPreferencesDAO.PREFS_ID_SHOW_LATER, value);
		edit.commit();
	}

	public boolean getChangeGases() {
		return prefs.getBoolean(SharedPreferencesDAO.PREFS_ID_CHANGE_GASES, MvplanInstance.getPrefs().getOcDeco());
	}
	
	public void setChangeGases(boolean value) {
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPreferencesDAO.PREFS_ID_CHANGE_GASES, value);
		edit.commit();
		MvplanInstance.getPrefs().setOcDeco(value);
	}
	
	
	

}
