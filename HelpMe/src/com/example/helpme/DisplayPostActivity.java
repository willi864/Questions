package com.example.helpme;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


import android.os.AsyncTask;
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
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

public class DisplayPostActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.helpme.post.Question";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_post);
        
		TextView tv = (TextView) findViewById(R.id.text_post);
		tv.setText("Post question:");
		tv.setTextSize(30);
		
		
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
		getMenuInflater().inflate(R.menu.display_post, menu);
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
	
	public void postQuestion(View view){		
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String question = editText.getText().toString();
		if(question.equals("")||question.equals(" ")||question.equals("\t")||question.equals("\n")){
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
	    
		String[] ques={question};
		new Operation().execute(ques);
	}

	
	private class Operation extends AsyncTask<String, Void, String>{
		Socket requestsocket;
		PrintWriter out;
		protected String doInBackground(String... question){
			try{
				requestsocket = new Socket(InetAddress.getByName("10.0.2.2"),7777);
				
				out = new PrintWriter(requestsocket.getOutputStream(),true);
				
				out.println("ADDQUESTION|"+question[0]);
				
				requestsocket.close();
				
			}catch(Exception err){ return err.getMessage();	}
			
			return question[0];
		}
		

		 
		protected void onPostExecute(String question) {
			TextView tv = (TextView) findViewById(R.id.text_post);
			tv.setText("You posted the question:\n"+question);
			tv.setTextSize(30);
		}
		 
		 
	}
}
