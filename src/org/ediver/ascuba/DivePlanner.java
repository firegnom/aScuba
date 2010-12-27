package org.ediver.ascuba;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class DivePlanner extends ActivityGroup {
	LinearLayout view;
	Window gasListAction;
	Window profileAction ;
	Window calculateAction ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dive_planner_view);
		view = (LinearLayout) findViewById(R.id.testme);
		LocalActivityManager localActivityManager = getLocalActivityManager();
		profileAction = localActivityManager.startActivity("test", new Intent(
				this, Welcome.class));
		calculateAction = localActivityManager.startActivity("test", new Intent(
				this, Welcome.class));
		gasListAction = localActivityManager.startActivity("test", new Intent(this,
				GasList.class));
		view.addView(gasListAction.getDecorView(), LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);

		Button b = (Button) findViewById(R.id.dive_planner_calculate);
		b.setOnClickListener(calculateButtonListener);
		
		b = (Button) findViewById(R.id.dive_planner_gasList);
		b.setOnClickListener(gasListButtonListener);

		b = (Button) findViewById(R.id.dive_planner_profile);
		b.setOnClickListener(profileButtonListener);
	}

	android.view.View.OnClickListener calculateButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(calculateAction.getDecorView(), LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener gasListButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(gasListAction.getDecorView(), LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener profileButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(profileAction.getDecorView(), LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
		}
	};

}