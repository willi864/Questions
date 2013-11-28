package com.example.helpme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AnswerSqlHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_ANSWERS ="answers";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN1_ANSWER = "answer";
	public static final String COLUMN2_ANSWER = "quesid";

	
	private static final String DATABASE_NAME = "answers.db";
	private static final int DATABASE_VERSION = 1;
	
	 private static final String DATABASE_CREATE = "create table "
		      + TABLE_ANSWERS + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN1_ANSWER
		      + " text not null,"+COLUMN2_ANSWER+" integer);";
	
	public AnswerSqlHelper(Context context) {
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
		Log.w(AnswerSqlHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
		onCreate(db);
		
	}
	
	
}