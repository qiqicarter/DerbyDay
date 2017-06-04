package com.dt.derbyday.model;

public class Question {
    private Integer id;

    private String game;

    private String question;

    private Integer seq;
    
    private Integer maxChoice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game == null ? null : game.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

	public Integer getMaxChoice() {
		return maxChoice;
	}

	public void setMaxChoice(Integer maxChoice) {
		this.maxChoice = maxChoice;
	}
    
}