package com.example.helpme;


import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplayAnswerActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.helpme.Question";
	private QuestionDataSource datasource;
	private AnswerDataSource ansdatasource;
	
	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_answer);
		
		datasource = new QuestionDataSource(this);
		datasource.open();
		ansdatasource = new AnswerDataSource(this);
		ansdatasource.open();
		
		message=datasource.getFirstQuestions();
		
		TextView tv = (TextView) findViewById(R.id.text_question);
		tv.setText(message);
		tv.setTextSize(20);
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
		if(answer.equals("")){
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle("Input Error");
			
			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			return;
		}
		long quesId=datasource.getQuestionId(message);
		ansdatasource.createAnswer(answer, quesId);
		
		Intent intent =  new Intent(this, DisplaySelectQuestion.class);
		intent.putExtra(EXTRA_MESSAGE, message);		
		
		startActivity(intent);
	}
	
	public void toNextQuestion(View view){
		long quesId=datasource.getQuestionId(message);
		quesId++;
		message = datasource.getQuestion(quesId);
		if(message==null)
			message=datasource.getFirstQuestions();
		
		TextView tv = (TextView) findViewById(R.id.text_question);
		tv.setText(message);
		tv.setTextSize(20);
	}
	
	public void toPrevQuestion(View view){
		long quesId=datasource.getQuestionId(message);
		quesId--;
		message = datasource.getQuestion(quesId);
		if(message==null)
			message=datasource.getLastQuestions();
		
		TextView tv = (TextView) findViewById(R.id.text_question);
		tv.setText(message);
		tv.setTextSize(20);
	}
	
	 @Override
	  protected void onResume() {
		ansdatasource.open();
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
		ansdatasource.close();
	    datasource.close();
	    super.onPause();
	  }
}
