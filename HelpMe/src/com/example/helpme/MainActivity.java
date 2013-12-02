package com.example.helpme;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	final static int port =7755;
	private static int[] mostRecent = new int[10];
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//	    try{  
//	        ServerSocket s = new ServerSocket(port);
//	        while (true){ 
//	          Socket incoming = s.accept();
//	          Runnable r = new ThreadedHandler(incoming);
//	          Thread t = new Thread(r);
//	          t.start();
//	        }
//	      }catch (IOException e){
//	        e.printStackTrace();
//	      }
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void lookQuestion(View view) {
        Intent intent = new Intent(this, DisplayLookActivity.class);
        startActivity(intent);
    }
	
	public void answerQuestion(View view) {
        Intent intent = new Intent(this, DisplayAnswerActivity.class);
        startActivity(intent);
    }
	
	
	public void newQuestion(View view) {
        Intent intent = new Intent(this, DisplayPostActivity.class);
        startActivity(intent);
    }

}
