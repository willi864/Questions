package com.example.helpme;

public class Questions{
      private long id;
	  private String question;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getQuestion() {
	    return question;
	  }

	  public void setQuestion(String question) {
	    this.question = question;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return ""+id+". "+question;
	  }
}