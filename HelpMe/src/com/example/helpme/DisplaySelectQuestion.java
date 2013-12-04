package com.example.helpme;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
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
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_select_question);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(DisplayLookActivity.EXTRA_MESSAGE);
		
		TextView tv = (TextView) findViewById(R.id.question);
		tv.setText(message);
		
		String[] params = {message};
		new FetchAnswerOperation().execute(params);		
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
		
		TextView tv = (TextView)findViewById(R.id.question);
		String message = tv.getText().toString();
		
		String[] params = {message, answer};
		new AddAnswerOperation().execute(params);
		
		String[] mes = {message};
		new FetchAnswer2Operation().execute(mes);
	}
	
	private class AddAnswerOperation extends AsyncTask<String, Void, Void>{
		Socket requestsocket;
		PrintWriter out;
		
		protected Void doInBackground(String... param){
			try{
				requestsocket = new Socket(InetAddress.getByName("10.0.2.2"),7777);
				out = new PrintWriter(requestsocket.getOutputStream(),true);
				
				out.println("ADDANSWER|"+param[0]+"|"+param[1]);
				
				requestsocket.close();
				
			}catch(Exception err){	}
			return null;
		}
	}
	
	private class FetchAnswerOperation extends AsyncTask<String, Void, List<String>>{
		Socket requestsocket;		
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		
		protected List<String> doInBackground(String... param){
			try{
				requestsocket = new Socket(InetAddress.getByName("10.0.2.2"),7777);
				in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
				out = new PrintWriter(requestsocket.getOutputStream(),true);
				
				out.println("FETCHANSWERS|"+param[0]);
				answer = in.readLine();
				
				requestsocket.close();
				
			}catch(Exception err){	}
			
			if(answer!=null){
				ans = Arrays.asList(answer.split("\\|"));
			}
			return ans;
		}
		 
		protected void onPostExecute(List<String> list) {
			List<String> values = list;
			if(values!=null){
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplaySelectQuestion.this,android.R.layout.simple_dropdown_item_1line,values);
				setListAdapter(adapter);
			}		
		}
		 
	}
	
	private class FetchAnswer2Operation extends AsyncTask<String, Void, List<String>>{
		Socket requestsocket;		
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		
		protected List<String> doInBackground(String... param){
			try{
				requestsocket = new Socket(InetAddress.getByName("10.0.2.2"),7777);
				in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
				out = new PrintWriter(requestsocket.getOutputStream(),true);
				
				out.println("FETCHANSWERS|"+param[0]);
				answer = in.readLine();
				
				requestsocket.close();
				
			}catch(Exception err){	}
			
			if(answer!=null){
				ans = Arrays.asList(answer.split("\\|"));
			}
			return ans;
		}
		 
		@SuppressLint("NewApi")
		protected void onPostExecute(List<String> list) {
			List<String> answers = list;
			setListAdapter(new ArrayAdapter<String>(DisplaySelectQuestion.this,android.R.layout.simple_dropdown_item_1line,answers));
		}
		 
	}
	
}
