package com.example.helpme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AnswerDataSource{
	private SQLiteDatabase database;
	private AnswerSqlHelper dbHelper;
	private String[] allColumns ={AnswerSqlHelper.COLUMN_ID,
			AnswerSqlHelper.COLUMN1_ANSWER, AnswerSqlHelper.COLUMN2_ANSWER
	};
	
	public AnswerDataSource(Context context) {
		 dbHelper = new AnswerSqlHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	 
	public void close(){
		dbHelper.close();
	}
	
	public Answers createQuestion(String answers, int quesid){
		if(answers.equals(""))
			return null;
		ContentValues values = new ContentValues();
		values.put(AnswerSqlHelper.COLUMN1_ANSWER, answers);
		values.put(AnswerSqlHelper.COLUMN2_ANSWER, ""+quesid);

		long insertId = database.insert(AnswerSqlHelper.TABLE_ANSWERS, null, values);
		Cursor cursor = database.query(AnswerSqlHelper.TABLE_ANSWERS, allColumns, SqlHelper.COLUMN_ID+"="+insertId, null, null, null, null);
		cursor.moveToFirst();
		Answers newAnswers = cursorToAnswer(cursor);
		cursor.close();
		return newAnswers;
	}
	
	public void deleteAnswers(Answers answer){
		long id = answer.getId();
		database.delete(AnswerSqlHelper.TABLE_ANSWERS, AnswerSqlHelper.COLUMN_ID+" = "+id, null);
	}
	
	public void clearAnswers(){
		dbHelper.onUpgrade(database, 1, 2);
	}
	
	public List<Answers> getAllAnswers(){
		List<Answers> answers = new ArrayList<Answers>();
		Cursor cursor = database.query(AnswerSqlHelper.TABLE_ANSWERS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Answers answer = cursorToAnswer(cursor);
			answers.add(answer);
			cursor.moveToNext();
		}
		cursor.close();
		return answers;
	}
	
	private Answers cursorToAnswer(Cursor cursor) {
		Answers answer = new Answers();
		answer.setId(cursor.getLong(0));
		answer.setAnswer(cursor.getString(1));
		answer.setQuesId(cursor.getLong(2));
		return answer;
	}
}