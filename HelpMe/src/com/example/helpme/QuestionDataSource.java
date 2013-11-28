package com.example.helpme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuestionDataSource{
	private SQLiteDatabase database;
	private SqlHelper dbHelper;
	private String[] allColumns ={SqlHelper.COLUMN_ID,
			SqlHelper.COLUMN1_QUESTION
	};
	
	public QuestionDataSource(Context context) {
		 dbHelper = new SqlHelper(context);
		
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	 
	public void close(){
		dbHelper.close();
	}
	
	public Questions createQuestion(String question){
		if(question.equals(""))
			return null;
		ContentValues values = new ContentValues();
		values.put(SqlHelper.COLUMN1_QUESTION, question);

		long insertId = database.insert(SqlHelper.TABLE_QUESTIONS, null, values);
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS, allColumns, SqlHelper.COLUMN_ID+"="+insertId, null, null, null, null);
		cursor.moveToFirst();
		Questions newQuestion = cursorToQuestion(cursor);
		cursor.close();
		return newQuestion;
	}
	
	public void deleteQuestion(Questions question){
		long id = question.getId();
		System.out.println("Question deleted with id: "+id);
		database.delete(SqlHelper.TABLE_QUESTIONS, SqlHelper.COLUMN_ID+" = "+id, null);
	}
	
	public Questions currentQuestion(){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS, allColumns,null, null, null, null, null);
		cursor.moveToLast();
		Questions newQuestion = cursorToQuestion(cursor);
		cursor.close();
		return newQuestion;
	}
	
	public void clearQuestions(){
		dbHelper.onUpgrade(database, 1, 2);
	}
	
	public List<Questions> getAllQuestions(){
		List<Questions> questions = new ArrayList<Questions>();
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Questions question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToNext();
		}
		cursor.close();
		return questions;
	}
	
	public List<Questions> getTopTenQuestions(){
		List<Questions> questions = new ArrayList<Questions>();
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, null, null, null, null, null);
		int num=cursor.getCount();
		if(num<=0)
			return null;
		cursor.moveToLast();
		while(cursor.getPosition()>=0&&cursor.getPosition()>=(num-10)){
			Questions question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToPrevious();
		}
		cursor.close();
		return questions;
	}
	
	public List<Questions> getRelatedQuestions(String key){
		List<Questions> questions = new ArrayList<Questions>();
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, SqlHelper.COLUMN1_QUESTION+" like \"%"+key+"%\"", null, null, null, null);
		if(cursor.getCount()==0)
			return null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Questions question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToNext();
		}
		cursor.close();
		
		return questions;
	}
	
	public String getFirstQuestions(){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(1);
	}
	
	public String getLastQuestions(){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, null, null, null, null, null);
		cursor.moveToLast();		
		return cursor.getString(1);
	}
	
	public String getQuestion(long id){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, SqlHelper.COLUMN_ID+"="+id, null, null, null, null);
		if(cursor.getCount()==0)
			return null;
		cursor.moveToFirst();
		Questions question = cursorToQuestion(cursor);
		return question.getQuestion();
	}
	
	public long getQuestionId(String question){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns,SqlHelper.COLUMN1_QUESTION+" like \""+question+"\"" , null, null, null, null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
	
	public int getNumberOfQuestions(){
		Cursor cursor = database.query(SqlHelper.TABLE_QUESTIONS,allColumns, null, null, null, null, null);
		int num = cursor.getCount();
		return num;
	}
	
	
	private Questions cursorToQuestion(Cursor cursor) {
		Questions question = new Questions();
		question.setId(cursor.getLong(0));
		question.setQuestion(cursor.getString(1));
		return question;
	}
}