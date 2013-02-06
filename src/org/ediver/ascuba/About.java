package org.ediver.ascuba;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends AScubaActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		TextView t = (TextView) findViewById(R.id.about_text);
		
		t.setText(Html.fromHtml(getString(R.string.about_text)));
		
		
		Button b = (Button) findViewById(R.id.about_close_button);
		b.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

}