package org.ediver.ascuba.gui;

import java.text.NumberFormat;

import mvplan.gas.Gas;

import org.ediver.ascuba.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GasDialog extends Dialog {
	GasDialogCallback callback;
	Gas gas;

	// O2
	SeekBar o2bar;
	TextView o2result;
	Button o2plus;
	Button o2minus;

	// He2
	SeekBar he2bar;
	TextView he2result;
	Button he2minus;
	Button he2plus;

	// N2
	ProgressBar n2bar;
	TextView n2result;

	// ppo2
	SeekBar ppo2bar;
	TextView ppo2result;
	Button ppo2plus;
	Button ppo2minus;

	// MOD

	// ppo2
	double ppo2;

	public GasDialog(Context context, GasDialogCallback callback) {
		super(context);
		ppo2 = 1.4;
		gas = new Gas(0.0, 0.32, Gas.getMod(0.32, ppo2));

	}

	public GasDialog(Context context, Gas gas) {
		super(context);
		this.gas = gas;
		ppo2 = Gas.getppO2(gas.getFO2(), gas.getMod());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.gas_dialog);
		setTitle(R.string.gas_dialog_title);
		setCancelable(true);

		o2bar = (SeekBar) findViewById(R.id.GasDialogO2);
		o2result = (TextView) findViewById(R.id.GasDialogO2Result);
		o2plus = (Button) findViewById(R.id.GasDialogO2plus);
		o2minus = (Button) findViewById(R.id.GasDialogO2minus);
		o2plus.setOnClickListener(o2plusListener);
		o2minus.setOnClickListener(o2minusListener);

		he2bar = (SeekBar) findViewById(R.id.GasDialogHe2);
		he2result = (TextView) findViewById(R.id.GasDialogHe2Result);
		he2plus = (Button) findViewById(R.id.GasDialogHe2plus);
		he2minus = (Button) findViewById(R.id.GasDialogHe2minus);
		he2plus.setOnClickListener(he2plusListener);
		he2minus.setOnClickListener(he2minusListener);
		

		n2bar = (ProgressBar) findViewById(R.id.GasDialogN2);
		n2result = (TextView) findViewById(R.id.GasDialogN2Result);

		ppo2bar = (SeekBar) findViewById(R.id.GasDialogppO2);
		ppo2result = (TextView) findViewById(R.id.GasDialogppO2Result);


		o2bar.setOnSeekBarChangeListener(o2Listener);
		he2bar.setOnSeekBarChangeListener(he2Listener);

		//
		LayoutParams params = getWindow().getAttributes();
		//params.height = LayoutParams.FILL_PARENT;
		params.width = LayoutParams.FILL_PARENT;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		//

		redrawGas();

		super.onCreate(savedInstanceState);

	}

	OnSeekBarChangeListener o2Listener = new OnSeekBarChangeListener() {
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				modifyO2(progress);
			}
		}
	};

	OnSeekBarChangeListener he2Listener = new OnSeekBarChangeListener() {
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				modifyHe(progress);
			}

		}
	};

	android.view.View.OnClickListener he2plusListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {

			int a = (int) ((gas.getFHe() * 100) + 0.5d) + 1;
			modifyHe(a);

		}

	};
	android.view.View.OnClickListener he2minusListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {

			int a = (int) ((gas.getFHe() * 100) + 0.5d) - 1;
			modifyHe(a);

		}

	};
	
	android.view.View.OnClickListener o2plusListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {

			int a = (int) ((gas.getFO2() * 100) + 0.5d) + 1;
			modifyO2(a);

		}

	};
	android.view.View.OnClickListener o2minusListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {

			int a = (int) ((gas.getFO2() * 100) + 0.5d) - 1;
			modifyO2(a);

		}

	};
	
	private void modifyHe(int a){
		if (a >= 0 && a <= 100) {
			double fhe2 = (a) / 100.0;
			double fo2 = gas.getFO2();
			if (fo2 + fhe2 >= 1.0) {
				fo2 = 1 - fhe2;
			}
			gas.setFO2(fo2);
			gas.setFHe(fhe2);

			redrawGas();
		}
	}
	private void modifyO2(int a){
		if (a >= 0 && a <= 100) {
			double fo2 = a / 100.0;
			double fhe2 = gas.getFHe();
			if (fo2 + fhe2 >= 1.0) {
				fhe2 = 1 - fo2;
			}
			gas.setFHe(fhe2);
			gas.setFO2(fo2);
			gas.setMod(Gas.getMod(gas.getFO2(), ppo2));

			redrawGas();
		}
	}
	
	

	private void redrawGas() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		setTitle(gas.toString() + " MOD: " + nf.format(gas.getMod()));

		int o2 = (int) ((gas.getFO2() * 100) + 0.5);
		o2bar.setProgress(o2);
		o2result.setText(o2 + " %");

		double a = gas.getFHe();
		a = (a * 100) + 0.5;
		int b = (int) a;
		he2bar.setProgress(b);
		he2result.setText(b + " %");

		n2bar.setProgress((int) (gas.getFN2() * 100));
		n2result.setText((int) (gas.getFN2() * 100) + " %");

		ppo2bar.setProgress((int) (ppo2 * 100 - 80));
		ppo2result.setText(ppo2 + " ");


	}
}
