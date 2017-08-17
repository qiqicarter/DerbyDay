package com.dt.derbyday.dto;

import java.util.List;

import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.Question;

public class QuestionDto extends Question{
	private List<Choice> choices;

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	
}
