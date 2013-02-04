package org.ediver.ascuba.preferences.serialization;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mvplan.gas.Gas;
import mvplan.prefs.Prefs;
import mvplan.segments.SegmentAbstract;

public class PrefsSerialization {
	Gson gson;
	private Prefs prefs;
	
	public PrefsSerialization(Prefs prefs) {
		this.prefs = prefs;
		gson = new Gson();
	}
	
	
	public String serializeGases(){
		List<Gas> gases = prefs.getPrefGases();
		if (gases == null  || gases.size()==0) return null;
		
		List<StoredGas> sgases = new ArrayList<StoredGas>() ;
		for (Gas gas : gases) {
			sgases.add(new StoredGas(gas));
		}
		return gson.toJson(sgases);
	}
	public void deserializeGases(String gases) {
		List<StoredGas> sgases = gson.fromJson(gases, new TypeToken<List<StoredGas>>(){}.getType());
		ArrayList<Gas> g = new ArrayList<Gas>();
		for (StoredGas storedGas : sgases) {
			g.add(storedGas.toGas());
		}
		prefs.setPrefGases(g);
	}
	
	/**
	 * Deserialize segments, remember to first deserialize gases.
	 *
	 * @param segments the gases
	 */
	public void deserializeSegments(String segments) {
		List<StoredSegment> sseg = gson.fromJson(segments, new TypeToken<List<StoredSegment>>(){}.getType());
		ArrayList<SegmentAbstract> g = new ArrayList<SegmentAbstract>();
		for (StoredSegment storedGas : sseg) {
			g.add(storedGas.toSegment(prefs.getPrefGases()));
		}
		prefs.setPrefSegments(g);
	}

	
	public String serializeSegments(){
		List<SegmentAbstract> segments = prefs.getPrefSegments();
		if (segments == null  || segments.size()==0) return null;
		
		List<StoredSegment> ss = new ArrayList<StoredSegment>() ;
		for (SegmentAbstract s : segments) {
			ss.add(new StoredSegment(s,prefs.getPrefGases()));
		}
		return gson.toJson(ss);
	}



}
