package com.example.helpme;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplayAnswerActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.helpme.Question";
	private QuestionDataSource datasource;
	String message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_answer);
		
		datasource = new QuestionDataSource(this);
		datasource.open();
		
		Intent intent = getIntent();
		message = intent.getStringExtra(DisplaySelectQuestion.EXTRA_MESSAGE);
		if(message==null)
			message=datasource.getTheQuestions(0);
		
		TextView tv = (TextView) findViewById(R.id.text_question);
		tv.setText(message);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_answer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void addAnswer(View view){
		EditText editText = (EditText) findViewById(R.id.edit_answer);
		String answer = editText.getText().toString();
		if(answer.equals(""))
			return;
//		datasource.addAnswer(message,answer);

		Intent intent =  new Intent(this, DisplaySelectQuestion.class);
		intent.putExtra(EXTRA_MESSAGE, message);		
		
		startActivity(intent);
		
		
	}
}
