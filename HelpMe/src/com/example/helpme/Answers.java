package com.example.helpme;

public class Answers{
      private long id;
	  private String answer;
	  private long questionId;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  
	  public String getAnswer() {
	    return answer;
	  }

	  public void setAnswer(String answer) {
	    this.answer = answer;
	  }
	  
	  public long getQuesId() {
		    return questionId;
		  }

	  public void setQuesId(long questionId) {
		  this.questionId = questionId;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return answer;
	  }
}