package com.example.helpme;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_look, menu);
		return true;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
	    // Get the item that was clicked
	    Object o = this.getListAdapter().getItem(position);
	    String keyword = o.toString();
//	    keyword=keyword.substring(keyword.indexOf(" ")+1);
	    Intent intent = new Intent(this, DisplaySelectQuestion.class);
        intent.putExtra(EXTRA_MESSAGE, keyword);
        startActivity(intent);

	}
	
	public void showSearchQuestion(View view) {
		Intent intent = new Intent(this, DisplayShowActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String questionKey = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, questionKey);
        startActivity(intent);
    }
	

}
