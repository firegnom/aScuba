package org.ediver.ascuba.gui;

import java.text.NumberFormat;

import mvplan.gas.Gas;

import org.ediver.ascuba.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GasDialog extends Dialog {
	GasDialogCallback callback;
	Gas gas;
	
	//O2
	SeekBar o2bar;
	TextView o2result;
	
	//He2
	SeekBar he2bar;
	TextView he2result;
	
	//N2
	ProgressBar n2bar;
	TextView n2result;
	
	//ppo2
	ProgressBar ppo2bar;
	TextView ppo2result;
	
	//MOD
	TextView mod;
	
	
	//ppo2
	double ppo2;
	
	public GasDialog(Context context,GasDialogCallback callback) {
		super(context);
		ppo2 = 1.4;
		gas = new Gas(0.0,0.32,Gas.getMod(0.32, ppo2));
		
	}
	public GasDialog(Context context,Gas gas) {
		super(context);
		this.gas= gas;
		ppo2 =Gas.getppO2(gas.getFO2(), gas.getMod()); 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.gas_dialog);
		setTitle(R.string.gas_dialog_title);
		setCancelable(true);
		
		o2bar = (SeekBar) findViewById(R.id.GasDialogO2);
		o2result = (TextView) findViewById(R.id.GasDialogO2Result);
		
		he2bar = (SeekBar) findViewById(R.id.GasDialogHe2);
		he2result = (TextView) findViewById(R.id.GasDialogHe2Result);
		
		n2bar = (ProgressBar) findViewById(R.id.GasDialogN2);
		n2result = (TextView) findViewById(R.id.GasDialogN2Result);
		
		ppo2bar = (ProgressBar) findViewById(R.id.GasDialogppO2);
		ppo2result = (TextView) findViewById(R.id.GasDialogppO2Result);
		
		mod =(TextView) findViewById(R.id.GasDialogMod);
			
		o2bar.setOnSeekBarChangeListener(o2Listener);
		he2bar.setOnSeekBarChangeListener(he2Listener);
		
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
			
			double fo2 = progress/100.0;
			double fhe2 = gas.getFHe();
			if (fo2 + fhe2 >= 1.0){
				fhe2=1-fo2;
			}
			gas.setFHe(fhe2);
			gas.setFO2(fo2);
			gas.setMod(Gas.getMod(gas.getFO2(), ppo2));
			
			redrawGas();
			
		}
	}; 
	
	OnSeekBarChangeListener he2Listener = new OnSeekBarChangeListener() {
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			double fhe2 = progress/100.0;
			double fo2 = gas.getFO2();
			if (fo2 + fhe2 >= 1.0){
				fo2=1-fhe2;
			}
			gas.setFO2(fo2);
			gas.setFHe(fhe2);
			
			redrawGas();
			
		}
	}; 
	
	private void redrawGas(){
		o2bar.setProgress((int) (gas.getFO2()*100));
		o2result.setText((int) (gas.getFO2()*100) + " %");
		
		he2bar.setProgress((int) (gas.getFHe()*100));
		he2result.setText((int) (gas.getFHe()*100) + " %");
		
		n2bar.setProgress((int) (gas.getFN2()*100));
		n2result.setText((int) (gas.getFN2()*100) + " %");
		
		
		ppo2bar.setProgress((int) (ppo2*100-80));
		ppo2result.setText(ppo2 + " ");
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		mod.setText(nf.format(gas.getMod())+"");
	}
}
