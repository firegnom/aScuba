package org.ediver.ascuba;

import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;

import org.ediver.ascuba.gui.MVPlanPreferences;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DivePlanner extends ActivityGroup {
	LinearLayout view;
	Window gasListAction;
	Window segmentAction;
	Window calculateAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dive_planner_view);
		view = (LinearLayout) findViewById(R.id.testme);
		LocalActivityManager localActivityManager = getLocalActivityManager();
		segmentAction = localActivityManager.startActivity("segmentAction",
				new Intent(this, SegmentList.class));
		calculateAction = localActivityManager.startActivity("calculateAction",
				new Intent(this, ProfileView.class));
		gasListAction = localActivityManager.startActivity("gasListAction",
				new Intent(this, GasList.class));
		view.addView(gasListAction.getDecorView(), LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		Button b = (Button) findViewById(R.id.dive_planner_calculate);
		b.setOnClickListener(calculateButtonListener);

		b = (Button) findViewById(R.id.dive_planner_gasList);
		b.setOnClickListener(gasListButtonListener);

		b = (Button) findViewById(R.id.dive_planner_profile);
		b.setOnClickListener(profileButtonListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.plannermenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.plan_menu_preferences:
			Intent settingsActivity = new Intent(getBaseContext(),
					MVPlanPreferences.class);
			startActivity(settingsActivity);
			return true;
		case R.id.quit:
			this.finish();
			return true;
			// Generic catch all for all the other menu resources
		default:
			// Don't toast text when a submenu is clicked
			if (!item.hasSubMenu()) {
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
			break;
		}

		return false;
	}

	android.view.View.OnClickListener calculateButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			ProfileView p = (ProfileView) (DivePlanner.this
					.getLocalActivityManager().getActivity("calculateAction"));
			p.calculate();
			view.addView(calculateAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener gasListButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(gasListAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener profileButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(segmentAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};

	protected void onStart() {
		super.onStart();
		getPrefs();
	};

	private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		double lastStopDepth;
		try {
			lastStopDepth = Double.parseDouble(prefs.getString("lastStopDepth",
					"" + MvplanInstance.getPrefs().getLastStopDepth()));
		} catch (NumberFormatException e) {
			lastStopDepth = MvplanInstance.getPrefs().getLastStopDepth();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("lastStopDepth", "" + lastStopDepth);
			editor.commit();
		}
		MvplanInstance.getPrefs().setLastStopDepth(lastStopDepth);
		MvplanInstance.getPrefs();
		MvplanInstance.getPrefs().setUnitsTo(Integer.parseInt(prefs.getString("units", ""+Prefs.METRIC)));
		MvplanInstance.getPrefs().setGfLow(Double.parseDouble(prefs.getString("gfLow", ""+MvplanInstance.getPrefs().getGfLow()))/100.0);
		MvplanInstance.getPrefs().setGfHigh(Double.parseDouble(prefs.getString("gfHigh", ""+MvplanInstance.getPrefs().getGfHigh()))/100.0);
		MvplanInstance.getPrefs().setGfHigh(Double.parseDouble(prefs.getString("diveRVM", ""+MvplanInstance.getPrefs().getGfHigh())));
		MvplanInstance.getPrefs().setGfHigh(Double.parseDouble(prefs.getString("decoRVM", ""+MvplanInstance.getPrefs().getGfHigh())));
		MvplanInstance.getPrefs().validatePrefs();

	}

}