package org.ediver.ascuba;

import mvplan.dive.Profile;
import mvplan.gui.android.ProfilePrinter;
import mvplan.main.Mvplan;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProfileView extends AScubaActivity {

	TextView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_text_view);
		view = (TextView) findViewById(R.id.profile_text_view_text);

	}

	public void calculate() {
		super.onResume();
		Mvplan.prefs.setOcDeco(true);

		Profile p = new Profile(Mvplan.prefs.getPrefSegments(),
				Mvplan.prefs.getPrefGases(), null);
		// m.printModel();
		int returnCode = p.doDive();
		Profile currentProfile;
		switch (returnCode) {
		case Profile.SUCCESS:
			currentProfile = p; // Save as current profile
			p.doGasCalcs(); // Calculate gases
			// Save Model
			// Model currentModel = p.getModel();
			// System.out.println("Dive Metadata:"+currentModel.getMetaData());
			StringBuffer b = new StringBuffer();
			new ProfilePrinter(currentProfile, b).doPrintTable();

			view.setText(b);
			break;

		case Profile.CEILING_VIOLATION:
			view.setText(Mvplan
					.getResource("mvplan.gui.MainFrame.ceilingViolation.text"));
			break;

		case Profile.NOTHING_TO_PROCESS:
			view.setText(Mvplan
					.getResource("mvplan.gui.MainFrame.noSegments.text"));
			break;
		case Profile.PROCESSING_ERROR:
			view.setText(Mvplan
					.getResource("mvplan.gui.MainFrame.processingError.text"));
		case Profile.INFINITE_DECO:
			view.setText(Mvplan
					.getResource("mvplan.gui.MainFrame.decoNotPossible.text"));

		default:
			view.setText("Can not calculate");
			break;

		}
	}

}