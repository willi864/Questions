package com.example.helpme;


import java.util.List;

import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplaySelectQuestion extends ListActivity {

	private QuestionDataSource datasource;
	private AnswerDataSource ansdatasource;
	
	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_select_question);

		datasource = new QuestionDataSource(this);
		datasource.open();
		ansdatasource = new AnswerDataSource(this);
		ansdatasource.open();
		
		Intent intent = getIntent();
		message = intent.getStringExtra(DisplayLookActivity.EXTRA_MESSAGE);
		
		TextView tv = (TextView) findViewById(R.id.question);
		tv.setText(message);
		
		
		message = message.substring(message.indexOf(" ")+1);
		long quesid = datasource.getQuestionId(message);
		List<Answers> values = ansdatasource.getAllAnswers(quesid);
		if(values!=null){
			ArrayAdapter<Answers> adapter = new ArrayAdapter<Answers>(this,android.R.layout.simple_dropdown_item_1line,values);
			setListAdapter(adapter);
		}		
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
		getMenuInflater().inflate(R.menu.display_select_question, menu);
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
	
	@SuppressLint("NewApi")
	public void answerQuestion(View view){
		
        EditText editText = (EditText) findViewById(R.id.edit_textanswer);
		String answer = editText.getText().toString();
		if(answer.equals("")||answer.equals(" ")||answer.equals("\n")||answer.equals("\t")){
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
		
		List<Answers> answers= ansdatasource.getAllAnswers(quesId);
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<Answers> adapter = (ArrayAdapter<Answers>) getListAdapter();
		if(adapter==null){
			adapter = new ArrayAdapter<Answers>(this,android.R.layout.simple_dropdown_item_1line,answers);
			setListAdapter(adapter);
		}else{
			adapter.clear();
			adapter.addAll(answers);
		    adapter.notifyDataSetChanged();
		}
	}

	
	 @Override
	  protected void onResume() {
		ansdatasource.open();
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    ansdatasource.close();
	    super.onPause();
	  }
}
