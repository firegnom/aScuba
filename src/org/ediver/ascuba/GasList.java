package org.ediver.ascuba;

import mvplan.gas.Gas;

import org.ediver.ascuba.gui.GasDialog;
import org.ediver.ascuba.gui.GasDialogCallback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class GasList extends AScubaActivity {
	Button add;
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gas_list_view);
		add = (Button) findViewById(R.id.gas_list_add);
		add.setOnClickListener(addButtonListener);
		
	}
	
	android.view.View.OnClickListener addButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			new GasDialog(GasList.this,addCallback).show();
		}
	};
	
	GasDialogCallback addCallback = new GasDialogCallback() {
		public void notify(Gas g) {
			System.out.println(g);
		}
	};
	
	

}