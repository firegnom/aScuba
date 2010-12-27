package org.ediver.ascuba;

import java.util.List;

import mvplan.gas.Gas;
import mvplan.main.Mvplan;

import org.ediver.ascuba.gui.GasDialog;
import org.ediver.ascuba.gui.GasDialogCallback;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GasList extends AScubaActivity {
	Button add;
	ListView list;
	TestAdaptor a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gas_list_view);
		list = (ListView) findViewById(R.id.gas_list_list);
		add = (Button) findViewById(R.id.gas_list_add);
		add.setOnClickListener(addButtonListener);
		a = new TestAdaptor(this.getApplicationContext(),
				R.layout.gas_list_label, R.id.gas_list_label_text,
				Mvplan.prefs.getPrefGases());
		list.setAdapter(a);
	}

	android.view.View.OnClickListener addButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			new GasDialog(GasList.this, addCallback).show();
		}
	};

	GasDialogCallback addCallback = new GasDialogCallback() {
		public void notify(Gas g) {
			Mvplan.prefs.getPrefGases().add(g);
			System.out.println(g);
			a.notifyDataSetChanged();

		}
	};

	private class TestAdaptor extends ArrayAdapter<Gas> {

		public TestAdaptor(Context context, int resource,
				int textViewResourceId, List<Gas> objects) {
			super(context, resource, textViewResourceId, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View a =  super.getView(position, convertView, parent);
			ImageButton b = (ImageButton) a.findViewById(R.id.gas_list_label_edit);
			b.setTag(new Integer(position));
			b.setOnClickListener(editButtonListener);
			b = (ImageButton) a.findViewById(R.id.gas_list_label_delete);
			b.setTag(new Integer(position));
			b.setOnClickListener(deleteButtonListener);
			return a;
		}
		
		android.view.View.OnClickListener editButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {
				
				Gas g = a.getItem((Integer) v.getTag());
				EditCallback e = new EditCallback((Integer) v.getTag()); 
				new GasDialog(GasList.this ,g, e).show();
			}
		};
		android.view.View.OnClickListener deleteButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {
				a.remove(a.getItem((Integer) v.getTag()));
			}
		};
	}
	private class EditCallback implements GasDialogCallback{
		public EditCallback(int position) {
			this.position=position;
		}
		int position;
		public void notify(Gas g) {
			a.remove(a.getItem(position));
			a.insert(g, position);
			
		}
	}
	private class DeleteCallback implements GasDialogCallback{
		public DeleteCallback(int position) {
			this.position=position;
		}
		int position;
		public void notify(Gas g) {
			a.remove(a.getItem(position));
		}
	}

}