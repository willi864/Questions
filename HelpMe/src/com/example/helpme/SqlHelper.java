package com.example.helpme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_QUESTIONS ="questions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN1_QUESTION = "question";
	
	private static final String DATABASE_NAME = "questions.db";
	private static final int DATABASE_VERSION = 1;
	
	 private static final String DATABASE_CREATE = "create table "
		      + TABLE_QUESTIONS + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN1_QUESTION
		      + " text not null);";
	
	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(SqlHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
		onCreate(db);
		
	}
	
	
}