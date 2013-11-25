package com.example.helpme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DisplayLookActivity extends Activity {

	static final String EXTRA_MESSAGE = "com.example.helpme.MESSAGE";;
    public String test = "hmm?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_look);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_look, menu);
		return true;
	}
	
	public void showQuestion(View view) {	
		Intent intent = new Intent(this, DisplayShowActivity.class);
        intent.putExtra(EXTRA_MESSAGE, test);
        startActivity(intent);
    }
	
	public void showSearchQuestion(View view) {
		Intent intent = new Intent(this, DisplayShowActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
	

}
