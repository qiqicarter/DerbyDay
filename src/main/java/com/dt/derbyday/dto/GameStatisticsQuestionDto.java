package com.dt.derbyday.dto;

import java.util.List;

public class GameStatisticsQuestionDto {

	private String question;
	private List<GameStatisticsChoiceDto> choices;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<GameStatisticsChoiceDto> getChoices() {
		return choices;
	}
	public void setChoices(List<GameStatisticsChoiceDto> choices) {
		this.choices = choices;
	}
}
