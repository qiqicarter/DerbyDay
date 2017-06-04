package com.dt.derbyday.model;

public class GameResult {
    private Integer id;

    private String game;

    private Integer question;

    private Integer choice;

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

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }
}