package com.dt.derbyday.dto;

public class UserChoiceDisplay {
private int questionId;	
private String question;
private String choice;
private int seq;

public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public String getChoice() {
	return choice;
}
public void setChoice(String choice) {
	this.choice = choice;
}
public int getSeq() {
	return seq;
}
public void setSeq(int seq) {
	this.seq = seq;
}
public int getQuestionId() {
	return questionId;
}
public void setQuestionId(int questionId) {
	this.questionId = questionId;
}

}
