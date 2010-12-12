package org.ediver.ascuba;

import java.util.ArrayList;
import java.util.ResourceBundle;

import mvplan.dive.Profile;
import mvplan.gas.Gas;
import mvplan.gui.android.ProfilePrinter;
import mvplan.main.Mvplan;
import mvplan.model.Model;
import mvplan.prefs.Prefs;
import mvplan.segments.SegmentAbstract;
import mvplan.segments.SegmentDive;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends Activity {
	TextView view;
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.welcome);
		view =  (TextView) findViewById(R.id.TextView01);
		b =  (Button) findViewById(R.id.Button01);
		b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Mvplan mv = new Mvplan();
				Prefs prefs = new Prefs();
				Mvplan.stringResource = ResourceBundle.getBundle("mvplan/resources/strings");
				Mvplan.prefs = prefs;
				Mvplan.prefs.setDefaultPrefs();


				ArrayList<SegmentAbstract> knownSegments = new ArrayList<SegmentAbstract>();
				ArrayList<Gas> knownGases = new ArrayList<Gas>();

				Gas g = new Gas(0, 0.21, 66.0);
				knownGases.add(g);
				SegmentDive s = new SegmentDive(20, 40, g, 0);
				knownSegments.add(s);
				

				Profile p = new Profile(knownSegments, knownGases, null);
				//m.printModel();
				int returnCode = p.doDive();
				Profile currentProfile;
				switch (returnCode) {
				case Profile.SUCCESS:
					currentProfile = p; // Save as current profile
					p.doGasCalcs(); // Calculate gases
					// Save Model
					Model currentModel = p.getModel();
					// System.out.println("Dive Metadata:"+currentModel.getMetaData());
					StringBuffer b = new StringBuffer();
					new ProfilePrinter(currentProfile, b).doPrintTable();

					System.out.println(b);
					view.setText(b);
					break;

				default:
					break;
				}
				
			}
		});
		
	}

}