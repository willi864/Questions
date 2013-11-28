package com.example.helpme;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DisplayLookActivity extends ListActivity {

	static final String EXTRA_MESSAGE = "com.example.helpme.Question";
	private QuestionDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_look);
		
		datasource = new QuestionDataSource(this);
		datasource.open();
		
		List<Questions> values = datasource.getTopTenQuestions();
		if(values!=null){
			ArrayAdapter<Questions> adapter = new ArrayAdapter<Questions>(this,android.R.layout.simple_dropdown_item_1line,values);
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
	    // Get the item that was clicked
	    Object o = this.getListAdapter().getItem(position);
	    String keyword = o.toString();
	    Intent intent = new Intent(this, DisplaySelectQuestion.class);
        intent.putExtra(EXTRA_MESSAGE, keyword);
        startActivity(intent);

	}
	
	@SuppressLint("NewApi")
	public void showSearchQuestion(View view) {
		EditText editText = (EditText) findViewById(R.id.editText1);
		String questionKey = editText.getText().toString();
	    if(questionKey.equals("")||questionKey.equals(" ")||questionKey.equals("\t")||questionKey.equals("\n")){
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
		List<Questions> values = datasource.getRelatedQuestions(questionKey);		

		@SuppressWarnings("unchecked")
		ArrayAdapter<Questions> adapter = (ArrayAdapter<Questions>) getListAdapter();
		if(adapter==null){
			adapter = new ArrayAdapter<Questions>(this,android.R.layout.simple_dropdown_item_1line,values);
			setListAdapter(adapter);
		}else if(values==null){
			adapter.clear();
		    adapter.notifyDataSetChanged();
		}else{
			adapter.clear();
			adapter.addAll(values);
		    adapter.notifyDataSetChanged();
		}
    }
	
	 @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
}
