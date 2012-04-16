package org.ediver.ascuba.mvplan;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;
import mvplan.prefs.PrefsDAO;
import mvplan.prefs.PrefsException;

public class SharedPreferencesDAO implements PrefsDAO{
	
	
	private final SharedPreferences prefs;

	public SharedPreferencesDAO(SharedPreferences prefs){
		this.prefs = prefs;
	}

	public void setPrefs(Prefs p) throws PrefsException {
	
		
	}

	public Prefs getPrefs() throws PrefsException {
		
		Prefs mvPrefs = MvplanInstance.getPrefs();
		
		double lastStopDepth;
		try {
			lastStopDepth = Double.parseDouble(prefs.getString("lastStopDepth",
					"" + mvPrefs.getLastStopDepth()));
		} catch (NumberFormatException e) {
			lastStopDepth = mvPrefs.getLastStopDepth();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("lastStopDepth", "" + lastStopDepth);
			editor.commit();
		}
		mvPrefs.setLastStopDepth(lastStopDepth);
		mvPrefs.setUnitsTo(Integer.parseInt(prefs.getString("units", ""+Prefs.METRIC)));
		mvPrefs.setGfLow(Double.parseDouble(prefs.getString("gfLow", ""+mvPrefs.getGfLow()))/100.0);
		mvPrefs.setGfHigh(Double.parseDouble(prefs.getString("gfHigh", ""+mvPrefs.getGfHigh()))/100.0);
		mvPrefs.setDiveRMV(Double.parseDouble(prefs.getString("diveRVM", ""+mvPrefs.getDiveRMV())));
		mvPrefs.setDecoRMV(Double.parseDouble(prefs.getString("decoRVM", ""+mvPrefs.getDecoRMV())));
		mvPrefs.validatePrefs();
		return mvPrefs;
	}

}
