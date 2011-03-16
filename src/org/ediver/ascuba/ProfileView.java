package org.ediver.ascuba;

import mvplan.dive.Profile;
import mvplan.gui.android.ProfilePrinter;
import mvplan.main.Mvplan;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProfileView extends AScubaActivity {
	
	
	TextView view;
	private String TAG="bbbbbb";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_text_view);
		view = (TextView) findViewById(R.id.profile_text_view_text);
		Log.d(TAG,"Iaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaane");
		
		
	}
	
	public void calculate() {
		// TODO Auto-generated method stub
		Log.d(TAG,"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		// TODO Auto-generated method stub
		super.onResume();
		// TODO Auto-generated method stub
		super.onResume();
		Mvplan.prefs.getPrefGases();

		Profile p = new Profile(Mvplan.prefs.getPrefSegments(),Mvplan.prefs.getPrefGases(), null);
		// m.printModel();
		int returnCode = p.doDive();
		Profile currentProfile;
		switch (returnCode) {
		case Profile.SUCCESS:
			currentProfile = p; // Save as current profile
			p.doGasCalcs(); // Calculate gases
			// Save Model
			//Model currentModel = p.getModel();
			// System.out.println("Dive Metadata:"+currentModel.getMetaData());
			StringBuffer b = new StringBuffer();
			new ProfilePrinter(currentProfile, b).doPrintTable();

			view.setText(b);
			break;

		default:
			view.setText("Can not calculate");
			break;

		}
	}
	

}