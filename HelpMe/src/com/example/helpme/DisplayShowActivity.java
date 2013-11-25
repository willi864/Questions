package com.example.helpme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DisplayShowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
            Intent intent = getIntent();
            String message = intent.getStringExtra(DisplayLookActivity.EXTRA_MESSAGE);

            // Create the text view
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText(message);

            // Set the text view as the activity layout
            setContentView(textView); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_show, menu);
		return true;
	}

}
