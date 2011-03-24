package org.ediver.ascuba.gui;

import java.util.ArrayList;
import java.util.List;

import mvplan.gas.Gas;
import mvplan.main.Mvplan;
import mvplan.segments.SegmentAbstract;

import org.ediver.ascuba.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class SegmentDialog extends Dialog {
	SegmentDialogCallback callback;
	SegmentAbstract segment;
	
	EditText depth;
	EditText time;
	EditText setPoint;
	Spinner gases;
	
	Button ok;
	Button cancel;
	
	
	public SegmentDialog(Context context, SegmentDialogCallback callback) {
		super(context);
		this.callback = callback;
		

	}

	public SegmentDialog(Context context, SegmentAbstract segment, SegmentDialogCallback callback) {
		super(context);
		this.callback = callback;
		this.segment = (SegmentAbstract) segment.clone();
	}
	public SegmentDialog(Context context, SegmentAbstract segment) {
		super(context);
		this.segment = segment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.segment_dialog);
		setTitle(R.string.gas_dialog_title);
		setCancelable(true);
		
		depth = (EditText) findViewById(R.id.SegmentDialogDepth);
		time = (EditText) findViewById(R.id.SegmentDialogTime);
		setPoint = (EditText) findViewById(R.id.SegmentDialogSetPoint);
		depth.setText(""+segment.getDepth());
		time.setText(""+segment.getTime());
		setPoint.setText(""+segment.getSetpoint());
		gases= (Spinner) findViewById(R.id.SegmentDialogGases);
		ok= (Button) findViewById(R.id.SegmentDialogOk);
		cancel= (Button) findViewById(R.id.SegmentDialogCancel);
		cancel.setOnClickListener(cancelListener);
		ok.setOnClickListener(okListener);
		
		ArrayList<Gas> prefGases = Mvplan.prefs.getPrefGases();
		ArrayList<Gas> prefGasesEnabled = new ArrayList<Gas>();
		for (Gas gas : prefGases) {
			if (gas.getEnable()){
				prefGasesEnabled.add(gas);
			}
		}
		
		gases.setAdapter(new ArrayAdapter<Gas>(this.getContext(), android.R.layout.simple_spinner_item,prefGasesEnabled));
		//gases.
		
		LayoutParams params = getWindow().getAttributes();
		//params.height = LayoutParams.FILL_PARENT;
		params.width = LayoutParams.FILL_PARENT;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		//
		super.onCreate(savedInstanceState);

	}
	
	
	private class SegmentListAdaptor extends ArrayAdapter<SegmentAbstract> {

		public SegmentListAdaptor(Context context, int resource,
				int textViewResourceId, List<SegmentAbstract> objects) {
			super(context, resource, textViewResourceId);
			
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View a =  super.getView(position, convertView, parent);
			
			//handle checkbox;
			CheckBox cb = (CheckBox) a.findViewById(R.id.gas_list_label_check_box);
			cb.setTag(new Integer(position));
			cb.setChecked(getItem(position).getEnable());
			return a;
		}
		
	}
	android.view.View.OnClickListener cancelListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			SegmentDialog.this.dismiss();
		}

	};
	
	android.view.View.OnClickListener okListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			try {
				double d = Double.parseDouble(depth.getText().toString());
				segment.setDepth(d);
				double t = Double.parseDouble(time.getText().toString());
				segment.setTime(t);
				double sp = Double.parseDouble(setPoint.getText().toString());
				segment.setSetpoint(sp);
				segment.setGas((Gas) gases.getSelectedItem());
				
				SegmentDialog.this.callback.notify(segment);
				SegmentDialog.this.dismiss();
			} catch (NumberFormatException e) {
				// TODO display error message
				e.printStackTrace();
				
				
			}
			
			
		}

	};

	
	
	
	
}
